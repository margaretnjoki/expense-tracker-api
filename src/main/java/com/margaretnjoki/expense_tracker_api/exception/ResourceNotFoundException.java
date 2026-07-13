package com.margaretnjoki.expense_tracker_api.exception;


import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, UUID id) {
        super(resource + " not found: " + id);
    }
}