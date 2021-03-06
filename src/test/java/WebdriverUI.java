import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

public class WebdriverUI {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void today()  {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1080, 480));
    driver.findElement(By.id("today")).click();
    assertThat(driver.getTitle(), is("Today"));
    driver.findElement(By.id("cityInput")).click();
    driver.findElement(By.id("cityInput")).sendKeys("Aveiro");
    driver.findElement(By.id("button")).click();


  }
  @Test
  public void historic() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1080, 480));
    driver.findElement(By.id("historic")).click();
    assertThat(driver.getTitle(), is("Historic"));
    driver.findElement(By.id("cityInput")).click();
    driver.findElement(By.id("cityInput")).sendKeys("Aveiro");
    driver.findElement(By.id("dateStartInput")).click();
    driver.findElement(By.id("dateStartInput")).sendKeys("2021-05-11");
    driver.findElement(By.id("dateEndInput")).click();
    driver.findElement(By.id("dateEndInput")).sendKeys("2021-05-13");
    driver.findElement(By.id("button")).click();
  }

  @Test
  public void date() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1080, 480));
    driver.findElement(By.id("date")).click();
    assertThat(driver.getTitle(), is("Date"));
    driver.findElement(By.id("cityInput")).click();
    driver.findElement(By.id("cityInput")).sendKeys("Aveiro");
    driver.findElement(By.id("dateInput")).click();
    driver.findElement(By.id("dateInput")).sendKeys("2021-05-11");
    driver.findElement(By.id("button")).click();
  }

}
