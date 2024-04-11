package com.pedro.sphynx.infrastructure.exceptions;

public class Validation extends RuntimeException{
    public Validation(String s){
        super(s);
    }
}
