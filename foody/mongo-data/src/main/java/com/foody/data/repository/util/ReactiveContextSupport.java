package com.foody.data.repository.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import reactor.core.publisher.Signal;
import reactor.util.context.ContextView;

import java.util.Optional;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReactiveContextSupport {
    public static void onFinally(ContextView ctx, Runnable finalRunner) {
        Optional<String> toPutInMdc = ctx.getOrEmpty("CONTEXT_KEY");

        toPutInMdc.ifPresentOrElse(tpim -> {
                    try (MDC.MDCCloseable cMdc = MDC.putCloseable("REQUEST_ID", tpim)) {
                        finalRunner.run();
                    }
                },
                finalRunner::run);
    }


    public static <T> Consumer<Signal<T>> onEach(Consumer<T> logStatement) {
        return signal -> {
            if (!signal.isOnNext()) return;
            Optional<String> toPutInMdc = signal.getContextView().getOrEmpty("CONTEXT_KEY");

            toPutInMdc.ifPresentOrElse(tpim -> {
                        try (MDC.MDCCloseable cMdc = MDC.putCloseable("REQUEST_ID", tpim)) {
                            logStatement.accept(signal.get());
                        }
                    },
                    () -> logStatement.accept(signal.get()));
        };
    }

    public static <T> Consumer<Signal<T>> onEachWithErrorCheck(Consumer<Throwable> logStatement) {
        return signal -> {
            if (signal.isOnError()) {
                Optional<String> toPutInMdc = signal.getContextView().getOrEmpty("CONTEXT_KEY");

                toPutInMdc.ifPresentOrElse(tpim -> {
                            try (MDC.MDCCloseable cMdc = MDC.putCloseable("REQUEST_ID", tpim)) {
                                logStatement.accept(signal.getThrowable());
                            }
                        },
                        () -> logStatement.accept(signal.getThrowable()));
            }
        };
    }
}