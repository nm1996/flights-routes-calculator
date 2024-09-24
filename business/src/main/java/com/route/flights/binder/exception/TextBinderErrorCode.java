package com.route.flights.binder.exception;

public class TextBinderErrorCode {
    private TextBinderErrorCode(){}

    public static String NOT_ENOUGH_PARAMS (){
        return "Not enough params provided for making DTO";
    }
}
