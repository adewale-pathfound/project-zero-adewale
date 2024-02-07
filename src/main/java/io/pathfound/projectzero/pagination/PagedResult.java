package io.pathfound.projectzero.pagination;

public record PagedResult<T>(T items, PagingMetadata pagingMetadata) {}
