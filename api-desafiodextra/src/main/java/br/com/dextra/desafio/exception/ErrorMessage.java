package br.com.dextra.desafio.exception;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class ErrorMessage implements Serializable {

  private static final long serialVersionUID = -40089374255050241L;

  private String code;

  private String message;

  private String link;

  public ErrorMessage(final String code, final String msg, final String identification) {
    this.code = code;
    message = msg;
    link = identification;
  }

  public ErrorMessage(final String code, final String msg) {
    this.code = code;
    message = msg;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = StringUtils.trimAllWhitespace(code);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = StringUtils.trimAllWhitespace(message);
  }

  public String getLink() {
    return link;
  }

  public void setLink(final String link) {
    this.link = StringUtils.trimAllWhitespace(link);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (code == null ? 0 : code.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ErrorMessage other = (ErrorMessage) obj;
    if (code == null) {
      if (other.code != null) {
        return false;
      }
    } else if (!code.equals(other.code)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ErrorMessage [code=" + code + ", message=" + message + ", link=" + link + "]";
  }

}
