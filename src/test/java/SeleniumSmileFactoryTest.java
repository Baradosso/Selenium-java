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

public class SeleniumSmileFactoryTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Baradosso\\Documents\\Git\\Selenium\\geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        driver.get("http://server664844.nazwa.pl/");
        driver.manage().window().setSize(new Dimension(834, 5233));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void shouldShowAboutUsText() {
        //given
        wait.until(d -> d.findElement(By.linkText("Wynajem Namiotów")));

        //when
        driver.findElement(By.linkText("Wynajem Namiotów")).click();

        //then
        final String content = driver.findElement(By.cssSelector("div[class='about_us']")).getText();
        assertThat(content, containsString("Od niedawana w naszej us³udze pojawi\u0142y si\u0119 namioty"));
    }

    @Test
    public void shouldShowErrorAboutIncorrectEmail() {
        //given
        wait.until(d -> d.findElement(By.cssSelector("div[class='burger']")));
        driver.findElement(By.cssSelector("div[class='burger']")).click();

        wait.until(d -> d.findElement(By.linkText("Zaloguj si\u0119")));
        driver.findElement(By.linkText("Zaloguj si\u0119")).click();
        driver.findElement(By.cssSelector("input[name='uid']")).sendKeys("123");
        driver.findElement(By.cssSelector("input[name='pwd']")).sendKeys("123");

        //when
        driver.findElement(By.cssSelector("button[name='submit']")).click();

        //then
        final String content = driver.findElement(By.cssSelector("div[class='bubble']")).getText();
        assertThat(content, containsString("Niepoprawny e-mail!"));
    }

    @Test
    public void shouldShowErrorAboutEmptyFields() {
        //given
        wait.until(d -> d.findElement(By.cssSelector("div[class='burger']")));
        driver.findElement(By.cssSelector("div[class='burger']")).click();

        wait.until(d -> d.findElement(By.linkText("Zaloguj si\u0119")));
        driver.findElement(By.linkText("Zaloguj si\u0119")).click();

        //when
        driver.findElement(By.cssSelector("button[name='submit']")).click();

        //then
        final String content = driver.findElement(By.cssSelector("div[class='bubble']")).getText();
        assertThat(content, containsString("Pola nie mog\u0105 by\u0107 puste!"));
    }

    @Test
    public void shouldLogIntoAccount() {
        //given
        wait.until(d -> d.findElement(By.cssSelector("div[class='burger']")));
        driver.findElement(By.cssSelector("div[class='burger']")).click();

        wait.until(d -> d.findElement(By.linkText("Zaloguj si\u0119")));
        driver.findElement(By.linkText("Zaloguj si\u0119")).click();
        driver.findElement(By.cssSelector("input[name='uid']")).sendKeys("Baradosso");
        driver.findElement(By.cssSelector("input[name='pwd']")).sendKeys("Mikolaj_Administracja_2137");

        //when
        driver.findElement(By.cssSelector("button[name='submit']")).click();

        //then
        final String content = driver.findElement(By.cssSelector("input[id='email']")).getAttribute("value");
        assertThat(content, containsString("mikolajhaglauer@gmail.com"));
    }
}
