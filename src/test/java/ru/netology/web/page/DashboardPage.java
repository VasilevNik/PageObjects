package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private static ElementsCollection cards = $$(".list__item");
    public String cardOneInnerText;
    public String cardTwoInnerText;
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getFirstCardBalance() {
        cardOneInnerText = cards.get(0).text();
        return extractBalance(cardOneInnerText);
    }

    public int getSecondCardBalance() {
        cardTwoInnerText = cards.get(1).text();
        return extractBalance(cardTwoInnerText);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferByCardPage selectCardButton(String cardId) {
        SelenideElement element = $("[data-test-id='" + cardId + "']");
        element.find("button[data-test-id=action-deposit]").click();
        return new TransferByCardPage();
    }
}
