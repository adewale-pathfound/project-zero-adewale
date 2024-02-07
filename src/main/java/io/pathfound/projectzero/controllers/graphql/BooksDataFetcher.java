package io.pathfound.projectzero.controllers.graphql;

import com.netflix.graphql.dgs.DgsComponent;

@DgsComponent
public class BooksDataFetcher {
  //  @DgsData(
  //      parentType = DgsConstants.BOOKQUERIES.TYPE_NAME,
  //      field = DgsConstants.BOOKQUERIES.BookById
  //  )
  //  public DataFetcherResult<Book> getBookById(
  //          @InputArgument final String id,
  //          DataFetchingEnvironment dataFetchingEnvironment) {
  //    return DataFetcherResult.<Book>newResult()
  //            .data(Book.newBuilder().id("1").build())
  //            .build();
  //  }

  //  @DgsData(
  //          parentType = DgsConstants.BOOKQUERIES.TYPE_NAME,
  //          field = DgsConstants.BOOKQUERIES.BooksBySearch
  //  )
  //  public DataFetcherResult<Books_Paginated> getBooksBySearch(
  //          @InputArgument(name="searchParams") final Books_SearchParams_Input searchParams,
  //          @InputArgument final Integer size,
  //          @InputArgument final Integer page,
  //          DataFetchingEnvironment dataFetchingEnvironment) {
  //
  //    System.out.printf("===> searchParams: %s\n", searchParams);
  //
  //    return DataFetcherResult.<Books_Paginated>newResult()
  //            .data(Books_Paginated.newBuilder()
  //                    .items(List.of())
  //                    .pagination(
  //                            Pagination.newBuilder()
  //                                    .pageIndex(0)
  //                                    .pageSize(10)
  //                                    .totalItems(50)
  //                                    .nextPageIndex(1)
  //                                    .build()
  //                    )
  //                    .build())
  //            .build();
  //  }
}
