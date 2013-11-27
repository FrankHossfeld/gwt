/*
 * Copyright 2013 Google Inc.
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
package com.google.gwt.core.ext.impl;

import com.google.gwt.thirdparty.guava.common.collect.Maps;

import java.io.File;
import java.util.Collections;
import java.util.Map;

/**
 * Provides private and temporary compiler support for user space generator
 * ResourceGeneratorUtil.addNamedFile().<br />
 *
 * Normally resources should be created with generatorContext.tryCreateResource() but it does not
 * well support the use case where the created resource should not be an output artifact and should
 * instead be made available as input to other generators. Once that use case is provided for
 * ResourceGeneratorUtil.addNamedFile() and this file should be deleted.
 */
// TODO(stalcup): delete the ResourceGeneratorUtil.addNamedFile() system.
public class ResourceGeneratorUtilImpl {

  /**
   * Records files and names of resources generated by Generators.
   */
  private static Map<String, File> generatedFilesByName = Maps.newHashMap();

  /**
   * Records generated resource files (such as css) which are not available on the classpath but
   * which need to be accessible by other generators.
   */
  public static void addGeneratedFile(String name, File file) {
    assert name != null : "name";
    assert file != null : "file";
    assert file.isFile() && file.canRead() : "file does not exist or cannot be read";

    generatedFilesByName.put(name, file);
  }

  /**
   * Returns the previously recorded generated file with the given name.
   */
  public static File getGeneratedFile(String name) {
    return generatedFilesByName.get(name);
  }

  /**
   * Returns the map that contains previously recorded generated files for given names.
   */
  public static Map<String, File> getGeneratedFilesByName() {
    return Collections.unmodifiableMap(generatedFilesByName);
  }
}