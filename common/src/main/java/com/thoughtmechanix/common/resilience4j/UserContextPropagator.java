package com.thoughtmechanix.common.resilience4j;


import com.thoughtmechanix.common.utils.UserContext;
import com.thoughtmechanix.common.utils.UserContextHolder;
import io.github.resilience4j.core.ContextPropagator;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UserContextPropagator implements ContextPropagator<UserContext> {
    @Override
    public Supplier<Optional<UserContext>> retrieve() {
        return () -> Optional.ofNullable(UserContextHolder.getContext());
    }

    @Override
    public Consumer<Optional<UserContext>> copy() {
        return userContext -> {
            userContext.ifPresent(UserContextHolder::setContext);
        };
    }

    @Override
    public Consumer<Optional<UserContext>> clear() {
        return userContext -> UserContextHolder.clear();
    }
}
