package me.mrabar.aml.engine;

import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.OwnershipEdge;
import me.mrabar.aml.data.graph.Person;
import one.microstream.persistence.types.Storer;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

import java.math.BigDecimal;

public class BatEngine {
  private BatRoot root = new BatRoot();
  private EmbeddedStorageManager storageManager = EmbeddedStorage.start(root);

  public BatRoot debugRoot() {
    return root;
  }

  public void storePerson(Person person) {
    storageManager.store(root.savePerson(person));
  }

  public void storeEntity(LegalEntity entity) {
    storageManager.store(root.saveEntity(entity));
  }

  public void linkPersonAndBusiness(String personId, String entityId, BigDecimal ownership) {
    Person p = root.getPerson(personId);
    LegalEntity l = root.getEntity(entityId);

    OwnershipEdge edge = new OwnershipEdge(p, l, ownership);

    Storer storer = storageManager.createEagerStorer();
    storer.storeAll(p.addShare(edge), l.addOwner(edge));
    storer.commit();
  }

  public void init() {
    if(storageManager.root() == null) {
      storageManager.storeRoot();
    }
  }

  public void shutdown() {
    storageManager.shutdown();
  }
}
