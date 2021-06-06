package com.google.inject.internal;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.InstanceBinding;

import java.util.Collections;
import java.util.Set;

final class ForestHackyInstanceBindingImpl<T> extends BindingImpl<T> implements InstanceBinding<T> {
    private final T instance;

    public ForestHackyInstanceBindingImpl(Key<T> key, T instance) {
        super(SourceProvider.UNKNOWN_SOURCE, key, Scoping.EAGER_SINGLETON);
        this.instance = instance;
    }

    @Override
    public T getInstance() {
        return instance;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return Collections.emptySet();
    }

    @Override
    public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void applyTo(Binder binder) {
        binder.withSource(getSource()).bind(getKey()).toInstance(instance);
    }

    @Override
    public Set<Dependency<?>> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public InternalFactory<T> getInternalFactory() {
        return new ConstantFactory<T>(Initializables.of(instance));
    }
}
