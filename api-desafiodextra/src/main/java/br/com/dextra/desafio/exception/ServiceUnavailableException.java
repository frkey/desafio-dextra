package br.com.dextra.desafio.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends APIException {

  private static final long serialVersionUID = 1L;

  public ServiceUnavailableException(final Throwable cause) {
    super(HttpStatus.SERVICE_UNAVAILABLE, cause);
  }
}

