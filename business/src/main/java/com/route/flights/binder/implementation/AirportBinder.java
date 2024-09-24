package com.route.flights.binder.implementation;

import com.route.flights.binder.AbstractBinder;
import com.route.flights.binder.exception.TextBinderErrorCode;
import com.route.flights.binder.exception.TextBinderMismatchArgs;
import com.route.flights.dto.AirportDto;
import com.route.flights.util.StringManipulator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AirportBinder extends AbstractBinder<AirportDto> {

    private final CityBinder cityBinder;

    @Override
    public AirportDto bindFromTextualSourceFile(String... rowStrings) throws TextBinderMismatchArgs{
        if(rowStrings.length < 14){
            throw new TextBinderMismatchArgs(TextBinderErrorCode.NOT_ENOUGH_PARAMS());
        }

        AirportDto airportDto = new AirportDto();

        airportDto.setId(StringManipulator.preventNForLong(rowStrings[0]));
        airportDto.setName(StringManipulator.removeQuotes(rowStrings[1]));
        airportDto.setCity(cityBinder.bindFromTextualSourceFile(rowStrings[2], rowStrings[3]));
        airportDto.setIata(StringManipulator.removeQuotes(rowStrings[4]));
        airportDto.setIcao(StringManipulator.removeQuotes(rowStrings[5]));
        airportDto.setLatitude(StringManipulator.preventNForDouble(rowStrings[6]));
        airportDto.setLongitude(StringManipulator.preventNForDouble(rowStrings[7]));
        airportDto.setAltitude(StringManipulator.preventNForDouble(rowStrings[8]));
        airportDto.setTimezone(StringManipulator.preventNForDouble(rowStrings[9]));
        airportDto.setDst(StringManipulator.removeQuotes(rowStrings[10]));
        airportDto.setTz(StringManipulator.removeQuotes(rowStrings[11]));
        airportDto.setType(StringManipulator.removeQuotes(rowStrings[12]));
        airportDto.setSource(StringManipulator.removeQuotes(rowStrings[13]));

        return airportDto;
    }
}
