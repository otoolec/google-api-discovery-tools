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
 * Type that represents integral numerical data.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class IntegerType extends DiscoveryType {
  /**
   * More specific type information for this type.
   */
  public enum Format {
    /**
     * A 32-bit signed integer. It has a minimum value of -2,147,483,648 and a
     * maximum value of 2,147,483,647 (inclusive).
     */
    INT32("int32"),

    /**
     * A 32-bit unsigned integer. It has a minimum value of 0 and a maximum
     * value of 4,294,967,295 (inclusive).
     */
    UINT32("uint32");

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
  public IntegerType getInteger() {
    return this;
  }

  /**
   * Returns the minimum possible value that can be used for this type or {@code
   * null} if none was specified.
   */
  public Integer getMinimum() {
    String min = schemaNode.getMinimum();
    return min == null ? null : Integer.parseInt(min);
  }

  /**
   * Returns the maximum possible value that can be used for this type or {@code
   * null} if none was specified.
   */
  public Integer getMaximum() {
    String max = schemaNode.getMaximum();
    return max == null ? null : Integer.parseInt(max);
  }

  /**
   * Returns the default value.
   */
  public Integer getDefault() {
    String def = schemaNode.getDiscoveryDefault();
    return def == null ? null : Integer.parseInt(def);
  }

  /**
   * Additional information that helps define the specific format of this type.
   */
  public Format getFormat() {
    String jsonFormat = schemaNode.getFormat();
    return jsonFormat == null ? null : Format.getEnumForJsonFormat(jsonFormat);
  }
}
