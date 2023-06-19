import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class SeleniumAllegroLokalnieTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions action;
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Baradosso\\Documents\\Git\\Selenium\\geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        action = new Actions(driver);
        driver.get("https://allegrolokalnie.pl/");
        driver.manage().window().setSize(new Dimension(1334, 5233));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void shouldFindOpelAstraOsobowe() {
        //given
        wait.until(d -> d.findElement(By.cssSelector("button[id='cookies_reject']")));
        driver.findElement(By.cssSelector("button[id='cookies_reject']")).click();
        driver.findElement(By.cssSelector("input[id='suggests-search']")).sendKeys("Opel Astra");
        wait.until(d -> d.findElement(By.cssSelector("li[id='react-autowhatever-1--item-0']")));

        //when
        driver.findElement(By.cssSelector("li[id='react-autowhatever-1--item-0']")).click();

        //then
        final String content = driver.findElement(By.cssSelector("div[class='listing-filters__body']")).getText();
        assertThat(content, containsString("Rodzaj paliwa"));
    }

    @Test
    public void shouldFindOpelAstraOsoboweWithManualnaFilter() {
        //given
        wait.until(d -> d.findElement(By.cssSelector("button[id='cookies_reject']")));
        driver.findElement(By.cssSelector("button[id='cookies_reject']")).click();

        driver.findElement(By.cssSelector("input[id='suggests-search']")).sendKeys("Opel Astra");

        wait.until(d -> d.findElement(By.cssSelector("li[id='react-autowhatever-1--item-0']")));
        driver.findElement(By.cssSelector("li[id='react-autowhatever-1--item-0']")).click();

        wait.until(d -> d.findElement(By.cssSelector("label[for='filter-attrib-199-199_2']")));
        driver.findElement(By.cssSelector("label[for='filter-attrib-199-199_2']")).click();

        //when
        driver.findElement(By.cssSelector("button[id='search-sidebar__submit-buton']")).click();


        //then
        final String content = driver.findElement(By.cssSelector("span[class='active-filter__value']")).getAttribute("innerHTML");
        assertThat(content, containsString("Manualna"));
    }
}
