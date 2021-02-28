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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Report for a person - lists all the companies this person has a stake in.
 */
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
    return String.format(
        "Report for %s (%s) - status %s:\n\t%s",
        owner.getName(),
        owner.getId(),
        owner.getStatus(),
        shares.stream().sorted(Comparator.comparing(Share::getShare).reversed()).map(Share::toString).collect(Collectors.joining("\n\t"))
    );

  }
}
