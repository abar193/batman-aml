package me.mrabar.aml.data.graph;

import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.List;

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
