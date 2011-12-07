/*
 * Copyright 2011 JBoss, by Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.codegen.framework.meta.impl.gwt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.jboss.errai.codegen.framework.meta.*;

import com.google.gwt.core.ext.typeinfo.JConstructor;
import com.google.gwt.core.ext.typeinfo.JParameter;
import org.jboss.errai.codegen.framework.util.GenUtil;

/**
 * @author Mike Brock <cbrock@redhat.com>
 */
public class GWTConstructor extends MetaConstructor {
  private JConstructor constructor;
  private MetaClass declaringClass;
  private Annotation[] annotations;

  public GWTConstructor(JConstructor c) {
    this.constructor = c;
    this.declaringClass = GWTClass.newInstance(c.getEnclosingType());

    try {
      Class<?> cls = Class.forName(c.getEnclosingType().getQualifiedSourceName(), false,
              Thread.currentThread().getContextClassLoader());

      Constructor constr = cls.getConstructor(GWTClass.jParmToClass(c.getParameters()));

      annotations = constr.getAnnotations();

    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  @Override
  public MetaParameter[] getParameters() {
    List<MetaParameter> parameterList = new ArrayList<MetaParameter>();

    for (JParameter jParameter : constructor.getParameters()) {
      parameterList.add(new GWTParameter(jParameter, this));
    }

    return parameterList.toArray(new MetaParameter[parameterList.size()]);
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public MetaClass getReturnType() {
    return declaringClass;
  }

  @Override
  public MetaType getGenericReturnType() {
    return declaringClass;
  }

  @Override
  public MetaClass getDeclaringClass() {
    return declaringClass;
  }

  @Override
  public Annotation[] getAnnotations() {
    return annotations == null ? new Annotation[0] : annotations;
  }

  @Override
  public final <A extends Annotation> A getAnnotation(Class<A> annotation) {
    for (Annotation a : getAnnotations()) {
      if (a.annotationType().equals(annotation)) return (A) a;
    }
    return null;
  }

  @Override
  public final boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
    return getAnnotation(annotation) != null;
  }

  @Override
  public MetaType[] getGenericParameterTypes() {
    return null;
  }

  @Override
  public MetaClass[] getCheckedExceptions() {
    return new MetaClass[0];
  }

  @Override
  public boolean isVarArgs() {
    return constructor.isVarArgs();
  }

  @Override
  public boolean isAbstract() {
    return false;
  }

  @Override
  public boolean isPublic() {
    return constructor.isPublic();
  }

  @Override
  public boolean isPrivate() {
    return constructor.isPrivate();
  }

  @Override
  public boolean isProtected() {
    return constructor.isProtected();
  }

  @Override
  public boolean isFinal() {
    return false;
  }

  @Override
  public boolean isStatic() {
    return false;
  }

  @Override
  public boolean isTransient() {
    return false;
  }

  @Override
  public boolean isSynthetic() {
    return false;
  }

  @Override
  public boolean isVolatile() {
    return false;
  }

  @Override
  public boolean isSynchronized() {
    return false;
  }

  @Override
  public MetaTypeVariable[] getTypeParameters() {
    return GWTUtil.fromTypeVariable(constructor.getTypeParameters());
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof MetaConstructor && GenUtil.equals(this, (MetaConstructor) o);
  }
}
