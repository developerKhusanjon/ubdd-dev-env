package uz.ciasev.ubdd_service.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.metamodel.SingularAttribute;

public class PageUtils {

    private static Pageable topOrderBy(int n, String sortAttribute) {
        return top(n, Sort.by(sortAttribute));
    }

    public static Pageable top(int n, Sort sort) {
        return PageRequest.of(0, n, sort);
    }

    public static Pageable top(int n, SingularAttribute<?,?> sortAttribute) {
        return PageRequest.of(0, n, Sort.by(sortAttribute.getName()));
    }

    public static Pageable last(int n, SingularAttribute<?,?> sortAttribute) {
        return PageRequest.of(0, n, Sort.by(sortAttribute.getName()).descending());
    }

    public static Pageable limit(int n) {
        return top(n, Sort.unsorted());
    }

    public static Pageable one() {
        return top(1, Sort.unsorted());
    }

    public static Pageable topWithMaxId(int n) {
        return top(n, Sort.by("id").descending());
    }

    public static Pageable topWithMinId(int n) {
        return topOrderBy(n, "id");
    }

    public static Pageable oneWithMaxId() {
        return topWithMaxId(1);
    }

    public static Pageable oneWithMinId() {
        return topWithMinId(1);
    }

    public static Pageable unpaged() {
        return Pageable.unpaged();
    }
}
