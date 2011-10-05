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

import java.util.List;
import java.util.Map;

/**
 * Class that describes a string variable.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class StringType extends DiscoveryType {
  /**
   * More specific type information for this type.
   */
  public enum Format {
    /**
     * A base64-encoded string of bytes.
     */
    BYTE("byte"),

    /**
     * An RFC339 date in the format YYYY-MM-DD. Defined in the JSON Schema spec.
     */
    DATE("date"),

    /**
     * An RFC3339 timestamp in UTC time. This is formatted as
     * YYYY-MM-DDThh:mm:ss.fffZ. The milliseconds portion (".fff") is optional.
     * Defined in the JSON Schema spec.
     */
    DATE_TIME("date-time"),

    /**
     * A 64-bit signed integer. It has a minimum value of
     * -9,223,372,036,854,775,808 and a maximum value of
     * 9,223,372,036,854,775,807 (inclusive).
     */
    INT64("int64"),

    /**
     * A 64-bit unsigned integer. It has a minimum value of 0 and a maximum
     * value of (2^64)-1 (inclusive).
     */
    UINT64("uint64");

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
  public StringType getString() {
    return this;
  }

  /**
   * Regular expression against which a value can be validated.
   */
  public String getPattern() {
    return schemaNode.getPattern();
  }

  /**
   * Returns all possible values that can be passed as a value for this
   * enumeration.
   */
  public List<String> getEnumValues() {
    return schemaNode.getEnumValues();
  }

  /**
   * Returns descriptions of the values that can be passed for this enumeration.
   */
  public List<String> getEnumDescriptions() {
    return schemaNode.getEnumDescriptions();
  }

  /**
   * Returns whether or not this field is an enumeration.
   */
  public boolean isEnum() {
    return schemaNode.getEnumValues() != null;
  }

  /**
   * Returns the default value.
   */
  public String getDefault() {
    return schemaNode.getDefault();
  }

  /**
   * Additional information that helps define the specific format of this type.
   */
  public Format getFormat() {
    String jsonFormat = schemaNode.getFormat();
    return jsonFormat == null ? null : Format.getEnumForJsonFormat(jsonFormat);
  }
}
