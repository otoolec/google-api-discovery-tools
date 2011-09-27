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

import com.google.api.client.discovery.wireformat.DiscoveryDocument.Schema;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Base type from which all Discovery types descend.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public abstract class DiscoveryType {
  /**
   * More specific type that can be represented by a {@link DiscoveryType}.
   */
  public enum BaseType {
    STRING("string"),
    NUMBER("number"),
    INTEGER("integer"),
    BOOLEAN("boolean"),
    OBJECT("object"),
    ARRAY("array"),
    NULL("null"),
    ANY("any");

    private static final Map<String, BaseType> jsonTypeToBaseType = Maps.newHashMap();

    static {
      for (BaseType type : values()) {
        jsonTypeToBaseType.put(type.getJsonType(), type);
      }
    }

    /**
     * Returns the enumerated type based on the JSON type string.
     */
    public static BaseType getTypeForJsonType(String jsonType) {
      return jsonTypeToBaseType.get(jsonType);
    }

    private String jsonType;

    private BaseType(String jsonType) {
      this.jsonType = jsonType;
    }

    private String getJsonType() {
      return jsonType;
    }
  }

  /** All named top level schemas from the discovery document. */
  protected Map<String, Schema> topLevelSchemas;

  /** The schema node being wrapped by this type. */
  protected Schema schemaNode;

  /** Lower level type. */
  protected BaseType type;

  /**
   * Reusable function to convert a schema node to the corresponding wrapped
   * {@link DiscoveryType}.
   */
  protected Function<Schema, DiscoveryType> schemaToDisicoveryType =
      new Function<Schema, DiscoveryType>() {
        public DiscoveryType apply(Schema input) {
          return createTypeFromSchemaNode(input, topLevelSchemas);
        }
      };

  /**
   * Convert a schema node to the corresponding wrapped Discovery type.
   *
   * @param node Schema node to convert to an {@link DiscoveryType}.
   * @param topLevelSchemas All named top level schemas.
   * @return The corresponding wrapped representation.
   */
  public static DiscoveryType createTypeFromSchemaNode(
      Schema node, Map<String, Schema> topLevelSchemas) {

    if (node == null) {
      return null;
    }

    Schema realSchema = dereferenceSchema(node, topLevelSchemas);
    BaseType type = BaseType.getTypeForJsonType(realSchema.getType());

    DiscoveryType newTypeWrapper;
    switch (type) {
      case ANY:
        newTypeWrapper = new AnyType();
        break;
      case ARRAY:
        newTypeWrapper = new ArrayType();
        break;
      case BOOLEAN:
        newTypeWrapper = new BooleanType();
        break;
      case INTEGER:
        newTypeWrapper = new IntegerType();
        break;
      case NULL:
        newTypeWrapper = new NullType();
        break;
      case NUMBER:
        newTypeWrapper = new NumberType();
        break;
      case OBJECT:
        newTypeWrapper = new ObjectType();
        break;
      case STRING:
        newTypeWrapper = new StringType();
        break;
      default:
        throw new DiscoveryTypeException("Schema node does not contain a type identifier");
    }

    newTypeWrapper.type = type;
    newTypeWrapper.topLevelSchemas = topLevelSchemas;
    newTypeWrapper.schemaNode = realSchema;

    return newTypeWrapper;
  }

  private static Schema dereferenceSchema(
      Schema possiblyAReference, Map<String, Schema> topLevelSchemas) {
    if (possiblyAReference.getRef() != null) {
      return dereferenceSchema(topLevelSchemas.get(possiblyAReference.getRef()), topLevelSchemas);
    }
    return possiblyAReference;
  }

  /**
   * Returns the ID of this Object if one is available.
   */
  public String getId() {
    return schemaNode.getId();
  }

  /**
   * Returns the base DiscoveryType of this instance. Use this to determine
   * which type can be extracted using get<Type>() methods.
   */
  public BaseType getBaseType() {
    return type;
  }

  /**
   * Get the description string associated with this type.
   */
  public String getDescription() {
    return schemaNode.getDescription();
  }

  /**
   * Return whether this method is required or {@code null} if unspecified.
   */
  public Boolean getRequired() {
    return schemaNode.getRequired();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(schemaNode, topLevelSchemas);
  }

  @Override
  public boolean equals(Object rhs) {
    if (rhs instanceof DiscoveryType) {
      DiscoveryType rhsTyped = (DiscoveryType) rhs;
      return Objects.equal(schemaNode, rhsTyped.schemaNode)
          && Objects.equal(topLevelSchemas, rhsTyped.topLevelSchemas);
    } else {
      return false;
    }
  }

  // Defaults which prevent fetching from the wrong type.

  public StringType getString() {
    throw new DiscoveryTypeException("Not an instance of StringType");
  }

  public NumberType getNumber() {
    throw new DiscoveryTypeException("Not an instance of NumberType");
  }

  public IntegerType getInteger() {
    throw new DiscoveryTypeException("Not an instance of IntegerType");
  }

  public BooleanType getBoolean() {
    throw new DiscoveryTypeException("Not an instance of BooleanType");
  }

  public ObjectType getObject() {
    throw new DiscoveryTypeException("Not an instance of ObjectType");
  }

  public ArrayType getArray() {
    throw new DiscoveryTypeException("Not an instance of ArrayType");
  }

  public NullType getNull() {
    throw new DiscoveryTypeException("Not an instance of NullType");
  }

  public AnyType getAny() {
    throw new DiscoveryTypeException("Not an instance of AnyType");
  }
}
