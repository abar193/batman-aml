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

import io.helidon.common.http.Http;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import me.mrabar.aml.data.reporting.OwnersReport;
import me.mrabar.aml.data.reporting.PersonReport;
import me.mrabar.aml.engine.BatEngine;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import java.util.Collections;
import java.util.logging.Logger;

public class BatService implements Service {
  private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

  private static final Logger LOGGER = Logger.getLogger(BatService.class.getName());
  private static final BatEngine batEngine = BatEngine.getInstance();

  @Override
  public void update(Routing.Rules rules) {
    rules.get("/", this::index)
        .get("/person/{pid}/companies", this::getPersonReport)
        .get("/person/{pid}/shares", this::getPersonOwnership)
        .get("/entity/{eid}/owners", this::getOwnershipReport);
  }

  private void index(ServerRequest request, ServerResponse response) {
    response.status(200).headers().add("Content-Type", "text/html; charset=UTF-8");
    response.send("<html><head><title>BATMAN AML</title></head><body><ul>" +
                      "<li><a href='/aml/person/:id/shares'>Calculate exact shares of a person</a></li>" +
                      "<li><a href='/aml/person/:id/companies'>Just list all companies a person owns</a></li>" +
                      "<li><a href='/aml/entity/:id/owners'>Find all owners of a company</a></li></body></html>");
  }

  private void reportPerson(ServerRequest request, ServerResponse response, boolean full) {
    String pid = request.path().param("pid");

    if (!batEngine.containsPerson(pid)) {
      response.status(Http.Status.NOT_FOUND_404).send(JSON.createObjectBuilder()
                                                          .add("error",
                                                               "Person not found, your invalid request has been " +
                                                                   "reported to the authorities" +
                                                                   " and your manager"
                                                          )
                                                          .build());
      return;
    }

    PersonReport pr = (full) ? batEngine.personOwnership(pid) : batEngine.personCompanies(pid);

    response.status(Http.Status.OK_200).send(BatUtils.bindReport(JSON, pr));
  }

  private void getPersonReport(ServerRequest request, ServerResponse response) {
    reportPerson(request, response, false);
  }

  private void getPersonOwnership(ServerRequest request, ServerResponse response) {
    reportPerson(request, response, true);
  }

  private void getOwnershipReport(ServerRequest request, ServerResponse response) {
    String eid = request.path().param("eid");

    if (!batEngine.containsEntity(eid)) {
      response.status(Http.Status.NOT_FOUND_404)
          .send(JSON.createObjectBuilder().add("error", "Company not found").build());
      return;
    }

    OwnersReport or = batEngine.companyOwners(eid);

    response.status(Http.Status.OK_200).send(BatUtils.bindReport(JSON, or));
  }
}
