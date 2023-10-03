package com.app.task.enums;

public enum ResponseCode {

    FAILED(1, "Failed request"),

    AUTH_ERROR_INVALID_USERNAME_OR_PASSWORD(102, "Invalid username or password"),

    AUTH_ERROR_NOT_PERMITTED(103, "Account is not allowed to access this system"),

    UNAUTHORIZED(401, "Unauthorized"),

    INTERNAL_SERVER_ERROR(500, "Internal server error"),


    // File
    FILE_ERROR_UPLOAD_FAILED (103, "Upload file failed"),


    // Account
    ACCOUNT_ERROR_EXIST_USER_NAME(201, "Existed username"),

    ACCOUNT_ERROR_EXIST_EMAIL(202, "Existed email"),

    ACCOUNT_ERROR_EXIST_CODE(203, "Existed code"),

    ACCOUNT_ERROR_NOT_FOUND(204, "Account not found"),


;

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
