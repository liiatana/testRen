package rc.tests;

import base.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import model.NewClientInfo;
import model.NewDepositInfo;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.qameta.allure.SeverityLevel.NORMAL;
import static model.Sections.CARDS;
import static model.Sections.DEPOSIT;

public class Tests extends TestBase {

    @Epic(value = "Тестовые задания")
    @Feature(value = "Задание 1")
    @Test(testName = "Задание 1",
            description = "Скачать файл со страницы")
    @Severity(NORMAL)
    public void downloadFile() throws IOException {
        // по заданию: Выгрузить Печатную Форму "Общие условия по вкладам"
        // но фактически не нашла такой формы
        // поэтому для демонстрации скачивания используется, то что есть ( внизу страницы есть некие *.pdf)

        tmanager.getMainPage()
                .openSection(DEPOSIT)
                .selectAnyDeposit()
                .depositInOffice()
                .fillForm(new NewDepositInfo())
                .downloadGuide("popolnenie.pdf", tmanager.getDownloadsFolder());

        // some assertion
    }

    @Epic(value = "Тестовые задания")
    @Feature(value = "Задание 2")
    @Test(testName = "Задание 2",
            description = "Заполнение реквизитов для получения карты")
    @Severity(NORMAL)
    public void printableForm() {

        tmanager.getMainPage()
                .openSection(CARDS)
                .selectAnyCard()
                .cardAcure()
                .fillForm(new NewClientInfo(true));

        // some assertion
    }

}
