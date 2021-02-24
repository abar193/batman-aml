package me.mrabar.aml.data.graph;

import me.mrabar.aml.data.ClientStatus;

public class Person extends AbstractEntity {
  protected final ClientStatus status;

  public Person(String id, String name, ClientStatus status) {
    super(id, name);
    this.status = status;
  }
}
