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

package me.mrabar.aml.datagen;

import me.mrabar.aml.data.ClientStatus;
import me.mrabar.aml.data.graph.AbstractEntity;
import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.Person;
import me.mrabar.aml.engine.BatEngine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is not a good example of how I normally code. <p/>
 *
 * It is a good example of how I code when I have only a couple of hours left before the demo,
 * and I know that I'm writing a one-time code.
 */
public class DataGenerator {
  public static void generateBatVerseNodes(final BatEngine be) {
    be.storePerson(new Person("A", "Bruce Wayne", ClientStatus.GREEN));
    be.storePerson(new Person("B", "Alfred Pennyworth", ClientStatus.GREEN));
    be.storePerson(new Person("C", "James Gordon", ClientStatus.GREEN));
    be.storePerson(new Person("D", "Barbara Gordon", ClientStatus.GREEN));
    be.storePerson(new Person("E", "Dick Grayson", ClientStatus.GREEN));

    be.storePerson(new Person("F", "Selina Kyle", ClientStatus.UNKNOWN));

    be.storePerson(new Person("G", "Harley Quinn", ClientStatus.RED));
    be.storePerson(new Person("H", "Ra's al Ghul", ClientStatus.BLACK));
    be.storePerson(new Person("I", "Bane", ClientStatus.BLACK));
    be.storePerson(new Person("J", "Oswald Chesterfield Kapelput", ClientStatus.RED));

    be.storePerson(new Person("K", "Talia al Ghul", ClientStatus.BLACK));
    be.storePerson(new Person("L", "Miranda Tate", ClientStatus.GREEN));

    be.storeEntity(new LegalEntity("WNE", "Wayne Enterprises"));

    be.storeEntity(new LegalEntity("ROT", "Rich Orphans Trust"));
    be.storeEntity(new LegalEntity("ASV", "Alley Shooting Victims Fund"));
    be.storeEntity(new LegalEntity("FMF", "Flying Mammals Farm"));
    be.storeEntity(new LegalEntity("ICI", "Innocent Cosplay Industries"));
    be.storeEntity(new LegalEntity("GDI", "Good Deeds Inc"));

    be.linkBusinesses("ROT", "WNE", BigDecimal.valueOf(20));
    be.linkBusinesses("ASV", "WNE", BigDecimal.valueOf(20));
    be.linkBusinesses("FMF", "WNE", BigDecimal.valueOf(20));
    be.linkBusinesses("ICI", "WNE", BigDecimal.valueOf(20));
    be.linkBusinesses("GDI", "WNE", BigDecimal.valueOf(20));

    be.linkPersonAndBusiness("A", "ROT", BigDecimal.valueOf(50));
    be.linkPersonAndBusiness("B", "ROT", BigDecimal.valueOf(50));

    be.linkPersonAndBusiness("A", "ASV", BigDecimal.valueOf(50));
    be.linkPersonAndBusiness("C", "ASV", BigDecimal.valueOf(50));

    be.linkPersonAndBusiness("A", "FMF", BigDecimal.valueOf(50));
    be.linkPersonAndBusiness("B", "FMF", BigDecimal.valueOf(50));

    be.linkPersonAndBusiness("A", "ICI", BigDecimal.valueOf(50));
    be.linkPersonAndBusiness("D", "ICI", BigDecimal.valueOf(50));

    be.linkPersonAndBusiness("A", "GDI", BigDecimal.valueOf(50));
    be.linkPersonAndBusiness("L", "GDI", BigDecimal.valueOf(50));

    generateGenericCompanies(be);
  }

  private static void generateGenericCompanies(BatEngine be) {
    int cmpCnt = 1;
    int id = 1;
    for(String s: List.of("E", "F", "G", "H", "I", "J", "K")) {
      for(int i = 0; i < cmpCnt; i++) {
        String cid = "GC" + id;
        be.storeEntity(new LegalEntity(cid, "Generic Company #"));
        be.linkPersonAndBusiness(s, cid, BigDecimal.valueOf(1L));
      }
    }
  }

  public static void fillMemory(BatEngine be, int simpleRecords, int bitMessy, int bigMessy) {
    random = new Random(simpleRecords);

    ExecutorService executor = Executors.newFixedThreadPool(4);
    for(int i = 0; i < simpleRecords; i++) {
      simpleCase(be);
    }
    System.out.println("Simple cases done");
    for(int i = 0; i < bitMessy; i++) {
      proxyCases(be, 5 + random.nextInt(5), 5, 15);
    }
    System.out.println("Advanced cases done");
    for(int i = 0; i < bigMessy; i++) {
      proxyCases(be, 7 + random.nextInt(5), 15, 10);
    }
  }

