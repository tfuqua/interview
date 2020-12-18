package com.interview.model;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenreListConverter implements AttributeConverter<List<Genre>, String> {

    public static final String DELIMITER = "|";

    @Override
    public String convertToDatabaseColumn(List<Genre> list) {
        return list == null || list.isEmpty() ? "" :
                list.stream()
                        .map(genre -> genre.name())
                        .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<Genre> convertToEntityAttribute(String data) {
        return data == null || data.isEmpty() ?
                Collections.EMPTY_LIST :
                Arrays.stream(data.split("\\" + DELIMITER))
                        .map(name -> Genre.valueOf(name))
                        .collect(Collectors.toList());
    }

}
