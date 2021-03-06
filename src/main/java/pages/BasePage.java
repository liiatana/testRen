package pages;

import io.qameta.allure.Step;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

/**
 * Базовая страница ( с методами для работы с базовыми элементами)
 */
public class BasePage {

    protected WebDriver wd;

    public BasePage(WebDriver wd) {
        this.wd = wd;
    }

    /**
     * Ввести данные в текстовое поле
     *
     * @param element поле ввода
     * @param text    вводимая строка
     */
    @Step("Ввести: {text}")
    protected void type(WebElement element, String text) {
        if (text != null) {
            String existingValue = element.getAttribute("value");
            if (!existingValue.equals(text)) {
                element.click();
                element.clear();
                element.sendKeys(text);
            }
        }
    }

    /**
     * Установить положение чекбокса
     *
     * @param element чекбокс
     * @param value   состояние
     */
    @Step("Установить чекбокс в значение: {value}")
    protected void setCheckBox(WebElement element, boolean value) {
        if (element.getAttribute("class").contains("checked") != value)
            element.click();
    }

    /**
     * Подвинуть слайдер в заданное положение
     *
     * @param element слайдер
     * @param value   новое положение слайдера
     * @param range   весь диапазон значений слайдера
     */
    @Step("Подвинуть ползунок на значение: {value}")
    protected void setSlider(WebElement element, int value, Integer[] range) {
        // Координаты slider
        Rectangle sliderRectangle = element.findElement(By.xpath(".//div")).getRect();

        //Определение "шага" и центра slider
        int step = sliderRectangle.getWidth() / (range.length - 1);
        int center = sliderRectangle.getWidth() / 2;

        //Определение смещения по оси Х относительно центра слайдера для клика на slider  над выбранным значением
        HashMap<Integer, Integer> mapXCoordinates = new HashMap<>();
        List<Integer> values = Arrays.asList(range);
        values.forEach(x -> mapXCoordinates.put(x, step * values.indexOf(x) - center));

        //Собственно клик с найденным смещением по оси X
        (new Actions(wd)).moveToElement(element)
                .moveByOffset(mapXCoordinates.get(value), 0)
                .click()
                .build()
                .perform();

    }

    /**
     * Проскроллить до элемента
     *
     * @param element элемент
     */
    protected void scrollTo(WebElement element) {
        Actions actions = new Actions(wd);
        actions.moveToElement(element);
        actions.perform();
    }

    /**
     * Скачать файл
     *
     * @param element  элемент
     * @param fileName имя скачиваемого файла
     * @param toFolder каталог назначения
     * @return объект File
     * @throws IOException
     */
    protected File downloadFile(WebElement element, String fileName, File toFolder) throws IOException {
        if (element == null) return null;
        //scrollTo(element);

        File downloadLink = new File(String.format("%s/%s", toFolder.getAbsolutePath(), fileName));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(element.getAttribute("href"));
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet, new BasicHttpContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        copyInputStreamToFile(Objects.requireNonNull(response).getEntity().getContent(), downloadLink);

        return downloadLink;
    }

    /**
     * Выбор значения в выпадающем списке
     *
     * @param element элемент
     * @param value   выбираемое значение
     */
    @Step("В выпадающем списке выбрать: {value}")
    protected void setDropdownList(WebElement element, String value) {
        //раскрыть выпадающий список
        element.findElement(By.className("jq-selectbox__trigger")).click();

        //найти в списке заданный элемент по соответствию текста и нажать на него
        element
                .findElements(By.xpath(".//div[@class='jq-selectbox__dropdown']/ul/li"))
                .stream()
                .filter(x -> x.getText().equalsIgnoreCase(value))
                .findFirst()
                .get()
                .click();
    }
}
