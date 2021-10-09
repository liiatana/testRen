package model;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;

@Getter
public class NewDepositInfo {
    private final Integer[] enablePeriod = {3, 6, 9, 12, 13, 18};
    private int amount;
    private int months;
    private boolean inOffice;

    public NewDepositInfo() {
        this.amount = (new Faker()).number().numberBetween(1000, 2000000);

        Integer[] shuffleEnabledPeriod = Arrays.copyOf(enablePeriod, enablePeriod.length);
        Collections.shuffle(Arrays.asList(shuffleEnabledPeriod));
        this.months = Arrays.stream(shuffleEnabledPeriod).findAny().get(); //12

        inOffice = true;
    }

    @Override
    public String toString() {
        return (inOffice ? "в отделении банка: " : "в интернет-банке: ") +
                amount + " руб.," +
                months + " мес.";
    }
}
