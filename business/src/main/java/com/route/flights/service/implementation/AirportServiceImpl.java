//package com.route.flights.service.implementation;
//
//import com.route.flights.dto.AirportDto;
//import com.route.flights.entity.Airport;
//import com.route.flights.importer.AirportImporter;
//import com.route.flights.mapper.AirportMapper;
//import com.route.flights.repository.AirportRepository;
//import com.route.flights.service.AirportService;
//import com.route.flights.warehouse.implementation.AirportWarehouse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class AirportServiceImpl implements AirportService {
//
//    private final AirportRepository airportRepository;
//    private final AirportWarehouse airportWarehouse;
//    private final AirportImporter airportImporter;
//    private final AirportMapper airportMapper;
//
//    public List<AirportDto> getAll(){
////        List<AirportDto> results = airportWarehouse.getAirports();
////        if(!results.isEmpty()){
////            return results;
////        }
//
//        return getAndStoreAirportFromFile();
//    }
//
//    public void deleteAirport(Long id){
//        Optional<Airport> optionalAirport = findById(id);
//        optionalAirport.ifPresent(airportRepository::delete);
//    }
//
//    private Optional<Airport> findById(Long id){
//        return airportRepository.findById(id);
//    }
//
//    @Transactional
//    private Airport storeEntityToDatabase(Airport airport){
//        return airportRepository.save(airport);
//    }
//
//    private List<AirportDto> getAndStoreAirportFromFile(){
//        List<AirportDto> dtoListFromFile =  airportImporter.readFromStaticFile();
//
//        List<AirportDto> importedData = dtoListFromFile.stream()
//                .map(airportMapper::mapToEntity)
//                .map(this::storeEntityToDatabase)
//                .map(airportMapper::mapToDto)
//                .toList();
//
////        airportWarehouse.addListToCache(importedData);
//
//        return importedData;
//    }
//}
