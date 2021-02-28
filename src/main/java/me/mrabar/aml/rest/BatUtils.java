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

package me.mrabar.aml.rest;

import me.mrabar.aml.data.graph.AbstractEntity;
import me.mrabar.aml.data.graph.Person;
import me.mrabar.aml.data.reporting.OwnerInfo;
import me.mrabar.aml.data.reporting.OwnersReport;
import me.mrabar.aml.data.reporting.PersonReport;
import me.mrabar.aml.data.reporting.Share;

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;
import java.util.Optional;

public class BatUtils {
  public static JsonObjectBuilder bindAny(JsonObjectBuilder json, AbstractEntity ae) {
    json = json
        .add("id", ae.getId())
        .add("name", ae.getName());
    if(ae instanceof Person) {
      json.add("status", ((Person) ae).getStatus().toString());
    }
    return json;
  }

  public static JsonObject bindObject(JsonBuilderFactory builder, Share share) {
    return bindAny(builder.createObjectBuilder(), share.getEntity())
        .add("share", Optional.ofNullable(share.getShare()).map(BigDecimal::toPlainString).orElse("-"))
        .build();
  }

  public static JsonObject bindObject(JsonBuilderFactory builder, OwnerInfo oi) {
    return bindAny(builder.createObjectBuilder(), oi.getShareholder())
        .add("share", Optional.ofNullable(oi.getShare()).map(BigDecimal::toPlainString).orElse("-"))
        .build();
  }

  public static JsonObject bindReport(JsonBuilderFactory builder, PersonReport pr) {
    JsonArrayBuilder arrayBuilder = builder.createArrayBuilder();
    pr.getShares().forEach(c -> arrayBuilder.add(bindObject(builder, c)));

    return bindAny(builder.createObjectBuilder(), pr.getOwner())
        .add("shares", arrayBuilder.build())
        .build();
  }

  public static JsonObject bindReport(JsonBuilderFactory builder, OwnersReport or) {
    JsonArrayBuilder arrayBuilder = builder.createArrayBuilder();
    or.getOwners().forEach(o -> arrayBuilder.add(bindObject(builder, o)));

    return bindAny(builder.createObjectBuilder(), or.getBusiness())
        .add("owners", arrayBuilder.build())
        .build();
  }

}
