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

package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.graph.Person;

import java.math.BigDecimal;

/**
 * DTO to store "how many shares does this person has".
 */
public class OwnerInfo {
  private BigDecimal share;
  private Person shareholder;

  public OwnerInfo(BigDecimal share, Person shareholder) {
    this.share = share;
    this.shareholder = shareholder;
  }

  public BigDecimal getShare() {
    return share;
  }

  public void setShare(BigDecimal share) {
    this.share = share;
  }

  public Person getShareholder() {
    return shareholder;
  }

  public void setShareholder(Person shareholder) {
    this.shareholder = shareholder;
  }

  @Override
  public String toString() {
    return String.format(
        "%s %s (%s) owns %s %%",
        shareholder.getStatus(),
        shareholder.getName(),
        shareholder.getId(),
        share.multiply(BigDecimal.valueOf(100)).toPlainString()
    );
  }
}
