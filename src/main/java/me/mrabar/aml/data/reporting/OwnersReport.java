package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.graph.LegalEntity;

import java.util.List;
import java.util.stream.Collectors;

public class OwnersReport {
  private final LegalEntity business;
  private List<OwnerInfo> owners;

  public OwnersReport(LegalEntity business) {
    this.business = business;
  }

  public LegalEntity getBusiness() {
    return business;
  }

  public List<OwnerInfo> getOwners() {
    return owners;
  }

  public void setOwners(List<OwnerInfo> owners) {
    this.owners = owners;
  }

  @Override
  public String toString() {
    return String.format("Ownership report for %s (%s):\n\t", business.getName(), business.getId()) +
        owners.stream().map(OwnerInfo::toString).collect(Collectors.joining("\n\t"));

  }
}
