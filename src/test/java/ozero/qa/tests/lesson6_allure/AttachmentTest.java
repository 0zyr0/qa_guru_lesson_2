package ozero.qa.tests.lesson6_allure;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class AttachmentTest {

    public static final String REPOSITORY = "eroshenkoam/allure-example";
    public static final int NUMBER = 68;

    WebSteps steps = new WebSteps();

    @Test
    public void lambdaStepAttachmentTest() {

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Переходим в репозиторий " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            Allure.addAttachment("Page Source", "text/html", WebDriverRunner.source(), "html");
            $(partialLinkText("Issues")).click();
        });
    }

    @Test
    public void annotatedStepsTest() {
        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.openRepositoryPage(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(NUMBER);
    }

    @AfterEach
    public void saveSources() {
        steps.attachPageSource();
    }

}