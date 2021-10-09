package model;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.StringJoiner;

/**
 * Модель данных для формы запроса карты.
 */
@Getter
@Setter
@Accessors(chain = true)
public class NewClientInfo {
    private String lastName;
    private String firstName;
    private String secondName;
    private boolean withoutSecondName;
    private String mobile;
    private String email;
    //правильнее было бы загружать список регионов( и их городов для второго дропдаун листа) откуда-нибудь из КЛАДР
    // но для тестового задания оставила как есть
    private final String[] allRegions = {
            "Москва",
            "Московская область",
            "Ярославская область",
            "Тверская область",
            "Санкт-Петербург",
            "Пензенская область"};
    private String region;

    /**
     * Создание объекта для заполненния данных на форме запроса карты.
     * Автоматически в поля записываются валидные значения
     *
     * @param withoutSecondName признак отсутствия отчества
     */
    public NewClientInfo(boolean withoutSecondName) {
        Faker faker = new Faker(new Locale("ru-RU"));
        String[] fullName = faker.name().fullName().split(" ");

        lastName = fullName[0];
        firstName = fullName[1];
        mobile = faker.number().numberBetween(900, 999) + faker.phoneNumber().subscriberNumber(7);
        email = (new Faker()).internet().emailAddress(); //faker.internet().emailAddress();
        this.withoutSecondName = withoutSecondName;
        if (!withoutSecondName) secondName = fullName[2];

        Collections.shuffle(Arrays.asList(allRegions));
        region = Arrays.stream(allRegions).findFirst().get();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(lastName).add(firstName);
        if (withoutSecondName) joiner.add(secondName);
        return joiner.add(mobile).add(email).add(region).toString();
    }
}
