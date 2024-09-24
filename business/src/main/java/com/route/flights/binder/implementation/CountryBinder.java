package com.route.flights.binder.implementation;

import com.route.flights.binder.AbstractBinder;
import com.route.flights.binder.exception.TextBinderErrorCode;
import com.route.flights.binder.exception.TextBinderMismatchArgs;
import com.route.flights.dto.CountryDto;
import org.springframework.stereotype.Component;

@Component
public class CountryBinder extends AbstractBinder<CountryDto> {


    @Override
    public CountryDto bindFromTextualSourceFile(String... rowStrings) throws TextBinderMismatchArgs {
        if(rowStrings.length < 1){
            throw new TextBinderMismatchArgs(TextBinderErrorCode.NOT_ENOUGH_PARAMS());
        }
        return new CountryDto(rowStrings[0]);
    }
}
