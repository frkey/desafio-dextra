package br.com.dextra.desafio.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends APIException {

  private static final long serialVersionUID = 1L;

  public InternalServerErrorException(final Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, cause);
  }
}
