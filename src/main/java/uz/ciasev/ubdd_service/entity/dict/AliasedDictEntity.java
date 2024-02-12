package uz.ciasev.ubdd_service.entity.dict;

public interface AliasedDictEntity<T extends Enum<T>> extends DictEntity {

    T getAlias();

    default boolean is(T o) {
        return o.equals(getAlias());
    }

    default boolean not(T o) {
        return !is(o);
    }

//    default boolean oneOf(T... os) {
//        for (T o : os) {
//            if (o.equals(getAlias())) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    default boolean oneOf(T o1, T o2) {
        return is(o1) || is(o2);
    }

    default boolean notOneOf(T o1, T o2) {
        return not(o1) && not(o2);
    }

    default boolean notOneOf(T o1, T o2, T o3) {
        return notOneOf(o1, o2) && not(o3);
    }

    default boolean notOneOf(T o1, T o2, T o3, T o4) {
        return notOneOf(o1, o2, o3) && not(o4);
    }
}
