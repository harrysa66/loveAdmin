package com.love.framework.exception;

public class ApplicationRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = 1L;

  public ApplicationRuntimeException()
  {
  }

  public ApplicationRuntimeException(String message)
  {
    super(message);
  }

  public ApplicationRuntimeException(Throwable cause) {
    super(cause);
  }

  public ApplicationRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
}
