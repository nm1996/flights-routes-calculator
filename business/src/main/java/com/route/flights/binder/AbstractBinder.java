package com.route.flights.binder;

import com.route.flights.binder.exception.TextBinderMismatchArgs;

public abstract class AbstractBinder<T> {

    public abstract T bindFromTextualSourceFile(String... rowStrings) throws TextBinderMismatchArgs;
}
