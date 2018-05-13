package br.com.dextra.desafio.exception;

import org.springframework.http.HttpStatus;

public abstract class APIException extends Exception {

  private static final long serialVersionUID = 1L;

  private final HttpStatus status;

  public APIException(HttpStatus status, Throwable cause) {
    super(cause);
    if (status == null) {
      throw new IllegalArgumentException();
    }
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

}
