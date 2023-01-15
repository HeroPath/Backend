package com.gianca1994.aowebbackend.resources.jwt;

public class JWTConst {
    public static final String USER_NOT_FOUND = "User not found";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    public static final String USER_DISABLED = "USER_DISABLED";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String UNABLE_GET_TOKEN = "Unable to get JWT Token";
    public static final String TOKEN_EXPIRED = "JWT Token has expired";
    public static final String TOKEN_ADULTERATED = "JWT adulterated";
    public static final String TOKEN_NOT_BEARER = "JWT Token does not begin with Bearer String";
    public static final String EMAIL_NOT_VALID = "Invalid email address";
    public static final String USERNAME_NOT_VALID = "Username must be alphanumeric";
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]*$";
    public static final String USERNAME_EXISTS = "Username already exists";
    public static final String USERNAME_LENGTH = "Username must be between 3 and 20 characters";
    public static final String PASSWORD_LENGTH = "Password must be between 3 and 20 characters";
    public static final String CLASS_NOT_FOUND = "Class not found";

}
