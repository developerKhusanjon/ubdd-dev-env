package uz.ciasev.ubdd_service.service.aop.signature;

import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DigitalSignatureCheck {

    public SignatureEvent event();
}
