package org.boiar.walletmanagement.shared.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.catalina.mapper.Mapper;

import java.util.HashMap;
import java.util.Map;


/*
* Jpa converter used to store multilingual values
* inside a single JSON/JSONB database column.
* 
* Converts Java Map objects to JSON strings when saving
* to the database, and converts JSON strings back to Map
* objects when reading from the database.
* 
*  Commonly used for multilingual fields such as:
*  {
*       "ar": "مرحبا",
*       "en": "Hello",
*  }
*
* */




@Converter
public class JsonMapConverter implements AttributeConverter<Map<String, String>, String> {

    // shared object for all app
    private static final ObjectMapper MAPPER =  new ObjectMapper();
    // carrying type because Type Erasure issue (at runtime)
    private static final TypeReference<Map<String, String>> TYPE = new TypeReference<>() {};


    @Override
    public String convertToDatabaseColumn(Map<String, String> map) {
       if (map == null || map.isEmpty()) return "{}";
       try {
            return MAPPER.writeValueAsString(map);
       } catch (JsonProcessingException e) {
           throw new IllegalStateException("Failed to serialize map to JSON", e);
       }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String json) {
        if (json == null || json.isBlank() || json.equals("{}")) return new HashMap<>();
        try {
            return MAPPER.readValue(json, TYPE);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize JSON to map: " + json, e);
        }
    }
}
