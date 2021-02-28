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

package me.mrabar.aml.data.graph;

import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a legal entity - company, firm, trust, foundation, whatever your analysts can think of.
 * LegalEntity should contain a List of owners - LegalEntities or Persons that have some shares in this entity.
 * <p/>
 * From a business point of view sum of percentages of all OwnershipEdges should add up to 1 (100%),
 * but it's never checked for in this project. Data quality issues are a huge pain, and it's not our
 * duty to overthink stuff here - we're just getting it done.
 */
public class LegalEntity extends AbstractEntity {
  private final Lazy<List<OwnershipEdge>> owners = Lazy.Reference(new ArrayList<>());

  public LegalEntity(String id, String name) {
    super(id, name);
  }

  public List<OwnershipEdge> getOwners() {
    return owners.get();
  }

  public Lazy<List<OwnershipEdge>> addOwner(OwnershipEdge oe) {
    owners.get().add(oe);
    return owners;
  }
}
