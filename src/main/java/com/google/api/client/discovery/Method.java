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

package com.google.api.client.discovery;

import com.google.api.client.discovery.types.DiscoveryType;
import com.google.api.client.discovery.wireformat.DiscoveryDocument;
import com.google.api.client.discovery.wireformat.DiscoveryDocument.Schema;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class which describes a method available for this particular API.
 *
 * @author moshenko@google.com (Jacob Moshenko)
 */
public class Method {

  /**
   * Class which describes a single named parameter to a {@link Method}.
   */
  public class Parameter {
    private final String name;
    private final DiscoveryType type;

    /**
     * Create an instance in the context of the calling method.
     *
     * @param name Name of this parameter.
     * @param type Type description of this parameter.
     */
    Parameter(String name, DiscoveryType type) {
      this.name = Preconditions.checkNotNull(name);
      this.type = Preconditions.checkNotNull(type);
    }

    /**
     * Returns the name of this parameter.
     */
    public String getName() {
      return name;
    }

    /**
     * Returns the type description of this parameter.
     */
    public DiscoveryType getType() {
      return type;
    }
  }

  private final Map<String, Schema> topLevelSchemas;
  private final DiscoveryDocument.Method methodNode;

  /**
   * Create an instance.
   *
   * @param topLevelSchemas Map of the named top level schemas.
   * @param methodNode {@link DiscoveryDocument.Method} node which this method
   *        wraps.
   */
  public Method(Map<String, Schema> topLevelSchemas, DiscoveryDocument.Method methodNode) {
    this.topLevelSchemas = Preconditions.checkNotNull(topLevelSchemas);
    this.methodNode = Preconditions.checkNotNull(methodNode);
  }

  /**
   * Returns the description string for this method.
   */
  public String getDescription() {
    return methodNode.getDescription();
  }

  /**
   * Returns the ID for this method.
   */
  public String getId() {
    return methodNode.getId();
  }

  /**
   * Returns type descriptions for each of the required parameters, in the
   * suggested order.
   */
  public List<Parameter> getRequiredParameters() {
    return createParameterList(methodNode.getParameterOrder());
  }

  /**
   * Returns type descriptions for each of the parameters that are named but not
   * listed as required.
   */
  public Collection<Parameter> getOptionalParameters() {
    Set<String> required = Sets.newHashSet(methodNode.getParameterOrder());
    Set<String> allParameters = methodNode.getParameters().keySet();
    Set<String> optional = Sets.difference(allParameters, required);

    return createParameterList(Lists.newArrayList(optional));
  }

  /**
   * Returns the description of what type this method returns.
   */
  public DiscoveryType getReturnType() {
    return DiscoveryType.createTypeFromSchemaNode(methodNode.getReturns(), topLevelSchemas);
  }

  /**
   * Returns a list of the OAuth 2.0 scope names applicable to this method.
   */
  public List<String> getScopes() {
    return methodNode.getScopes();
  }

  private List<Parameter> createParameterList(List<String> paramNamesInOrder) {
    return Lists.transform(paramNamesInOrder, new Function<String, Parameter>() {
      public Parameter apply(String name) {
        DiscoveryType type = DiscoveryType.createTypeFromSchemaNode(
            methodNode.getParameters().get(name), topLevelSchemas);
        return new Parameter(name, type);
      }
    });
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(methodNode, topLevelSchemas);
  }

  @Override
  public boolean equals(Object rhs) {
    if (rhs instanceof Method) {
      Method rhsTyped = (Method) rhs;
      return Objects.equal(methodNode, rhsTyped.methodNode)
          && Objects.equal(topLevelSchemas, rhsTyped.topLevelSchemas);
    } else {
      return false;
    }
  }
}
