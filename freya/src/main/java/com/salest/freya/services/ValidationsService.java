package com.salest.freya.services;

import com.salest.freya.config.FieldRule;
import com.salest.freya.config.RulesProperties;
import com.salest.freya.dtos.config.UserPasswordValidationsDTO;
import com.salest.freya.dtos.config.UserUsernameValidationsDTO;
import com.salest.freya.dtos.config.UserValidationsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationsService {
    private final RulesProperties rules;

    public ValidationsService(RulesProperties rules) {
        this.rules = rules;
    }

    public UserValidationsDTO getUserValidations(){
        return new UserValidationsDTO(getPasswordValidations(), getUsernameValidations());
    }

    public UserUsernameValidationsDTO getUsernameValidations() {
        FieldRule r = find("user", "username");
        return new UserUsernameValidationsDTO(r.required(), intParam(r, "min"), intParam(r, "max"));
    }

    public UserPasswordValidationsDTO getPasswordValidations() {
        FieldRule r = find("user", "password");
        return new UserPasswordValidationsDTO(r.required(), intParam(r, "min"), intParam(r, "max"));
    }

    private FieldRule find(String form, String field) {
        return rules.getOrDefault(form, List.of()).stream()
                .filter(r -> r.field().equals(field))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Field " + field + " not found"));
    }

    private Integer intParam(FieldRule rule, String key) {
        if (rule.params() == null) return null;
        String v = rule.params().get(key);
        return v == null ? null : Integer.valueOf(v);
    }
}