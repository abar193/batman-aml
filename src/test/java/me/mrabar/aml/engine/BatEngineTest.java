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

import me.mrabar.aml.data.ClientStatus;
import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.Person;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BatEngineTest {
//  @Test
//  public void testInit() {
//    // Just test if my structure is even working
//    BatEngine be = BatEngine.getInstance();
//
//    be.storeEntity(new LegalEntity("L1", "Entity 1"));
//    be.storeEntity(new LegalEntity("L2", "Entity 2"));
//    be.storeEntity(new LegalEntity("L3", "Entity 3"));
//
//    be.storePerson(new Person("P1", "Person First", ClientStatus.GREEN));
//    be.storePerson(new Person("P2", "Person Second", ClientStatus.YELLOW));
//    be.storePerson(new Person("P3", "Person Third", ClientStatus.RED));
//
//    be.linkPersonAndBusiness("P1", "L1", BigDecimal.valueOf(1));
//
//    be.linkPersonAndBusiness("P1", "L2", BigDecimal.valueOf(0.20));
//    be.linkPersonAndBusiness("P2", "L2", BigDecimal.valueOf(0.80));
//
//    be.linkPersonAndBusiness("P1", "L3", BigDecimal.valueOf(0.05));
//    be.linkPersonAndBusiness("P2", "L3", BigDecimal.valueOf(0.45));
//    be.linkPersonAndBusiness("P3", "L3", BigDecimal.valueOf(0.50));
//
//    be.shutdown();
//  }
//
//  @Test
//  public void testVerifySaved() {
//    BatEngine be = BatEngine.getInstance();
//
//    assertTrue(be.containsPerson("P1"));
//    assertTrue(be.containsPerson("P2"));
//    assertTrue(be.containsPerson("P3"));
//    assertFalse(be.containsPerson("P4"));
//
//    assertTrue(be.containsEntity("L1"));
//    assertTrue(be.containsEntity("L2"));
//    assertTrue(be.containsEntity("L3"));
//    assertFalse(be.containsEntity("L4"));
//  }
//
//  @Test
//  public void createWayneEnterprises() {
//    BatEngine be = BatEngine.getInstance();
//    for (String s : new String[]{"L4", "L5", "L6", "L7", "L8", "L9", "L10"}) {
//      be.storeEntity(new LegalEntity(s, s));
//    }
//    for (String s : new String[]{"P4", "P5", "P6"}) {
//      be.storePerson(new Person(s, s, ClientStatus.GREEN));
//    }
//    be.storePerson(new Person("P7", "Bane", ClientStatus.RED));
//
//    be.linkBusinesses("L5", "L4", BigDecimal.valueOf(0.25));
//    be.linkBusinesses("L6", "L4", BigDecimal.valueOf(0.25));
//    be.linkBusinesses("L7", "L4", BigDecimal.valueOf(0.25));
//    be.linkBusinesses("L8", "L4", BigDecimal.valueOf(0.25));
//
//    be.linkBusinesses("L9", "L5", BigDecimal.valueOf(0.70));
//    be.linkPersonAndBusiness("P4", "L5", BigDecimal.valueOf(0.30));
//
//    be.linkBusinesses("L9", "L6", BigDecimal.valueOf(0.70));
//    be.linkPersonAndBusiness("P5", "L6", BigDecimal.valueOf(0.30));
//
//    be.linkBusinesses("L10", "L7", BigDecimal.valueOf(0.45));
//    be.linkPersonAndBusiness("P6", "L7", BigDecimal.valueOf(0.55));
//
//    be.linkBusinesses("L9", "L8", BigDecimal.valueOf(0.90));
//    be.linkPersonAndBusiness("P5", "L8", BigDecimal.valueOf(0.10));
//
//    be.linkBusinesses("L10", "L9", BigDecimal.valueOf(1));
//
//    be.linkPersonAndBusiness("P7", "L10", BigDecimal.valueOf(1));
//
//    be.shutdown();
//  }
//
//  @Test
//  public void testPersonCompanies() {
//    BatEngine be = BatEngine.getInstance();
//    for (int i = 0; i < 7; i++) {
//      System.out.println(be.personCompanies("P" + (i + 1)));
//    }
//  }
//
//  @Test
//  public void testPersonOwnership() {
//    BatEngine be = BatEngine.getInstance();
//    for (int i = 0; i < 7; i++) {
//      System.out.println(be.personOwnership("P" + (i + 1)));
//    }
//  }
//
//  @Test
//  public void testCompanyOwners() {
//    BatEngine be = BatEngine.getInstance();
//    for (int i = 0; i < 10; i++) {
//      System.out.println(be.companyOwners("L" + (i + 1)));
//    }
//  }
}
