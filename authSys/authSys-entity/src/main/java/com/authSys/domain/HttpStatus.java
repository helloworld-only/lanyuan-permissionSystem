package com.authSys.domain;

public class HttpStatus {

    // 操作成功
    public static final int SUCCESS = 200;

    public static final int CREATED = 201;

    public static final int ACCEPTED = 202;

    public static final int NO_CONTENT = 204;

    public static final int MOVED_PERM = 301;

    public static final int SEE_OTHER = 303;

    public static final int NOT_MODIFIED = 304;

    public static final int BAD_REQUEST = 400;

    public static final int UNAUTHORIZED = 401;

    // 无权访问
    public static final int FORBIDDEN = 403;

    // 资源未找到
    public static final int NOT_FOUND = 404;

    public static final int BAD_METHOD = 405;

    public static final int CONFLICT = 409;

    public static final int UNSUPPORTED_TYPE = 415;

    // 系统内部错误
    public static final int ERROR = 500;

    public static final int NOT_IMPLEMENTED = 501;

    public static final int WARN = 601;
}
