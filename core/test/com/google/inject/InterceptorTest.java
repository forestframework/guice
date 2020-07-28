package com.google.inject;

import junit.framework.TestCase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.google.inject.Asserts.assertContains;

public class InterceptorTest extends TestCase {
    @Retention(RetentionPolicy.RUNTIME)
    @BindingAnnotation
    @interface Config {
        String value();
    }

    public static class A {
        String config;

        @Inject
        public A(@Config("a") String config) {
            this.config = config;
        }
    }

    public static class B {
        int config;

        @Inject
        public B(@Config("42") int config) {
            this.config = config;
        }
    }

    public static class ConfigProvider implements LookupInterceptor {
        public <T> T getInstance(String configValue, Class<T> klass) {
            if (klass == String.class) {
                return (T) configValue;
            } else if (klass == Integer.class) {
                return (T) Integer.valueOf(configValue);
            } else {
                throw new RuntimeException();
            }
        }

        @Override
        public <T> T intercept(Key<T> key) {
            if (key.getAnnotation() != null && key.getAnnotation().annotationType() == Config.class) {
                return (T) getInstance(((Config) key.getAnnotation()).value(), key.getTypeLiteral().getRawType());
            }
            return null;
        }
    }

    public void testInterceptor() {
        Injector injector = GuiceExt.createInjector(new ConfigProvider());
        assertEquals("a", injector.getInstance(A.class).config);
        assertEquals(42, injector.getInstance(B.class).config);
    }

    public void testInterceptorFail() {
        try {
            Injector injector = GuiceExt.createInjector(new LookupInterceptor() {
                @Override
                public <T> T intercept(Key<T> key) {
                    return null;
                }
            });
            injector.getInstance(A.class);
            fail();
        } catch (ConfigurationException expected) {
            assertContains(expected.getMessage(), "No implementation for java.lang.String annotated");
        }
    }
}
