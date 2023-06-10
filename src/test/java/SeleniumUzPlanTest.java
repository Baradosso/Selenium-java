import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class SeleniumUzPlanTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Baradosso\\Documents\\Git\\Selenium\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://www.plan.uz.zgora.pl/");
        driver.manage().window().setSize(new Dimension(834, 5233));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void checkIfScheduleContainsSpecifiedClass() {
        //given
        driver.findElement(By.linkText("Plan nauczycieli")).click();
        driver.findElement(By.linkText("B")).click();

        //when
        driver.findElement(By.linkText("dr in\u017C. Jacek Bieganowski")).click();

        //then
        assertThat(driver.findElement(By.cssSelector(".main")).getText(), containsString("Seminarium IMEI"));
    }

    @Test
    public void checkIfDifferentRouteGoesToSameSchedule() {
        //given
        driver.findElement(By.linkText("Plan nauczycieli")).click();
        driver.findElement(By.linkText("Instytut Metrologii, Elektroniki i Informatyki")).click();

        //when
        driver.findElement(By.linkText("dr in\u017C. Jacek Bieganowski")).click();

        //then
        assertThat(driver.findElement(By.cssSelector(".main")).getText(), containsString("Seminarium IMEI"));
    }

    @Test
    public void checkIfSpecifiedClassDoesNotHaveClassesWithSelectedTeacher() {
        //given
        driver.findElement(By.linkText("Plan grup")).click();
        driver.findElement(By.linkText("Informatyka")).click();
        driver.findElement(By.partialLinkText("32INF-ISM-SP")).click();

        //when
        driver.findElement(By.id("pg2")).click();

        //then
        assertThat(driver.findElement(By.cssSelector(".main")).getText(), not(containsString("prof. dr hab. in\u017C. Alexander Barkalov")));
    }
}
