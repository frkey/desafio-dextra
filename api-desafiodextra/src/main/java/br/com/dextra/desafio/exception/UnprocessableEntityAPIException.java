package br.com.dextra.desafio.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityAPIException extends ErrorMessageAPIException {

  private static final long serialVersionUID = 1L;

  public UnprocessableEntityAPIException(String errorCode) {
    super(HttpStatus.UNPROCESSABLE_ENTITY, errorCode);
  }

  public UnprocessableEntityAPIException(List<ErrorMessage> errorCodes) {
    super(HttpStatus.UNPROCESSABLE_ENTITY, errorCodes);
  }

}
