package com.acl.municipalidad.images.domain.exceptions;

public class ImageLimitExceededException extends RuntimeException {
    public ImageLimitExceededException(String message) {
        super(message);
    }
}