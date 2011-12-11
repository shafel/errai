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

package org.jboss.errai.marshalling.server.util;

import org.jboss.errai.marshalling.client.util.MarshallUtil;

import java.util.Map;

/**
 * @author Mike Brock
 */
public class ServerMarshallUtil {
  public static boolean handles(Object object, Class<?> cls) {
    if (object instanceof Map) {
      return MarshallUtil.handles((Map) object, cls);
    }
    else {
      return cls.isAssignableFrom(object.getClass());
    }
  }
}