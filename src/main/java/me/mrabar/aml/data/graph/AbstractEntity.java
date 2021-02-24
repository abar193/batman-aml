package me.mrabar.aml.data.graph;

import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.List;

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
