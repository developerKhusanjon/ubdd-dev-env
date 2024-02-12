package uz.ciasev.ubdd_service.service.search;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface ChunkedSearchService<T> {

    Stream<T> findAllExcelProjectionByIds(final List<Long> ids, long limit, Function<List<Long>, List<T>> repositorySupplier);
}
