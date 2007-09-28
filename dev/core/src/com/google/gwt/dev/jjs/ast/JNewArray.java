/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.dev.jjs.ast;

import com.google.gwt.dev.jjs.SourceInfo;

import java.util.ArrayList;

/**
 * New array expression.
 */
public class JNewArray extends JExpression implements HasSettableType {

  public ArrayList<JExpression> dims = null;
  public ArrayList<JExpression> initializers = null;
  private JArrayType arrayType;

  public JNewArray(JProgram program, SourceInfo info, JArrayType arrayType) {
    super(program, info);
    this.arrayType = arrayType;
  }

  public JArrayType getArrayType() {
    return arrayType;
  }

  public JType getType() {
    return arrayType;
  }

  public boolean hasSideEffects() {
    if (initializers != null) {
      for (int i = 0, c = initializers.size(); i < c; ++i) {
        if (initializers.get(i).hasSideEffects()) {
          return true;
        }
      }
    }
    if (dims != null) {
      for (int i = 0, c = dims.size(); i < c; ++i) {
        if (dims.get(i).hasSideEffects()) {
          return true;
        }
      }
    }
    // The new operation on an array does not actually cause side effects.
    return false;
  }

  public void setType(JType arrayType) {
    this.arrayType = (JArrayType) arrayType;
  }

  public void traverse(JVisitor visitor, Context ctx) {
    if (visitor.visit(this, ctx)) {
      assert ((dims != null) ^ (initializers != null));

      if (dims != null) {
        visitor.accept(dims);
      }

      if (initializers != null) {
        visitor.accept(initializers);
      }
    }
    visitor.endVisit(this, ctx);
  }
}
