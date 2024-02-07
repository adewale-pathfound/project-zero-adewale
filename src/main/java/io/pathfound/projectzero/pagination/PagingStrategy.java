package io.pathfound.projectzero.pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PagingStrategy {
  DEFAULT("one-based start counter", 1, "page"),
  CURSOR("zero-based start counter", 0, "cursor");

  private final String description;
  private final int startCounter;
  private final String alias;

  public boolean isPageFloorValid(int pageNumber) {
    return this.equals(PagingStrategy.DEFAULT) && pageNumber >= 1;
  }

  public boolean isPageCeilingValid(int pageNumber, int pageSize) {
    return this.equals(PagingStrategy.DEFAULT) && pageNumber > pageSize;
  }
}
