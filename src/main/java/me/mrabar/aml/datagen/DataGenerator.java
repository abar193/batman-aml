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
import me.mrabar.aml.data.graph.LegalEntity;
import me.mrabar.aml.data.graph.Person;
import me.mrabar.aml.engine.BatEngine;

import java.math.BigDecimal;
import java.util.List;

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

//  private static int companyCounter = 1;
//  private static int clientCounter = 1;

}
