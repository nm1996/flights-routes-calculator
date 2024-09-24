package com.route.flights.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class FileReader {

    private final ResourceLoader resourceLoader;

    public List<String[]> readFileFromStaticResources(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/" + fileName);
        File file = resource.getFile();

        List<String[]> result = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()){
            var stringRow = scanner.nextLine().split(",");
            result.add(stringRow);
        }
        scanner.close();

        return result;
    }

}
