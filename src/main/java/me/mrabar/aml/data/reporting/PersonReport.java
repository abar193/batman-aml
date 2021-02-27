package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.graph.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonReport {
  private final Person owner;
  private List<Share> shares = new ArrayList<>();

  public PersonReport(Person owner) {
    this.owner = owner;
  }

  public Person getOwner() {
    return owner;
  }

  public List<Share> getShares() {
    return shares;
  }

  public void setShares(List<Share> shares) {
    this.shares = shares;
  }

  @Override
  public String toString() {
    return String.format("Report for %s (%s):\n\t", owner.getName(), owner.getId()) +
        shares.stream().map(Share::toString).collect(Collectors.joining("\n\t"));

  }
}
