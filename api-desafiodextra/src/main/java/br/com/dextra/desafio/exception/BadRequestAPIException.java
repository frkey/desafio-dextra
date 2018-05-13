package br.com.dextra.desafio.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class BadRequestAPIException extends ErrorMessageAPIException {

  private static final long serialVersionUID = 1L;

  public BadRequestAPIException(final String errorCode) {
    super(HttpStatus.BAD_REQUEST, errorCode);
  }

  public BadRequestAPIException(final ErrorMessage erros) {
    super(HttpStatus.BAD_REQUEST, erros);
  }

  public BadRequestAPIException(final List<ErrorMessage> erros) {
    super(HttpStatus.BAD_REQUEST, erros);
  }

}
