package org.shubham.archiver.exception;

public class ArchiverServerException extends RuntimeException {

  public ArchiverServerException(String message, Throwable ex) {
    super(message, ex);
  }

}
