package com.salest.freya.config;

import java.util.Map;

public record FieldRule(String field, String type, boolean required, Map<String, String> params) {}
