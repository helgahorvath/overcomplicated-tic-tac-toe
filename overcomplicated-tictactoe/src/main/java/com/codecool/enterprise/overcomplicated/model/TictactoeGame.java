package com.codecool.enterprise.overcomplicated.model;


import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TictactoeGame {

    private Map<Integer, String> fields = new HashMap<>(9);
    private static final List<Integer> CONDITION1 = new ArrayList<>(Arrays.asList(0, 1, 2));
    private static final List<Integer> CONDITION2 = new ArrayList<>(Arrays.asList(0, 3, 6));
    private static final List<Integer> CONDITION3 = new ArrayList<>(Arrays.asList(0, 4, 8));
    private static final List<Integer> CONDITION4 = new ArrayList<>(Arrays.asList(1, 4, 7));
    private static final List<Integer> CONDITION5 = new ArrayList<>(Arrays.asList(2, 5, 8));
    private static final List<Integer> CONDITION6 = new ArrayList<>(Arrays.asList(2, 4, 6));

    public void fillFields() {
        for (int i = 0; i < 9; i++) {
            this.fields.put(i, "");
        }
    }

    public void updateField(Integer square, String sign) {
        this.fields.replace(square, sign);
    }

    public boolean isGameWon(String sign) {
        List<Integer> playersFields = extractFieldsBySign(sign);

        return playersFields.containsAll(CONDITION1) || playersFields.containsAll(CONDITION2) ||
                playersFields.containsAll(CONDITION3) || playersFields.containsAll(CONDITION4) ||
                playersFields.containsAll(CONDITION5) || playersFields.containsAll(CONDITION6);
    }

    private List<Integer> extractFieldsBySign(String value) {
        return fields.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
