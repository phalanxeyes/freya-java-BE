package com.salest.freya.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "rules")
public class RulesProperties extends LinkedHashMap<String, List<FieldRule>> {
}