package br.com.dextra.desafio.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends APIException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(final Throwable cause) {
		super(HttpStatus.NOT_FOUND, cause);
	}

	public NotFoundException(final String msg) {
		super(HttpStatus.NOT_FOUND, new Throwable(msg));
	}
}

