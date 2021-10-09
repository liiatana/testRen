package model;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.util.Locale;
import java.util.StringJoiner;

@Getter
public class NewClientInfo {
    private String lastName;
    private String firstName;
    private String secondName;
    private boolean withoutSecondName;
    private String mobile;
    private String email;

    public NewClientInfo(boolean withoutSecondName) {
        Faker faker = new Faker(new Locale("ru-RU"));
        String[] fullName = faker.name().fullName().split(" ");

        lastName = fullName[0];
        firstName = fullName[1];
        mobile = faker.number().numberBetween(900, 999) + faker.phoneNumber().subscriberNumber(7);
        email = (new Faker()).internet().emailAddress(); //faker.internet().emailAddress();
        this.withoutSecondName = withoutSecondName;
        if (!withoutSecondName) secondName = fullName[2];
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(lastName).add(firstName);
        if (withoutSecondName) joiner.add(secondName);
        return joiner.add(mobile).add(email).toString();
    }
}
