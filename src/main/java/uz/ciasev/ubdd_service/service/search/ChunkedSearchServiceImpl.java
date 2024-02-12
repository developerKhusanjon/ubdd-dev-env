package uz.ciasev.ubdd_service.service.search;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChunkedSearchServiceImpl<T> implements ChunkedSearchService<T> {

    public Stream<T> findAllExcelProjectionByIds(final List<Long> ids, long limit, Function<List<Long>, List<T>> repositorySupplier) {

//        List<T> rsl = new LinkedList<>();
//
//        long start = 0;
//
//        while (true) {
//
//            List<Long> chunk = getListChunk(start, limit, ids);
//
//            if (chunk.size() == 0) {
//                break;
//            }
//            start += chunk.size();
//
//            List<T> projectionChunk = repositorySupplier.apply(chunk);
//
//            rsl.addAll(projectionChunk);
//        }
//
//        return rsl;


        List<List<Long>> rsl = new LinkedList<>();

        long start = 0;

        while (true) {

            List<Long> chunk = getListChunk(start, limit, ids);

            if (chunk.size() == 0) {
                break;
            }
            start += chunk.size();

            rsl.add(chunk);
        }

        return rsl
                .stream()
                .map(repositorySupplier::apply)
                .flatMap(Collection::stream);
    }

    private List<Long> getListChunk(long start, long size, List<Long> list) {

        return list.stream().skip(start).limit(size).collect(Collectors.toList());
    }
}
