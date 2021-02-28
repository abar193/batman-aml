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
 * Represents a vertex (or a node) in a AML graph. Each node is identified by a unique id, and
 * should have a name. May contain shares - list of businesses that this entity has a stake in.
 * <p/>
 * Is implemented by a (Physical)Person or a LegalEntity classes.
 */
public abstract class AbstractEntity {
  protected final String id;
  protected final String name;
  protected final Lazy<List<OwnershipEdge>> shares = Lazy.Reference(new ArrayList<>());

  public AbstractEntity(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<OwnershipEdge> getShares() {
    return shares.get();
  }

  public Lazy<List<OwnershipEdge>> addShare(OwnershipEdge oe) {
    shares.get().add(oe);
    return shares;
  }
}
