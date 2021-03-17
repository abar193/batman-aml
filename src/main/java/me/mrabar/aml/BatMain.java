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

package me.mrabar.aml;

import io.helidon.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.metrics.MetricsSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import me.mrabar.aml.rest.BatService;

/**
 * ... because <p/>
 * ... because I'm <p/>
 * ... BECAUSE I'M BAT MAIN <p/>
 * Get it? <p/>
 *
 *
 * Anyway, half this class is copied from Helidons' quickstart example, since I'm not fixing what is not broken.
 */
public class BatMain {
  public static void main(String[] args) {
    LogConfig.configureRuntime();

    Config config = Config.create();

    WebServer server = WebServer.builder(createRouting())
        .addMediaSupport(JsonpSupport.create())
        .config(config.get("server"))
        .port(8087)
        .build();

    server.start()
        .thenAccept(ws -> {
          System.out.println("Greeting, master Wayne, your service at http://localhost:" + ws.port()
                                 + "/aml is ready");
          ws.whenShutdown().thenRun(() -> System.out.println("TIME TO SERVE SOME JUSTICE!"));
        })
        .exceptionally(t -> {
          System.err.println("Startup failed: " + t.getMessage());
          t.printStackTrace(System.err);
          return null;
        });

  }

  private static Routing createRouting() {
    HealthSupport health = HealthSupport.builder()
        .addLiveness(HealthChecks.healthChecks())
        .build();

    return Routing.builder()
        .register(health)
        .register(MetricsSupport.create())
        .register("/aml", new BatService())
        .build();
  }
}
