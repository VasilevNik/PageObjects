package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferByCardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement sum = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement topUpButton = $("[data-test-id=action-transfer]");


    public TransferByCardPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage topUpAccountCard(int amount, String cardNumber) {
        sum.setValue(String.valueOf(amount));
        from.setValue(cardNumber);
        topUpButton.click();
        return new DashboardPage();
    }
}
