package com.pedro.sphynx.infrastructure.exceptions;

public class AccessValidation extends RuntimeException{
    public AccessValidation(String s){
        super(s);
    }
}
