package com.foodtruck.pos.foodtruck_pos_v2.common;

public class ExitApplicationException extends RuntimeException {
  public ExitApplicationException() {
    super();
  }

  public ExitApplicationException(Throwable cause) {
    super(cause);
  }

  public ExitApplicationException(String message) {
    super(message);
  }

  public ExitApplicationException(String message, Throwable cause) {
    super(message, cause);
  }
}
