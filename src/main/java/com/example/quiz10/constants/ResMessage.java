package com.example.quiz10.constants;

public enum ResMessage {
    SUCCESS(200,"Success"),
    DATE_ERROR(400,"Start date cannot be later than end date "),
    OPTION_ERROR(400,"Options cannot be error"),
    QUIZ_NOT_FOUND(404,"quiz not found"),
    QUIZ_ID_NOT_MATCH(400,"quiz id not match"),
    QUIZ_IN_PROGRESS(400,"quiz is in progress"),
    QUIZ_ID_OR_EMAIL_INCONSISTENT(400,"Quiz id or email inconsistent"),
    EMAIL_DUPLICATED(400,"Email is duplicated"),
    CANNOT_FILLIN_QUIZ(400,"Cannot fillin quiz"),
    FILLIN_INCOMPLETE(400,"Fillin incomplete"),
    FILLIN_IN_IS_NECESSARY(400,"Fillin is necessary"),
    QUID_MISMATCH(400,"Quid mismatch"),
    SINGLE_CHOICE_QUES(400,"Single choice ques"),
    OPTION_ANSWER_MISMATCH(400,"Option answer mismatch"),
    USER_NAME_EXISTED(400,"User name existed"),
    USER_NAME_NOT_FOUND(400,"User name not found"),
    PASSWORD_INCONSISTENT(400,"Password inconsistent"),
    PLEASE_LOGIN_FIRST(400,"Please login first");


    private final int code;
    private final String message;

    ResMessage(int code, String message) {
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
