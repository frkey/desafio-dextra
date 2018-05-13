package br.com.dextra.desafio.exception;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.Lists;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @Autowired
  private MessageSource messageSource;

  public GlobalExceptionHandler(final MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  protected void notFoundAPIException(final Exception ex) {
    ex.printStackTrace();
    logE(ex);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  protected void processException(final Exception ex) {
    ex.printStackTrace();
    logE(ex);
  }

  @ExceptionHandler(SocketTimeoutException.class)
  @ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
  protected void processSocketTimeoutException(final SocketTimeoutException ex) {
    logE(ex);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
  protected void processHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
    logE(ex);
  }

  @ExceptionHandler(TypeMismatchException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected void processHttpRequestMethodNotSupportedException(final TypeMismatchException ex) {
    logE(ex);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected void processHttpMessageNotReadableException(final HttpMessageNotReadableException ex) throws IOException {
    logE(ex);
    // return badRequest(ex);
  }

  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<List<ErrorMessage>> invalidFormatException(final BindException ex) {
    logE(ex);
    return badRequest(ex.getAllErrors());
  }


  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
  protected void processHttpMediaTypeNotAcceptableException(final HttpMediaTypeNotAcceptableException ex) {
    logE(ex);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  protected void processHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException ex) {
    logE(ex);
  }

  @ExceptionHandler(APIException.class)
  @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  protected ResponseEntity<Void> processAPIExceotion(final APIException ex) {
    logE(ex);
    return new ResponseEntity<Void>(ex.getStatus());
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<List<ErrorMessage>> processBindException(final BindException ex) {
    logE(ex);
    return badRequest(ex.getAllErrors());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorMessage>> processMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex) {
    logE(ex);
    return badRequest(ex.getBindingResult().getFieldErrors());
  }

  @ExceptionHandler(UnprocessableEntityAPIException.class)
  public ResponseEntity<List<ErrorMessage>> processUnprocessableEntityAPIException(
      final UnprocessableEntityAPIException ex) {
    logE(ex);
    return errorInfoBody(ex.getErros(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public void handleDataIntegrityException() {}

  private String getMessage(final String code, final Object... args) {
    return messageSource.getMessage(code, args, Locale.getDefault());
  }

  private ResponseEntity<List<ErrorMessage>> badRequest(final List<? extends ObjectError> oErrors) {
    if (oErrors == null) {
      throw new IllegalArgumentException();
    }
    final List<ErrorMessage> errors = Lists.newArrayList();
    for (final ObjectError oError : oErrors) {
      final String code = oError.getDefaultMessage();
      final String message = getMessage(code, oError.getArguments());
      final ErrorMessage error = new ErrorMessage(code, message);
      errors.add(error);
    }
    return errorInfoBody(errors, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<List<ErrorMessage>> errorInfoBody(final List<ErrorMessage> errors,
      final HttpStatus httpStatus) {
    if (errors == null || httpStatus == null) {
      throw new IllegalArgumentException();
    }
    final ArrayList<ErrorMessage> listErrors = Lists.newArrayList(errors);
    return new ResponseEntity<List<ErrorMessage>>(listErrors, httpStatus);
  }

  private static void logE(final Exception e) {
    LOGGER.info("e={},m={}", e.getClass().getSimpleName(), e.getMessage());
  }

}
