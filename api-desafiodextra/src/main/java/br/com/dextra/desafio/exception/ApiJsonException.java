package br.com.dextra.desafio.exception;

public class ApiJsonException extends Exception {

  private static final long serialVersionUID = 5456484840178149688L;

  public ApiJsonException() {
    super();
  }

  public ApiJsonException(final String message, final Throwable cause, final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ApiJsonException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ApiJsonException(final String message) {
    super(message);
  }

  public ApiJsonException(final Throwable cause) {
    super(cause);
  }

}

