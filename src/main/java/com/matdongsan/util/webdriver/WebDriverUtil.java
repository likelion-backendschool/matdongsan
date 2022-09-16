package com.matdongsan.util.webdriver;

import com.matdongsan.domain.place.PlaceWebInfo;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WebDriverUtil {
    private WebDriver driver;
    public final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public final String WEB_DRIVER_PATH = "/Users/woowang/Desktop/chromedriver";

    @PostConstruct
    public void postConstruct(){
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        // webDriver 옵션 설정.
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);

        // weDriver 생성.
        driver = new ChromeDriver(options);
    }

    public PlaceWebInfo useDriver(String url) throws InterruptedException {
        driver.get(url) ;

        driver.manage().timeouts().implicitlyWait(Duration.of(1000, ChronoUnit.MILLIS));

        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
        List<WebElement> info_menu = driver.findElements(By.className("info_menu"));
        List<WebElement> link_photo = driver.findElements(By.className("link_photo"));
        List<WebElement> bg_present = driver.findElements(By.className("bg_present"));
        String menuStr = info_menu.stream().map(i -> {
            String loss_word = i.findElements(By.className("loss_word")).get(0).getText();
            String price_menu = i.findElements(By.className("price_menu")).get(0).getText();
            return loss_word + "=" + price_menu;
        }).filter(i -> !i.equals("=")).collect(Collectors.joining("|"));
        String photoUrls = link_photo.stream().map(i -> {
            String cssValue = i.getCssValue("background-image");
            if(!StringUtils.hasText(cssValue)) return "";
            log.info("=========== {}", i);
            String substring = cssValue.substring("url:(\"".length()-1);
            return substring.substring(0, substring.length() - 2);
        }).collect(Collectors.joining(","));
        String bg = bg_present.stream().map(i -> {
            String cssValue = i.getCssValue("background-image");
            if(!StringUtils.hasText(cssValue)) return "";
            log.info("=========== {}", i);
            String substring = cssValue.substring("url:(\"".length()-1);
            return substring.substring(0, substring.length() - 2);
        }).collect(Collectors.joining());
        log.info("bg = {}", bg);
        log.info("photoUrls = {}", photoUrls);
        log.info("menuStr = {}", menuStr);
        return new PlaceWebInfo(menuStr, bg,photoUrls);
    }

    @PreDestroy
    private void destroy() {
        driver.quit();
    }




}
