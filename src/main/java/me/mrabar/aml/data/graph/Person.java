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

package me.mrabar.aml.data.graph;

import me.mrabar.aml.data.ClientStatus;

/**
 * Real, physical person - a human, that may have some shares in any given business or company.
 */
public class Person extends AbstractEntity {
  protected final ClientStatus status;

  public Person(String id, String name, ClientStatus status) {
    super(id, name);
    this.status = status;
  }

  public Person(String id, String name) {
    super(id, name);
    this.status = ClientStatus.UNKNOWN;
  }

  public ClientStatus getStatus() {
    return status;
  }
}
