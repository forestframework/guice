/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.inject;

import com.google.inject.internal.InternalInjectorCreator;

import java.util.Arrays;

/**
 * For internal use in Forest framework only.
 */
public final class GuiceExt {

  private GuiceExt() {}

  public static Injector createInjector(LookupInterceptor interceptor, Module... modules) {
    return createInjector(interceptor, Arrays.asList(modules));
  }

  public static Injector createInjector(LookupInterceptor interceptor, Iterable<? extends Module> modules) {
    return createInjector(interceptor, Stage.DEVELOPMENT, modules);
  }

  public static Injector createInjector(LookupInterceptor interceptor, Stage stage, Module... modules) {
    return createInjector(interceptor, stage, Arrays.asList(modules));
  }

  public static Injector createInjector(LookupInterceptor interceptor, Stage stage, Iterable<? extends Module> modules) {
    return new InternalInjectorCreator().lookupInterceptor(interceptor).stage(stage).addModules(modules).build();
  }
}
