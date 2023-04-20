package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    DashboardPage dashboardPage;
    private int cardOneBalanceStart;
    private int cardTwoBalanceStart;
    private int cardOneBalanceFinish;
    private int cardTwoBalanceFinish;

    @BeforeEach
    void actions() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        cardOneBalanceStart = dashboardPage.getFirstCardBalance();
        cardTwoBalanceStart = dashboardPage.getSecondCardBalance();
    }

    @Test
    void transferFromCard2ToCard1() {
        int sum = 5000;
        var cardInfo = DataHelper.getCardOneInfo();
        var transferByCardPage = dashboardPage.selectCardButton(cardInfo.getCardId());
        dashboardPage = transferByCardPage.topUpAccountCard(sum, DataHelper.getCardTwoInfo().getCardNumber());
        cardOneBalanceFinish = dashboardPage.getFirstCardBalance();
        cardTwoBalanceFinish = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(cardOneBalanceStart + sum, cardOneBalanceFinish);
        Assertions.assertEquals(cardTwoBalanceStart - sum, cardTwoBalanceFinish);
    }

    @Test
    void transferFromCard1ToCard2() {
        int sum = 10000;
        var cardInfo = DataHelper.getCardTwoInfo();
        var transferByCardPage = dashboardPage.selectCardButton(cardInfo.getCardId());
        dashboardPage = transferByCardPage.topUpAccountCard(sum, DataHelper.getCardOneInfo().getCardNumber());
        cardOneBalanceFinish = dashboardPage.getFirstCardBalance();
        cardTwoBalanceFinish = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(cardTwoBalanceStart + sum, cardTwoBalanceFinish);
        Assertions.assertEquals(cardOneBalanceStart - sum, cardOneBalanceFinish);
    }

    @Test
    void transferFromCard1ToCard2ExceedingBalance() {
        int sum = 21000;
        var cardInfo = DataHelper.getCardTwoInfo();
        var transferByCardPage = dashboardPage.selectCardButton(cardInfo.getCardId());
        dashboardPage = transferByCardPage.topUpAccountCard(sum, DataHelper.getCardOneInfo().getCardNumber());
        cardOneBalanceFinish = dashboardPage.getFirstCardBalance();
        Assertions.assertEquals(cardOneBalanceStart, cardOneBalanceFinish);
    }

}
