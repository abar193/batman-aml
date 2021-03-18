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
import one.microstream.reference.Lazy;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Root class for MicroStream.
 */
public class BatRoot {
  private Map<String, Lazy<Person>> physicalEntities = new TreeMap<>();
  private Map<String, Lazy<LegalEntity>> legalEntities = new TreeMap<>();

  Map<String, Lazy<Person>> savePerson(Person person) {
    physicalEntities.put(person.getId(), Lazy.Reference(person));
    return physicalEntities;
  }

  Map<String, Lazy<LegalEntity>> saveEntity(LegalEntity entity) {
    legalEntities.put(entity.getId(), Lazy.Reference(entity));
    return legalEntities;
  }

  public boolean isEmpty() {
    return physicalEntities.isEmpty() && legalEntities.isEmpty();
  }

  public boolean hasPerson(String id) {
    return physicalEntities.containsKey(id);
  }

  public boolean hasEntity(String id) {
    return legalEntities.containsKey(id);
  }

  public Person getPerson(String id) {
    if(!hasPerson(id)) {
      throw new IllegalArgumentException(id);
    }

    return physicalEntities.get(id).get();
  }

  public LegalEntity getEntity(String id) {
    if(!hasEntity(id)) {
      throw new IllegalArgumentException(id);
    }

    return legalEntities.get(id).get();
  }

  public void printDebugInfo() {
    System.out.format("Bat Root with %d phyical entities and %d legal entities%n",
                      physicalEntities.size(),
                      legalEntities.size());
  }

  public Map<String, Lazy<Person>> batchInsertPersons(List<Person> personList) {
    physicalEntities.putAll(personList.stream()
                                .collect(Collectors.toMap(Person::getId, Lazy::Reference)));
    return physicalEntities;
  }

  public Map<String, Lazy<LegalEntity>> batchInsertEntities(List<LegalEntity> personList) {
    legalEntities.putAll(personList.stream()
                                .collect(Collectors.toMap(LegalEntity::getId, Lazy::Reference)));
    return legalEntities;
  }


}
