/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.discovery.types;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Type that represents floating point numerical data.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class NumberType extends DiscoveryType {
  /**
   * More specific type information for this type.
   */
  public enum Format {
    /**
     * A double-precision 64-bit IEEE 754 floating point.
     */
    DOUBLE("double"),

    /**
     * A single-precision 32-bit IEEE 754 floating point.
     */
    FLOAT("float");

    private static final Map<String, Format> jsonFormatToEnum = Maps.newHashMap();

    static {
      for (Format type : values()) {
        jsonFormatToEnum.put(type.getJsonFormat(), type);
      }
    }

    /**
     * Returns the enumerated type based on the JSON type string.
     */
    public static Format getEnumForJsonFormat(String jsonFormat) {
      return jsonFormatToEnum.get(jsonFormat);
    }

    private String jsonFormat;

    private Format(String jsonType) {
      this.jsonFormat = jsonType;
    }

    private String getJsonFormat() {
      return jsonFormat;
    }
  }


  @Override
  public NumberType getNumber() {
    return this;
  }

  /**
   * Returns the maximum possible value that can be used for this type or {@code
   * null} if none was specified.
   */
  public Double getMaximum() {
    String max = schemaNode.getMaximum();
    return max == null ? null : Double.parseDouble(max);
  }

  /**
   * Returns the minimum possible value that can be used for this type or {@code
   * null} if none was specified.
   */
  public Double getMinimum() {
    String min = schemaNode.getMinimum();
    return min == null ? null : Double.parseDouble(min);
  }

  /**
   * Returns the default value or {@code null} if none was specified.
   */
  public Double getDefault() {
    String def = schemaNode.getDefault();
    return def == null ? null : Double.parseDouble(def);
  }

  /**
   * Additional information that helps define the specific format of this type.
   */
  public Format getFormat() {
    String jsonFormat = schemaNode.getFormat();
    return jsonFormat == null ? null : Format.getEnumForJsonFormat(jsonFormat);
  }
}
