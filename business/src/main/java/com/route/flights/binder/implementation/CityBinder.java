package com.route.flights.binder.implementation;


import com.route.flights.binder.AbstractBinder;
import com.route.flights.binder.exception.TextBinderErrorCode;
import com.route.flights.binder.exception.TextBinderMismatchArgs;
import com.route.flights.dto.CityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CityBinder extends AbstractBinder<CityDto> {

    private final CountryBinder countryBinder;


    @Override
    public CityDto bindFromTextualSourceFile(String... fileRowRecord) throws TextBinderMismatchArgs {
        if(fileRowRecord.length < 2){
            throw new TextBinderMismatchArgs(TextBinderErrorCode.NOT_ENOUGH_PARAMS());
        }

        CityDto cityDto = new CityDto();

        cityDto.setName(fileRowRecord[0]);
        cityDto.setCountry(countryBinder.bindFromTextualSourceFile(fileRowRecord[1]));

        return cityDto;
    }
}
