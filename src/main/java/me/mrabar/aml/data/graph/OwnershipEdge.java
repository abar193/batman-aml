package me.mrabar.aml.data.graph;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class OwnershipEdge {
  private final AbstractEntity shareholder;
  private final LegalEntity business;
  private final BigDecimal percentage;

  public OwnershipEdge(AbstractEntity shareholder, LegalEntity business, BigDecimal percentage) {
    this.shareholder = shareholder;
    this.business = business;
    this.percentage = percentage;
  }

  public AbstractEntity getShareholder() {
    return shareholder;
  }

  public LegalEntity getBusiness() {
    return business;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  @Override
  public String toString() {
    return String.format("%s %s %s%%", shareholder.id, business.getId(), percentage.toPlainString());
  }
}
