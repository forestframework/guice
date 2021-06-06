package com.google.inject;

/**
 * Hacky way to intercept the provider lookup, so we can do
 * custom injection.
 */
public interface LookupInterceptor {
    /**
     * Intercept the provider lookup for specified key.
     *
     * @param key the key to intercept
     * @param <T> the result provider's type parameter
     * @return null if continue to Guice lookup, or the instance to provision if not null
     */
    <T> T intercept(Key<T> key);
}
