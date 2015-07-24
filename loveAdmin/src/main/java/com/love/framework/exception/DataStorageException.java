package com.love.framework.exception;

public class DataStorageException extends RuntimeException
{
  private static final long serialVersionUID = -6282755065110379084L;
  public static final int INSERT_ERROR = 0;
  public static final int UPDATE_ERROR = 1;
  public static final int DELETE_ERROR = 2;

  public DataStorageException(int errorModel, String message)
  {
    super(model(errorModel) + message);
  }

  protected static String model(int errorCode) {
    String string = "";
    switch (errorCode) {
    case 0:
      string = "保存数据失败";
      break;
    case 1:
      string = "更新数据失败";
      break;
    case 2:
      string = "删除数据失败";
      break;
    }

    return string;
  }

  public DataStorageException(int errorCode, Throwable cause) {
    super(model(errorCode), cause);
  }

  public DataStorageException(int errorCode, String message, Throwable cause) {
    super(model(errorCode) + message, cause);
  }
}
