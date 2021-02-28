/*
 * Copyright 2021 Anton Bardishev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.mrabar.aml.engine;

import me.mrabar.aml.data.graph.AbstractEntity;
import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.OwnershipEdge;
import me.mrabar.aml.data.graph.Person;
import me.mrabar.aml.data.reporting.OwnerInfo;
import me.mrabar.aml.data.reporting.OwnersReport;
import me.mrabar.aml.data.reporting.PersonReport;
import me.mrabar.aml.data.reporting.Share;
import one.microstream.persistence.types.Storer;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class BatEngine implements BatEngineInterface {
  private final BatRoot root = new BatRoot();

  private final EmbeddedStorageManager storageManager = EmbeddedStorage.start(root);
  private final Storer eagerStorer = storageManager.createEagerStorer();

  public BatRoot debugRoot() {
    return root;
  }

  public void storePerson(Person person) {
    storageManager.store(root.savePerson(person));
  }

  public void storeEntity(LegalEntity entity) {
    storageManager.store(root.saveEntity(entity));
  }

  public void linkPersonAndBusiness(String personId, String entityId, BigDecimal percentage) {
    Person p = root.getPerson(personId);
    LegalEntity l = root.getEntity(entityId);

    OwnershipEdge edge = new OwnershipEdge(p, l, percentage);

    eagerStorer.storeAll(p.addShare(edge), l.addOwner(edge));
    eagerStorer.commit();
  }

  public void linkBusinesses(String ownerId, String targetId, BigDecimal percentage) {
    LegalEntity owner = root.getEntity(ownerId);
    LegalEntity target = root.getEntity(targetId);

    OwnershipEdge edge = new OwnershipEdge(owner, target, percentage);

    eagerStorer.storeAll(owner.addShare(edge), target.addOwner(edge));
    eagerStorer.commit();
  }

  @Override
  public PersonReport personCompanies(String pid) {
    Person p = root.getPerson(pid);
    List<LegalEntity> businesses = new ArrayList<>();

    Deque<OwnershipEdge> edges = new LinkedList<>(p.getShares());
    while (!edges.isEmpty()) {
      OwnershipEdge e = edges.removeFirst();
      if (!businesses.contains(e.getBusiness())) {
        LegalEntity le = e.getBusiness();
        edges.addAll(le.getShares());
        businesses.add(le);
      }
    }

    PersonReport pr = new PersonReport(p);
    pr.setShares(businesses.stream().map(Share::new).collect(Collectors.toList()));
    return pr;
  }

  // For personOwnership, recursive DFS
  private void visitNode(Map<String, BigDecimal> shares, LegalEntity le, BigDecimal percentage) {
    if (shares.containsKey(le.getId())) {
      shares.put(le.getId(), shares.get(le.getId()).add(percentage));
    } else {
      shares.put(le.getId(), percentage);
    }
    for (OwnershipEdge oe : le.getShares()) {
      visitNode(shares, oe.getBusiness(), percentage.multiply(oe.getPercentage()));
    }
  }

  public PersonReport personOwnership(String pid) {
    Person p = root.getPerson(pid);
    Map<String, BigDecimal> shares = new HashMap<>();

    for (OwnershipEdge oe : p.getShares()) {
      visitNode(shares, oe.getBusiness(), oe.getPercentage());
    }

    PersonReport pr = new PersonReport(p);
    pr.setShares(shares.entrySet().stream().map(e -> new Share(root.getEntity(e.getKey()), e.getValue())).collect(Collectors.toList()));
    return pr;
  }

  private void visitNodeReverse(Map<String, BigDecimal> owners, LegalEntity le, BigDecimal percentage) {
    for (OwnershipEdge oe : le.getOwners()) {
      AbstractEntity ae = oe.getShareholder();
      if (ae instanceof Person) {
        BigDecimal newPercentage = percentage.multiply(oe.getPercentage());
        if (owners.containsKey(ae.getId())) {
          newPercentage = newPercentage.add(owners.get(ae.getId()));
        }
        owners.put(ae.getId(), newPercentage);
      } else {
        visitNodeReverse(owners, (LegalEntity) ae, percentage.multiply(oe.getPercentage()));
      }
    }
  }

  public OwnersReport companyOwners(String cid) {
    Map<String, BigDecimal> owners = new HashMap<>();
    LegalEntity le = root.getEntity(cid);
    visitNodeReverse(owners, le, BigDecimal.ONE);

    OwnersReport or = new OwnersReport(le);
    or.setOwners(owners.entrySet().stream().map(e -> new OwnerInfo(e.getValue(), root.getPerson(e.getKey()))).collect(Collectors.toList()));

    return or;
  }

  public void init() {
    if (storageManager.root() == null) {
      storageManager.storeRoot();
    }
  }

  public void shutdown() {
    storageManager.shutdown();
  }
}
