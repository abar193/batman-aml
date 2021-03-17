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

package me.mrabar.aml.engine;

import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.Person;
import me.mrabar.aml.data.reporting.OwnersReport;
import me.mrabar.aml.data.reporting.PersonReport;

import java.math.BigDecimal;

public interface BatEngine {

  /**
   * @return initialized BatEngineInterface implementation
   */
  static BatEngine getInstance() {
    return BatEngineImpl.getInstance();
  }

  /**
   * Updates BatRoot by storing Person in a data storage.
   */
  void storePerson(Person person);

  /**
   * Updates BatRoot by storing LegalEntity in a data storage.
   */
  void storeEntity(LegalEntity entity);

  boolean containsPerson(String pid);
  boolean containsEntity(String eid);

  /**
   * Links a Person, identified by personId to a LegalEntity, identified by entityId.
   * Creates a new OwnershipEdge, where shareholder is a Person, and
   *
   * @throws IllegalArgumentException if Person or LegalEntity with said personId or entityId cannot be found.
   */
  void linkPersonAndBusiness(String personId, String entityId, BigDecimal percentage);

  void linkBusinesses(String ownerId, String targetId, BigDecimal percentage);

  /**
   * Returns a report with all the companies that this Person has shares in.
   * Does not determine actual amount of shares owned (Share.share == null).
   * <p>
   * Uses BFS and should be faster than personOwnership().
   *
   * @throws IllegalArgumentException if Person with such pid is not found.
   */
  PersonReport personCompanies(String pid);

  /**
   * Reports actual percentage of shares owned in each company by this person.
   * <p/>
   * Unlike personCompanies() this method uses recursive DFS algorithm and examines every single path
   * without skipping a single node. It might get slower (and we risk StackOverflowException), but it
   * is the easies way to ensure that the result is accurate.
   *
   * @throws IllegalArgumentException if Person with such pid is not found.
   */
  PersonReport personOwnership(String pid);

  /**
   * Calculates percentage of shares owned by each Person, having a stake in this business.
   * <p/>
   * Like personOwnership(), this method performs a recursive DFS and examines every single path -
   * it just goes in the opposite way and terminates only upon finding a Person.
   *
   * @throws IllegalArgumentException if LegalEntity with such cid is not found.
   */
  OwnersReport companyOwners(String cid);

  /**
   * According to MicroStream documentation, calling this method is optional, and nothing bad will
   * happen if we forget.
   */
  void shutdown();
}
