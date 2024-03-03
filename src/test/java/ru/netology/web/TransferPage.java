package ru.netology.web;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferHead = $(byText("Пополнение карты"));
    private final SelenideElement amountInputNew = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement errorMessage = $("[data-test-id='error-message']");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }
        public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
            makeTransfer(amountToTransfer, cardInfo);
            return new DashboardPage();
        }

        public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
            amountInputNew.setValue(amountToTransfer);
            fromInput.setValue(cardInfo.getCardNumber());
            transferButton.click();
        }
    }
