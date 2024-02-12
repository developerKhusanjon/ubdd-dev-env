package uz.ciasev.ubdd_service.service.aop.single_thread_operation;

import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationTypeAlias;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 /* В качестве айдишки блокируемого ресурса принимается аргумент
 обернутого метода, который должен быть аннотирован через @SingleThreadOperationResource
 и быть лонгом */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleThreadOperation {
    SingleThreadOperationTypeAlias type();
}
