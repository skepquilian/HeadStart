package com.example.headstart;

import java.util.regex.Pattern;

public class Utility {

    /**
     * For password validation, before user can sign up successfully, user must provide strong password
     * password which contains the list below,
     **/
    public static final Pattern PASSWORD_PATTERN
            = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +                 // at least 1 digit
                    "(?=.*[a-z])" +                 // at least 1 lower case letter
                    "(?=.*[A-Z])" +                 // at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +            // at least 1 special character
                    "(?=\\S+$)" +                   // no white spaces
                    ".{8,}" +                       // at least 8 characters
                    "$"                             //
    );
}
