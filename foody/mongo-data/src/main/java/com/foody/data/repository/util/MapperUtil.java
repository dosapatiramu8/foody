package com.foody.data.repository.util;

import com.foody.common.exception.AppDataBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

import java.util.function.Supplier;

import static com.foody.common.constant.DatabaseConstants.DATA_BASE_CONNECTION_ERROR;
import static com.foody.common.constant.DatabaseConstants.DOCUMENT;
import static com.foody.common.constant.DatabaseConstants.DOLLAR_ROOT;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapperUtil {

  private final ReactiveMongoTemplate reactiveMongoTemplate;


  public <T,E> Supplier<Flux<T>> aggregation(TypedAggregation<T> aggregation,
      Class<T> type, Class<E> entityType) {

    String collectionName = determineCollectionName(entityType);
     Aggregation newAggregation = allowDiskUse(aggregation);
    //log.info("DB_QUERY ");
    return () -> reactiveMongoTemplate.aggregate(newAggregation, collectionName,
        type).onErrorResume(throwable -> Flux.error(
        new AppDataBaseException(HttpStatus.BAD_GATEWAY,
            DATA_BASE_CONNECTION_ERROR + throwable.getMessage()))).contextWrite(context -> Context.of(
        MDC.getCopyOfContextMap()));
  }

  public <T> Supplier<Flux<T>> aggregation(TypedAggregation<T> aggregation,
      String collectionName, Class<T> entityType) {

    Aggregation newAggregation = allowDiskUse(aggregation);
    return () -> reactiveMongoTemplate.aggregate(newAggregation, collectionName,
        entityType).onErrorResume(throwable -> Flux.error(
        new AppDataBaseException(HttpStatus.BAD_GATEWAY,
            DATA_BASE_CONNECTION_ERROR + throwable.getMessage())));
  }

  public <T> Supplier<Flux<T>> aggregation(TypedAggregation<T> aggregation,
      Class<T> type) {

    String collectionName = determineCollectionName(type);
    Aggregation newAggregation = allowDiskUse(aggregation);
    return () -> reactiveMongoTemplate.aggregate(newAggregation, collectionName,
        type).onErrorResume(throwable -> Flux.error(
        new AppDataBaseException(HttpStatus.BAD_GATEWAY,
            DATA_BASE_CONNECTION_ERROR + throwable.getMessage())));
  }
  public <T> Supplier<Flux<T>> aggregation(Aggregation aggregation,
      Class<T> type) {

    String collectionName = determineCollectionName(type);
    Aggregation newAggregation = allowDiskUse(aggregation);
    return () -> reactiveMongoTemplate.aggregate(newAggregation, collectionName,
        type).onErrorResume(throwable -> Flux.error(
        new AppDataBaseException(HttpStatus.BAD_GATEWAY,
            DATA_BASE_CONNECTION_ERROR + throwable.getMessage())));
  }

  public <T,E> Supplier<Flux<T>> aggregation(Aggregation aggregation,
      Class<T> type, Class<E> entityType) {

    String collectionName = determineCollectionName(entityType);
    Aggregation newAggregation = allowDiskUse(aggregation);
    return () -> reactiveMongoTemplate.aggregate(newAggregation, collectionName,
        type).onErrorResume(throwable -> Flux.error(
        new AppDataBaseException(HttpStatus.BAD_GATEWAY,
            DATA_BASE_CONNECTION_ERROR + throwable.getMessage())));
  }

  public <T> Supplier<Flux<T>> aggregation(TypedAggregation<T> aggregation,
      Class<T> type, String collectionName) {

    Aggregation newAggregation = allowDiskUse(aggregation);
    return () -> reactiveMongoTemplate.aggregate(newAggregation, collectionName,
        type).onErrorResume(throwable -> Flux.error(
        new AppDataBaseException(HttpStatus.BAD_GATEWAY,
            DATA_BASE_CONNECTION_ERROR + throwable.getMessage())));
  }

  public GroupOperation distinctGroupOperation(String distinctField,
      String aliasName, String... fields){

    return Aggregation.group(fields)
        .first(DOLLAR_ROOT).as(DOCUMENT)
        .addToSet(distinctField).as(aliasName);
  }

  private String determineCollectionName(Class<?> targetType) {
    Document classAnnotation = AnnotationUtils.findAnnotation(targetType, Document.class);
    if (classAnnotation != null) {
      return classAnnotation.value();
    }
    return null;
  }

  private Aggregation allowDiskUse(Aggregation aggregation){
   return aggregation.withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build());
  }




}