  private static Random random;
  private static int companyCounter = 1;
  private static int clientCounter = 1;

  private static final ClientStatus[] statuses = new ClientStatus[] {
      ClientStatus.GREEN,
      ClientStatus.YELLOW,
      ClientStatus.RED,
      ClientStatus.BLACK,
      ClientStatus.UNKNOWN
  };

  private static final String[] names = new String[] {
      // Generated with http://listofrandomnames.com/index.cfm?generated
    "Jarret", "Noe", "Dominique", "Eliseo", "Nathanial",
      "Lu", "Jonnie", "Maryrose", "Sherilyn", "Brigette",
      "Dexter", "Donya", "Jc", "Ona", "Nguyet",
      "Alta", "Kathe", "Cris", "Jayme", "Olinda",
      "Carmine", "Odette", "Rocio", "Yu", "Bar"
  };

  private static final String[] companyNames = new String[] {
      // Generated with help of https://www.fantasynamegenerators.com/company-names.php
      "Lemon", "Apple", "Banana", "Stare", "Freak", "Media", "Production", "High", "Fire",
      "House", "Pluto", "Earth", "Venus", "Rabbit", "Wolf", "Apex", "Omega", "Ghost", "Aid",
      "Security", "Micro", "Vertex", "Co", "Night"
  };

  private static ClientStatus status() {
    return statuses[clientCounter % statuses.length];
  }

  private static String name() {
    int i = clientCounter;
    int j = 0;
    StringBuilder sb = new StringBuilder("");
    while(i > 0 && j++ < 4) {
      sb.append(' ').append(names[i % names.length]);
      i /= names.length;
    }
    return sb.toString().trim();
  }

  private static String companyName() {
    int i = companyCounter;
    int j = 0;
    StringBuilder sb = new StringBuilder("");
    while(i > 0 && j++ < 4) {
      sb.append(' ').append(companyNames[i % companyNames.length]);
      i /= companyNames.length;
    }
    return sb.toString().trim();
  }

  private static Person nextPerson(BatEngine be) {
    String cid = Integer.toString(clientCounter++);
    Person p = new Person("C" + cid, name(), status());
    be.storePerson(p);
    return p;
  }

  private static LegalEntity nextEntity(BatEngine be) {
    LegalEntity le = new LegalEntity("E" + companyCounter++, companyName());
    be.storeEntity(le);
    return le;
  }

  private static void simpleCase(BatEngine be) {
    List<Person> personList = IntStream.range(0, random.nextInt(4) + 1)
        .mapToObj(i -> nextPerson(be)).collect(Collectors.toList());
    personList.forEach(be::storePerson);

    final LegalEntity le = nextEntity(be);
    be.storeEntity(le);

    BigDecimal value = BigDecimal.valueOf(1.0d / (double)personList.size());

    personList.forEach(p -> be.linkPersonAndBusiness(p.getId(), le.getId(), value));
  }

  private static void proxyCases(BatEngine be, int users, int proxies, int reals) {
    List<Person> personList = IntStream.range(0, users)
        .mapToObj(i -> nextPerson(be))
        .collect(Collectors.toList());

    List<LegalEntity> trustsAndShells = IntStream.range(0, proxies)
        .mapToObj(i -> nextEntity(be))
        .collect(Collectors.toList());

    // to avoid cross-holdings, a newer trust/shell may not own an older trust/shell
    List<AbstractEntity> allowedNodes = new ArrayList<>(personList);

    // Level 1: link proxies to each other, somehow, I don't care
    for(LegalEntity le: trustsAndShells) {
      int owners = random.nextInt(8) + 1;
      BigDecimal value = BigDecimal.valueOf(1.0d / (double)owners);

      for(int i = 0; i < owners; i++) {
        AbstractEntity ae = allowedNodes.get(random.nextInt(allowedNodes.size()));
        if(ae instanceof Person) {
          be.linkPersonAndBusiness(ae.getId(), le.getId(), value);
        } else {
          be.linkBusinesses(ae.getId(), le.getId(), value);;
        }
      }

      allowedNodes.add(le);
    }

    // Level 2: Now that ownership structure of shell-companies is messy enough, time to register normal companies
    for(int i = 0; i < reals; i++) {
      LegalEntity le = nextEntity(be);
      int owners = random.nextInt(5) + 1;
      BigDecimal value = BigDecimal.valueOf(1.0d / (double)owners);

      for(int j = 0; j < owners; j++) {
        AbstractEntity ae = allowedNodes.get(random.nextInt(allowedNodes.size()));
        if(ae instanceof Person) {
          be.linkPersonAndBusiness(ae.getId(), le.getId(), value);
        } else {
          be.linkBusinesses(ae.getId(), le.getId(), value);;
        }
      }
    }
  }

}
