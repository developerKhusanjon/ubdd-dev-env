package uz.ciasev.ubdd_service.utils;

import uz.ciasev.ubdd_service.entity.permission.PermissionAlias;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@Retention(RUNTIME)
public @interface AllowedPermission {

    PermissionAlias value();
}
