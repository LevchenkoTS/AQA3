package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Левченко Татьяна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79151378527");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void shouldSendForm2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Римский-Корсаков Николай");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79151378527");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotSendFormTelIncorrect() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Левченко Татьяна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("91732390");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotSendFormTelEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Левченко Татьяна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotSendFormTelIncorrect2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Левченко Татьяна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("тел");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotSendFormNotAgreement() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Левченко Татьяна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79151378527");
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotSendFormNameEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79151378527");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotSendFormNameIncorrect() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Tatiana");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79151378527");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotSendFormNameIncorrect2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна'д");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79151378527");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
}
