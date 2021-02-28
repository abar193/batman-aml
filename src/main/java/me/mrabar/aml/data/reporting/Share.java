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

package me.mrabar.aml.data.reporting;

import me.mrabar.aml.data.graph.LegalEntity;

import java.math.BigDecimal;

/**
 * DTO to store info "how many shares from this company is owned".
 */
public class Share {
  private LegalEntity entity;
  private BigDecimal share;

  public Share(LegalEntity entity, BigDecimal share) {
    this.entity = entity;
    this.share = share;
  }

  // It's my project, I do what I want
  public Share(BigDecimal share, LegalEntity entity) {
    this.entity = entity;
    this.share = share;
  }

  public Share(LegalEntity entity) {
    this.entity = entity;
    this.share = null;
  }

  public BigDecimal getShare() {
    return share;
  }

  public LegalEntity getEntity() {
    return entity;
  }

  public void setShare(BigDecimal share) {
    this.share = share;
  }

  public void setEntity(LegalEntity entity) {
    this.entity = entity;
  }

  @Override
  public String toString() {
    if (share == null) {
      return String.format("owns %s (%s)", entity.getName(), entity.getId());
    }
    return String.format("%s %% of %s (%s)",
                         share.multiply(BigDecimal.valueOf(100)).toPlainString(),
                         entity.getName(),
                         entity.getId()
    );
  }
}
