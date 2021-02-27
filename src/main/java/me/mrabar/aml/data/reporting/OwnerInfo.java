package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.graph.Person;

import java.math.BigDecimal;

public class OwnerInfo {
  private BigDecimal share;
  private Person owner;

  public OwnerInfo(BigDecimal share, Person owner) {
    this.share = share;
    this.owner = owner;
  }

  public BigDecimal getShare() {
    return share;
  }

  public void setShare(BigDecimal share) {
    this.share = share;
  }

  public Person getOwner() {
    return owner;
  }

  public void setOwner(Person owner) {
    this.owner = owner;
  }

  @Override
  public String toString() {
    return String.format("%s(%s) owns %s %%",
                         owner.getName(),
                         owner.getId(),
                         share.multiply(BigDecimal.valueOf(100)).toPlainString()
    );
  }
}
