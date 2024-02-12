package uz.ciasev.ubdd_service.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReflectionUtils {

    public static <T> Map<String, Object> mapEntityToMapOfFields(T entity, ObjectMapper objectMapper) {

        Collection<String> fieldNames = getColumnFieldNames(entity.getClass());
        JsonNode jsonOfEntity = objectMapper.convertValue(entity, JsonNode.class);

        checkAllFieldsPresent(fieldNames, jsonOfEntity);

        return fieldNames.stream()
                .collect(Collectors.toMap(
                        name -> name,
//                        name -> jsonOfEntity.get(name).asText() не работает для вложенных обьектов
                        name -> jsonOfEntity.get(name)
                ));
    }

    private static void checkAllFieldsPresent(Collection<String> fieldNames, JsonNode jsonOfEntity) {
        List<String> missingFieldNames = fieldNames.stream()
                .filter(fieldName -> !jsonOfEntity.has(fieldName))
                .collect(Collectors.toList());

        if (missingFieldNames.isEmpty()) {
            return;
        }

        throw new ImplementationException("Json of entity does not contain all persistence fields %s. Add public getter for fix." + missingFieldNames);
    }


    private static Collection<String> getColumnFieldNames(Class<?> clazz) {
        Collection<String> collector = new ArrayList<>();
        buildColumnFieldNames(clazz, collector);
        return collector;
    }


    private static void buildColumnFieldNames(Class<?> clazz, Collection<String> collector) {
        if (clazz.equals(Object.class)) {
            return;
        }

        buildColumnFieldNames(clazz.getSuperclass(), collector);

        for (Field field : clazz.getDeclaredFields()) {
            if (isNotColumn(field)) continue;
            if (isReadOnlyColumn(field)) continue;

            // Поля обьектов всегда помечаны анатацие, потаум что надо указать название поля
            if (field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn annotation = field.getAnnotation(JoinColumn.class);
                collector.add(field.getName() + "Id");
                continue;
            }

            // Для примитивных булиан полей Lombok генерит гетор, ктаорый называется так же как поле (без префикса get),
            // и в итоге в json поле попадает без префикса is.
            if (isPrimitiveFlag(field)) {
                collector.add(StringUtils.removePrefixForCamelCase(field.getName(), 2));
                continue;
            }

            collector.add(field.getName());
        }
    }

    private static boolean isPrimitiveFlag(Field field) {
        return field.getType().equals(boolean.class) && field.getName().startsWith("is");
    }

    private static boolean isNotColumn(Field field) {
        if (Modifier.isStatic(field.getModifiers())) return true;
        if (field.isAnnotationPresent(Transient.class)) return true;
        if (field.isAnnotationPresent(OneToMany.class)) return true;
        if (field.isAnnotationPresent(OneToOne.class)) return true;
        return false;
    }


    private static boolean isReadOnlyColumn(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column annotation = field.getAnnotation(Column.class);
            return !(annotation.insertable() || annotation.updatable());
        }

        if (field.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn annotation = field.getAnnotation(JoinColumn.class);
            return !(annotation.insertable() || annotation.updatable());
        }

        return false;
    }
}
