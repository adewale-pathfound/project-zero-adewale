package io.pathfound.projectzero.pagination;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

public class Paginator<T> {

  private final int limit;
  @Getter private final List<T> pages;
  private final int totalItemsCount;
  private final T defaultEmptyItems;

  @Valid
  private Paginator(
      int limit, @Nonnull List<T> pages, int totalItemsCount, @Nonnull T defaultEmptyItems) {
    this.limit = limit;
    this.pages = pages;
    this.totalItemsCount = totalItemsCount;
    this.defaultEmptyItems = defaultEmptyItems;
  }

  /**
   * Paginates all objects in a List
   *
   * @param items List of Objects to be paginated
   * @param size Size of each page
   * @param <V> The Type of Objects to be paginated
   * @return Paginator for elements
   */
  public static <V> Paginator<List<V>> paginate(List<V> items, int size) {
    List<List<V>> pages =
        ListUtils.partition(items, size).stream().map(Collections::unmodifiableList).toList();
    return new Paginator<>(size, pages, items.size(), List.of());
  }

  /**
   * Paginates all objects in a map
   *
   * @param items A Map of Objects to be paginated (preferably an ordered Map e.g LinkedHashMap)
   * @param <K> Type of the map Key
   * @param <V> Type of the map value
   * @param size Size of each page
   * @return Paginator for elements
   */
  public static <K, V> Paginator<Map<K, V>> paginate(Map<K, V> items, int size) {
    LinkedHashMap<K, V> itemsMap =
        (items instanceof LinkedHashMap<K, V> linkedHashMap)
            ? linkedHashMap
            : new LinkedHashMap<>(items);
    List<List<Map.Entry<K, V>>> partitions =
        ListUtils.partition(new ArrayList<>(itemsMap.entrySet()), size);
    List<Map<K, V>> pages =
        partitions.stream()
            .map(
                partition -> {
                  Map<K, V> pageMap = new LinkedHashMap<>();
                  partition.forEach(entry -> pageMap.put(entry.getKey(), entry.getValue()));
                  return Collections.unmodifiableMap(pageMap);
                })
            .toList();
    return new Paginator<>(size, pages, itemsMap.size(), Map.of());
  }

  /**
   * gets the items in the specified page
   *
   * @param pagingStrategy determines how the page start counter is evaluated
   * @param pageNumber Page number of elements to be fetched.
   * @return {@link PagedResult}
   * @throws PageDoesNotExistException If page is out of the available range
   */
  public PagedResult<T> getPage(PagingStrategy pagingStrategy, int pageNumber) {

    if (pages.isEmpty()) {
      return new PagedResult<>(defaultEmptyItems, PagingMetadata.DEFAULT_INSTANCE);
    }

    int maxPageNumber;
    int pageIndex;
    switch (pagingStrategy) {
      case DEFAULT -> {
        maxPageNumber = pages.size();
        pageIndex = pageNumber - 1;
      }
      case CURSOR -> {
        maxPageNumber = pages.size() - 1;
        pageIndex = pageNumber;
      }
      default -> throw new IllegalStateException(
          "Unexpected pagingStrategy value: " + pagingStrategy);
    }

    if (pageNumber < pagingStrategy.getStartCounter()) {
      throw new PageDoesNotExistException(
          String.format(
              "[pagingStrategy = %s] - Requested %s value: %s is out of range!... (When totalItemsCount = %s and requested limit = %s, then min allowable %s value = %s)",
              pagingStrategy.name(),
              pagingStrategy.getAlias(),
              pageNumber,
              totalItemsCount,
              limit,
              pagingStrategy.getAlias(),
              pagingStrategy.getStartCounter()));
    }

    if (pageNumber > maxPageNumber) {
      throw new PageDoesNotExistException(
          String.format(
              "[pagingStrategy = %s] - Requested %s value: %s is out of range!... (When totalItemsCount = %s and requested limit = %s, then max allowable %s value = %s)",
              pagingStrategy.name(),
              pagingStrategy.getAlias(),
              pageNumber,
              totalItemsCount,
              limit,
              pagingStrategy.getAlias(),
              maxPageNumber));
    }

    int nextPageNumber = pageNumber + 1;
    Integer nextPage = (nextPageNumber <= maxPageNumber ? nextPageNumber : null);

    return new PagedResult<>(
        pages.get(pageIndex),
        PagingMetadata.builder()
            .currentPage(pageNumber)
            .nextPage(nextPage)
            .totalItemsCount(totalItemsCount)
            .build());
  }

  /**
   * gets the items in the specified page
   *
   * @param pagingStrategy determines how the page start counter is evaluated
   * @param pageNumber Page number of elements to be fetched.
   * @return {@link PagedResult}
   * @throws PageDoesNotExistException If page is out of the available range
   */
  public PagedResult<T> getPage(PagingStrategy pagingStrategy, String pageNumber) {
    return getPage(pagingStrategy, resolvePageNumber(pageNumber, pagingStrategy));
  }

  private static int resolvePageNumber(String pageNumber, PagingStrategy pagingStrategy) {
    if (StringUtils.isNumeric(pageNumber)) {
      return Integer.parseInt(pageNumber);
    } else {
      return pagingStrategy.getStartCounter();
    }
  }
}
