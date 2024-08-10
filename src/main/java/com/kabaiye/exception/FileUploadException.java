package com.kabaiye.exception;

import java.io.IOException;

public class FileUploadException extends IOException {
    public FileUploadException(String message) {
        super(message);
    }
}
