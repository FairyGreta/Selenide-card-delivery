package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.Selectors.withText;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendForm() {
        $("[data-test-id='city'] input").setValue("Казань");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Встреча успешно забронирована на " + deliveryDate));
    }
}