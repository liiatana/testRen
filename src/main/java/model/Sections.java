package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sections {

    CARDS("Карты", "cards", "карту"),
    DEPOSIT("Вклады", "deposits", "вклад");

    private final String name;
    private final String urlPart;
    private final String allureText;
}
