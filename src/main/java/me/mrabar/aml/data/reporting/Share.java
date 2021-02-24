package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.graph.LegalEntity;

import java.math.BigDecimal;

public class Share {
  private BigDecimal share;
  private LegalEntity entity;

  public Share(BigDecimal share, LegalEntity entity) {
    this.share = share;
    this.entity = entity;
  }

  public BigDecimal getShare() {
    return share;
  }

  public LegalEntity getEntity() {
    return entity;
  }

  public void setShare(BigDecimal share) {
    this.share = share;
  }

  public void setEntity(LegalEntity entity) {
    this.entity = entity;
  }

  @Override
  public String toString() {
    return String.format("%s %% of %s (%s)", share.toPlainString(), entity.getName(), entity.getId());
  }
}
