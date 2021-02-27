package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.graph.LegalEntity;

import java.math.BigDecimal;

public class Share {
  private LegalEntity entity;
  private BigDecimal share;

  public Share(LegalEntity entity, BigDecimal share) {
    this.entity = entity;
    this.share = share;
  }

  // It's my project, I do what I want
  public Share(BigDecimal share, LegalEntity entity) {
    this.entity = entity;
    this.share = share;
  }

  public Share(LegalEntity entity) {
    this.entity = entity;
    this.share = null;
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
    if (share == null) {
      return String.format("owns %s (%s)", entity.getName(), entity.getId());
    }
    return String.format("%s %% of %s (%s)",
                         share.multiply(BigDecimal.valueOf(100)).toPlainString(),
                         entity.getName(),
                         entity.getId()
    );
  }
}
