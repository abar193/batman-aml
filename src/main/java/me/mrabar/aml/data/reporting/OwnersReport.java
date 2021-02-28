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

import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.OwnershipEdge;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lists all the Persons - (beneficial) owners - that have shares of this company.
 */
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
    return String.format(
        "Ownership report for %s (%s):\n\t%s",
        business.getName(),
        business.getId(),
        owners.stream()
            .sorted(Comparator.comparing(OwnerInfo::getShare).reversed())
            .map(OwnerInfo::toString)
            .collect(Collectors.joining("\n\t"))
    );
  }

  public String beneficialOwners(BigDecimal threshold) {
    return String.format(
        "Ownership report for %s (%s):\n\t%s",
        business.getName(),
        business.getId(),
        owners.stream()
            .filter(oi -> oi.getShare().compareTo(threshold) >= 0)
            .sorted(Comparator.comparing(OwnerInfo::getShare).reversed())
            .map(OwnerInfo::toString)
            .collect(Collectors.joining("\n\t"))
    );
  }
}
