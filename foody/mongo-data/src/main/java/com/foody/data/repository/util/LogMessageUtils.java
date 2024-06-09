package com.foody.data.repository.util;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Supplier;


@Slf4j
public class LogMessageUtils {
    
  /**
   * Executes a process with the given name and request by invoking the provided supplier. The
   * process name and request are logged before executing the supplier.
   *
   * @param processName the name of the process being executed
   * @param request     the request object for the process
   * @param supplier    the supplier providing the Mono to execute
   * @param <T>         the type of the result returned by the supplier
   * @return a Mono representing the result of executing the supplier
   */
  public static <T> Mono<T> exec(String processName, Object request, Supplier<Mono<T>> supplier) {
    long startTime = System.currentTimeMillis();
    return Mono.just(Optional.of(request).orElse(""))
        /*.doOnEach(ReactiveContextSupport.onEach(req ->
            log.info("Executing Process: '{}' ", processName)))*/
        .flatMap(qry -> executed(processName, request, supplier)).switchIfEmpty(Mono.defer(() -> {
          loggingAttribute(processName,request,null);
          log.info("Process Executed with no results: '{}', timeTaken: {} ms", processName,
              (System.currentTimeMillis() - startTime));
          return Mono.empty();
        }));
  }
/*    public static <T> Mono<T> exec(String processName, Object request, Supplier<Mono<T>> supplier) {
        long startTime = System.currentTimeMillis();
        return Mono.just(Optional.of(request).orElse(""))
                .flatMap(qry -> executed(processName, request, supplier)).switchIfEmpty(Mono.defer(() -> {
                    loggingAttribute(processName,request,startTime,null);
                    return Mono.empty();
                }));
    }*/


    /**
   * Executes a process with the given name and request by invoking the provided supplier. The
   * process name and request are logged after executing the supplier or if there is any exception
   * thrown.
   *
   * @param processName the name of the process being executed
   * @param request     the request object for the process
   * @param supplier    the supplier providing the Mono to execute
   * @param <T>         the type of the result returned by the supplier
   * @return a Mono representing the result of executing the supplier
   */
  public static <T> Mono<T> executed(String processName, Object request,
      Supplier<Mono<T>> supplier) {
    long startTime = System.currentTimeMillis();
    return Mono.deferContextual(ctx -> supplier.get()
            .doFinally(value -> ReactiveContextSupport.onFinally(ctx, () ->
                loggingAttribute(processName, request,null))))
        .doOnEach(ReactiveContextSupport.onEachWithErrorCheck(e -> {
          loggingAttribute(processName, request, e);
          log.error(
              "Error occurred during process: '{}', error: '{}', object: '{}' timeTaken: {} ms",
              processName, e, request, System.currentTimeMillis() - startTime);
        }));

  }

  /**
   * Executes a process with the given name and request by invoking the provided supplier. The
   * process name and request are logged before executing the supplier.
   *
   * @param processName the name of the process being executed
   * @param request     the request object for the process
   * @param supplier    the supplier providing the Mono to execute
   * @param <T>         the type of the result returned by the supplier
   * @return a Flux representing the result of executing the supplier
   */
  public static <T> Flux<T> execFlux(String processName, Object request,
                                     Supplier<Flux<T>> supplier) {
      return exececutedFlux(processName, request, supplier).switchIfEmpty(Flux.defer(() -> {
        log.info("Process Executed with no results: '{}', request: {} ms", processName, request);
          return Flux.empty();
      }));
  }

  /**
   * Executes a process with the given name and request by invoking the provided supplier. The
   * process name and request are logged after executing the supplier or if there is any exception
   * thrown.
   *
   * @param processName the name of the process being executed
   * @param supplier    the supplier providing the Mono to execute
   * @param <T>         the type of the result returned by the supplier
   * @return a Flux representing the result of executing the supplier
   */
  public static <T> Flux<T> exececutedFlux(String processName,
      Object request, Supplier<Flux<T>> supplier) {
    long startTime = System.currentTimeMillis();
    return Flux.deferContextual(ctx -> supplier.get()
        .doFinally(value -> ReactiveContextSupport.onFinally(ctx, () ->
            loggingAttribute(processName, request, null)))
            .doOnEach(ReactiveContextSupport.onEachWithErrorCheck(e -> {
          loggingAttribute(processName,request,e);
          log.error("Error occurred during process: '{}', error: '{}', timeTaken: {} ms",
              processName, e, System.currentTimeMillis() - startTime);
        })));
  }


    private static void loggingAttribute(String processName, Object request,
                                          Throwable throwable) {
      log.info("Process Executed: {}  Request Payload: {}  ", processName, request);
    }

}