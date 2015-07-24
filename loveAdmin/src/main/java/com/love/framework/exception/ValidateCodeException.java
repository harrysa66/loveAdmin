package com.love.framework.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException
{
  public ValidateCodeException(String msg)
  {
    super(msg);
  }
}
