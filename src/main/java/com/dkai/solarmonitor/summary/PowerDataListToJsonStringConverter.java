package com.dkai.solarmonitor.summary;

import com.dkai.solarmonitor.powerdata.PowerData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class PowerDataListToJsonStringConverter implements AttributeConverter<List<PowerData>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<PowerData> attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public List<PowerData> convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, new TypeReference<>() {
        });
    }
}
