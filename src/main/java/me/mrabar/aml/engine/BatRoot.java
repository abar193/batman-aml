package me.mrabar.aml.engine;

import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.Person;
import one.microstream.reference.Lazy;

import java.util.Map;
import java.util.TreeMap;

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
}
