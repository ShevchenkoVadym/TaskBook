package ru.javarush.taskbook.util.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * User: blacky
 * Date: 23.11.2014
 * <p/>
 * Handling Hibernate lazy-loading
 *
 * @See https://github.com/FasterXML/json-datatype-hibernate
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
//        setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
//        setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
//        setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
}