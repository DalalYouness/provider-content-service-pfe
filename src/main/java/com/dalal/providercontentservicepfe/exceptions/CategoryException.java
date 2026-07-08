package com.dalal.providercontentservicepfe.exceptions;

public class CategoryException extends RuntimeException {
    public CategoryException(String categoryAlreadyExists) {
        super(categoryAlreadyExists);
    }
}
