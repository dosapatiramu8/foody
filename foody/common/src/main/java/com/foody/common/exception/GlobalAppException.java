package com.foody.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class GlobalAppException extends RuntimeException{
    private final int httpStatus;
    private final String message;
}
