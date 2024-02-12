package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;

public class DuplicatePathInDictServicesException extends ImplementationException {
    public DuplicatePathInDictServicesException(String oldClassName, String newClassName, String parentServiceName) {
        super(
                String.format(
                        "%s and %s return the same path. Each %s inheritor must return unique path from getSubPath() method.",
                        oldClassName, newClassName, parentServiceName)
        );
    }
}
