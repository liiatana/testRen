package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Вспомогательный enum для навигации по меню
 */
@Getter
@AllArgsConstructor
public enum Sections {

    CARDS("Карты", "cards"),
    DEPOSIT("Вклады", "deposits");

    private final String name;
    private final String urlPart;

}
