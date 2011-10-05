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

import com.google.api.client.discovery.Discovery;
import com.google.api.client.discovery.wireformat.DiscoveryDocument;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Tests for all type specific behavior.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class AllTypesTest extends TestCase {

  private Map<String, DiscoveryType> allTypes;

  @Override
  public void setUp() throws Exception {
    DiscoveryDocument doc =
      new Gson().fromJson(new InputStreamReader(
          new FileInputStream("src/test/resources/all-types.json")),
          DiscoveryDocument.class);
    Discovery discovery = new Discovery(doc);
    allTypes =
        discovery.getSchemas().entrySet().iterator().next().getValue().getObject().getProperties();
  }

  public void testEnum() {
    DiscoveryType unknown = allTypes.get("enumType");
    assertEquals(DiscoveryType.BaseType.STRING, unknown.getBaseType());

    try {
      // Try to cast it to something that it is not.
      unknown.getInteger();
      fail();
    } catch (DiscoveryTypeException e) {
      // Intentionally blank
    }

    StringType typed = unknown.getString();
    assertTrue(typed.isEnum());
    assertEquals(ImmutableList.of("Value 1.", "Value 2."), typed.getEnumDescriptions());
    assertEquals(ImmutableList.of("VALUE1", "VALUE2"), typed.getEnumValues());
    assertEquals("VALUE2", typed.getDefault());
    assertEquals(null, typed.getFormat());
    assertEquals(null, typed.getPattern());
  }

  public void testString() {
    DiscoveryType unknown = allTypes.get("stringType");
    assertEquals(DiscoveryType.BaseType.STRING, unknown.getBaseType());

    StringType typed = unknown.getString();
    assertEquals("[0-9]+", typed.getPattern());
    assertEquals(StringType.Format.DATE_TIME, typed.getFormat());
    assertEquals(false, typed.isEnum());
  }

  public void testInteger() {
    DiscoveryType unknown = allTypes.get("intType");
    assertEquals(DiscoveryType.BaseType.INTEGER, unknown.getBaseType());

    IntegerType typed = unknown.getInteger();
    assertEquals(new Integer(10), typed.getMinimum());
    assertEquals(new Integer(20), typed.getMaximum());
    assertEquals(new Integer(15), typed.getDefault());
    assertEquals(IntegerType.Format.INT32, typed.getFormat());
  }

  public void testPlainInteger() {
    DiscoveryType unknown = allTypes.get("plainIntType");
    assertEquals(DiscoveryType.BaseType.INTEGER, unknown.getBaseType());

    IntegerType typed = unknown.getInteger();
    assertEquals(null, typed.getMinimum());
    assertEquals(null, typed.getMaximum());
    assertEquals(null, typed.getDefault());
    assertEquals(null, typed.getFormat());
  }

  public void testNumber() {
    DiscoveryType unknown = allTypes.get("numType");
    assertEquals(DiscoveryType.BaseType.NUMBER, unknown.getBaseType());

    NumberType typed = unknown.getNumber();
    assertEquals(new Double(10.0), typed.getMinimum());
    assertEquals(new Double(20.0), typed.getMaximum());
    assertEquals(new Double(3.1415), typed.getDefault());
    assertEquals(NumberType.Format.DOUBLE, typed.getFormat());
  }

  public void testPlainNumber() {
    DiscoveryType unknown = allTypes.get("plainNumType");
    assertEquals(DiscoveryType.BaseType.NUMBER, unknown.getBaseType());

    NumberType typed = unknown.getNumber();
    assertEquals(null, typed.getMinimum());
    assertEquals(null, typed.getMaximum());
    assertEquals(null, typed.getDefault());
    assertEquals(null, typed.getFormat());
  }

  public void testObject() {
    DiscoveryType unknown = allTypes.get("objType");
    assertEquals(DiscoveryType.BaseType.OBJECT, unknown.getBaseType());

    ObjectType typed = unknown.getObject();

    Map<String, DiscoveryType> props = typed.getProperties();
    assertEquals(Sets.newHashSet("prop1", "prop2"), props.keySet());
    assertEquals(DiscoveryType.BaseType.STRING, props.get("prop1").getBaseType());
    assertEquals(DiscoveryType.BaseType.INTEGER, props.get("prop2").getBaseType());

    assertEquals(null, typed.getAdditionalPropertyType());
  }

  public void testMap() {
    DiscoveryType unknown = allTypes.get("mapType");
    assertEquals(DiscoveryType.BaseType.OBJECT, unknown.getBaseType());

    ObjectType typed = unknown.getObject();
    assertEquals(null, typed.getProperties());
    assertEquals(DiscoveryType.BaseType.STRING, typed.getAdditionalPropertyType().getBaseType());
  }

  public void testArray() {
    DiscoveryType unknown = allTypes.get("arrayType");
    assertEquals(DiscoveryType.BaseType.ARRAY, unknown.getBaseType());

    ArrayType typed = unknown.getArray();
    assertEquals(DiscoveryType.BaseType.INTEGER, typed.getElementType().getBaseType());
  }

  public void testBoolean() {
    DiscoveryType unknown = allTypes.get("boolType");
    assertEquals(DiscoveryType.BaseType.BOOLEAN, unknown.getBaseType());

    BooleanType typed = unknown.getBoolean();
    assertEquals(Boolean.FALSE, typed.getDefault());
  }

  public void testNull() {
    DiscoveryType unknown = allTypes.get("nullType");
    assertEquals(DiscoveryType.BaseType.NULL, unknown.getBaseType());

    NullType typed = unknown.getNull();
  }

  public void testAny() {
    DiscoveryType unknown = allTypes.get("anyType");
    assertEquals(DiscoveryType.BaseType.ANY, unknown.getBaseType());

    AnyType typed = unknown.getAny();
  }

}
