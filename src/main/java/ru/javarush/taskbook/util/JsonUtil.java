package ru.javarush.taskbook.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import ru.javarush.taskbook.util.mapper.HibernateAwareObjectMapper;

import java.io.IOException;

/**
 * User: blacky
 * Date: 23.11.2014
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new HibernateAwareObjectMapper();

    public static <T> MappingIterator<T> readValues(String json, Class<T> clazz) throws IOException {
        ObjectReader reader = mapper.reader(clazz);
        return reader.readValues(json);
    }

    public static <T> T readValue(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static <T> String writeValue(T obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }
}
