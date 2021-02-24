package me.mrabar.aml.data.me.mrabar.aml.engine;

import me.mrabar.aml.data.ClientStatus;
import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.Person;
import me.mrabar.aml.engine.BatEngine;
import me.mrabar.aml.engine.BatRoot;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BatEngineTest {
  @Test
  public void testInit() {
    BatEngine be = new BatEngine();
    be.init();

    be.storeEntity(new LegalEntity("L1", "Entity 1"));
    be.storeEntity(new LegalEntity("L2", "Entity 2"));
    be.storeEntity(new LegalEntity("L3", "Entity 3"));

    be.storePerson(new Person("P1", "Person First",  ClientStatus.GREEN));
    be.storePerson(new Person("P2", "Person First",  ClientStatus.YELLOW));
    be.storePerson(new Person("P3", "Person First",  ClientStatus.RED));

    be.linkPersonAndBusiness("P1", "L1", BigDecimal.valueOf(100));

    be.linkPersonAndBusiness("P1", "L2", BigDecimal.valueOf(20));
    be.linkPersonAndBusiness("P2", "L2", BigDecimal.valueOf(80));

    be.linkPersonAndBusiness("P1", "L3", BigDecimal.valueOf(5));
    be.linkPersonAndBusiness("P2", "L3", BigDecimal.valueOf(45));
    be.linkPersonAndBusiness("P3", "L3", BigDecimal.valueOf(50));

    be.shutdown();
  }

  @Test
  public void testVerifySaved() {
    BatEngine be = new BatEngine();
    be.init();
    BatRoot br = be.debugRoot();

    assertTrue(br.hasPerson("P1"));
    assertTrue(br.hasPerson("P2"));
    assertTrue(br.hasPerson("P3"));
    assertFalse(br.hasPerson("P4"));

    assertTrue(br.hasEntity("L1"));
    assertTrue(br.hasEntity("L2"));
    assertTrue(br.hasEntity("L3"));
    assertFalse(br.hasEntity("L4"));

    Person p = br.getPerson("P3");
    LegalEntity le = br.getEntity("L3");

    br.getEntity("L3").getShares().forEach(s -> System.out.println(s));
    br.getEntity("L3").getOwners().forEach(s -> System.out.println(s));
  }
}
