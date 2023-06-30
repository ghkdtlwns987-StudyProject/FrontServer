package com.front.studyprojectfrontserver.Domain.Exception;

public class RestException extends RuntimeException{
    public RestException(String s){
        super(s);
    }
}
