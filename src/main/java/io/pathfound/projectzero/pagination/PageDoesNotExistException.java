package io.pathfound.projectzero.pagination;

public class PageDoesNotExistException extends RuntimeException {
  public PageDoesNotExistException(String message) {
    super(message);
  }
}
