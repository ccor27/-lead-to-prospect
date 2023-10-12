package com.example.leadtoprospect.model;

import com.example.leadtoprospect.exception.ExceptionSystem;

import java.util.Optional;

public class ResultOrException<T> {
    private final Optional<T> result;
    private final Optional<ExceptionSystem> exception;

    private ResultOrException(Optional<T> result, Optional<ExceptionSystem> exception) {
        this.result = result;
        this.exception = exception;
    }

    public static <T> ResultOrException<T> ofResult(Optional<T> result) {
        return new ResultOrException<>(result, Optional.empty());
    }

    public static <T> ResultOrException<T> ofException(Optional<ExceptionSystem> exception) {
        return new ResultOrException<>(Optional.empty(),exception);
    }

    public Optional<T> getResult() {
        return result;
    }

    public Optional<ExceptionSystem> getException() {
        return exception;
    }
}
