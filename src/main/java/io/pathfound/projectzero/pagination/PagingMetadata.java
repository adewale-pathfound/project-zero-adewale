package io.pathfound.projectzero.pagination;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class PagingMetadata {
  private final int size;
  private final Integer currentPage;
  private final Integer nextPage;
  private final Integer totalItemsCount;

  public static PagingMetadata DEFAULT_INSTANCE =
      PagingMetadata.builder().size(0).currentPage(0).nextPage(0).totalItemsCount(0).build();
}
