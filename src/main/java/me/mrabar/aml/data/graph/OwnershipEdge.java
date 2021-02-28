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

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * Defines an edge between an AbstractEntity - be that Person or LegalEntity and a business that is
 * owned by the shareholder. Percentage should be in range [0; 1] inclusively.
 */
public class OwnershipEdge {
  private final AbstractEntity shareholder;
  private final LegalEntity business;
  private final BigDecimal percentage;

  public OwnershipEdge(AbstractEntity shareholder, LegalEntity business, BigDecimal percentage) {
    this.shareholder = shareholder;
    this.business = business;
    if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.ONE) > 0) {
      throw new IllegalArgumentException("Percentage should range from 0 to 1");
    }
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
