import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class SeleniumWKTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Baradosso\\Documents\\Git\\Selenium\\geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://rezerwacja.zielona-gora.pl/");
        driver.manage().window().setSize(new Dimension(834, 5233));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void shouldInformAboutAllErrorsInForm() {
        //given
        wait.until(d -> d.findElement(By.xpath("//button[contains(., 'Przejd\u017A do podsumowania')]")));

        //when
        driver.findElement(By.xpath("//button[contains(., 'Przejd\u017A do podsumowania')]")).click();

        //then
        final String errorBlock = driver.findElement(By.cssSelector("div[class='error-block']")).getText();
        assertThat(errorBlock, containsString("Formularz zawiera b\u0142\u0119dy:"));
        assertThat(errorBlock, containsString("Nale\u017Cy wybra\u0107 godzin\u0119"));
        assertThat(errorBlock, containsString("Nie podano wymaganych dodatkowych parametr\u00F3w rezerwacji"));
    }

    @Test
    public void shouldInformAboutOneErrorInForm() {
        //given
        wait.until(d -> d.findElement(By.cssSelector("span[class='multiselect__single']")));
        driver.findElement(By.cssSelector("span[class='multiselect__single']")).click();

        wait.until(d -> d.findElement(By.xpath("//span[contains(.,'Odbi\u00F3r dowodu rejestracyjnego')]")));
        driver.findElement(By.xpath("//span[contains(.,'Odbi\u00F3r dowodu rejestracyjnego')]")).click();

        wait.until(d -> d.findElement(By.xpath("//div[contains(@class, 'calendar-wrapper field is-required')]" +
                                               "//following::div[contains(@class, 'calendar')]" +
                                               "//following::div[contains(@class, 'calendar-days')]" +
                                               "//following::div[contains(@class, 'calendar-days-list')]" +
                                               "//following::div[contains(@class, 'calendar-days-list-cell is-valid')]")));
        driver.findElement(By.xpath("//div[contains(@class, 'calendar-days-list-cell is-valid')]")).click();

        wait.until(d -> d.findElement(By.cssSelector("div[class='hours-list-item']")));
        driver.findElement(By.cssSelector("div[class='hours-list-item']")).click();

        wait.until(d -> d.findElement(By.xpath("//button[contains(., 'Przejd\u017A do podsumowania')]")));

        //when
        driver.findElement(By.xpath("//button[contains(., 'Przejd\u017A do podsumowania')]")).click();

        //then
        final String errorBlock = driver.findElement(By.cssSelector("div[class='error-block']")).getText();
        assertThat(errorBlock, containsString("Formularz zawiera b\u0142\u0119dy:"));
        assertThat(errorBlock, containsString("Nie podano wymaganych dodatkowych parametr\u00F3w rezerwacji"));
        assertThat(errorBlock, not(containsString("Nale\u017Cy wybra\u0107 godzin\u0119")));

    }

    @Test
    public void shouldReserveDateForReceiptOfTheRegistrationCertificate() {
        //given
        final String name = "Barabasz";
        final String vin = "12345";
        wait.until(d -> d.findElement(By.cssSelector("span[class='multiselect__single']")));
        driver.findElement(By.cssSelector("span[class='multiselect__single']")).click();

        wait.until(d -> d.findElement(By.xpath("//span[contains(.,'Odbi\u00F3r dowodu rejestracyjnego')]")));
        driver.findElement(By.xpath("//span[contains(.,'Odbi\u00F3r dowodu rejestracyjnego')]")).click();

        wait.until(d -> d.findElement(By.xpath("//div[contains(@class, 'calendar-wrapper field is-required')]" +
                                               "//following::div[contains(@class, 'calendar')]" +
                                               "//following::div[contains(@class, 'calendar-days')]" +
                                               "//following::div[contains(@class, 'calendar-days-list')]" +
                                               "//following::div[contains(@class, 'calendar-days-list-cell is-valid')]")));
        driver.findElement(By.xpath("//div[contains(@class, 'calendar-days-list-cell is-valid')]")).click();

        wait.until(d -> d.findElement(By.cssSelector("div[class='hours-list-item']")));
        driver.findElement(By.cssSelector("div[class='hours-list-item']")).click();

        driver.findElement(By.xpath("//label[contains(.,'Dane w\u0142a\u015Bciciela pojazdu')]//following::input[1]")).sendKeys(name);
        driver.findElement(By.xpath("//label[contains(.,'Nr rej. pojazdu lub w przypadku pojazdu nowego i z zagranicy 5 ostatnich cyfr nr VIN')]//following::input[1]")).sendKeys(vin);
        driver.findElement(By.xpath("//button[contains(., 'Przejd\u017A do podsumowania')]")).click();

        wait.until(d -> d.findElement(By.linkText("Zobacz szczeg\u00F3\u0142y")));

        //when
        driver.findElement(By.linkText("Zobacz szczeg\u00F3\u0142y")).click();

        //then
        assertThat(driver.findElement(By.xpath("//span[contains(.,'Dane w\u0142a\u015Bciciela pojazdu')]//following::span[1]")).getText(), containsString(name));
        assertThat(driver.findElement(By.xpath("//span[contains(.,'Nr rej. pojazdu lub w przypadku pojazdu nowego i z zagranicy 5 ostatnich cyfr nr VIN')]//following::span[1]")).getText(), containsString(vin));
    }
}
