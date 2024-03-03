package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {
    DashboardPage dashboardPage;
    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities=options;

        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);



    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();

        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        var amount = 500;

        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;

        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);

        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);

        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }


}
