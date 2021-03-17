package org.shubham.archiver.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

import lombok.extern.slf4j.Slf4j;
import org.shubham.archiver.exception.ArchiverClientException;
import org.shubham.archiver.exception.ArchiverServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ArchiverExceptionHandler {

  @ExceptionHandler(ArchiverClientException.class)
  public ResponseEntity<String> handleArchiverClientException(ArchiverClientException exception) {
    log.info("ArchiverClientException Encountered.Caused by:: {}", exception.getMessage());
    return status(BAD_REQUEST)
        .body("Please provide valid input.");
  }

  @ExceptionHandler({ArchiverServerException.class})
  public ResponseEntity<String> handleArchiverServerException(ArchiverServerException exception) {
    log.info("ArchiverServerException Encountered.Caused by:: {}", exception.getMessage());
    return status(INTERNAL_SERVER_ERROR)
        .body("Failed to create zip file.");
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<String> handleGenericException(Exception exception) {
    log.error("Unknown exception occurred.", exception);
    return status(INTERNAL_SERVER_ERROR)
        .body("Something went wrong. Please contact the application support");
  }
}
