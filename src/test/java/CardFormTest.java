import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardFormTest {

    @Test
    void shouldRequestForm() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+78001231234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldRequestFormOnlyName() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иван");
        $("[data-test-id=phone] input").setValue("+78001231234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldRequestFormAddPatronymic() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иван Иванович Иванов");
        $("[data-test-id=phone] input").setValue("+78001231234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=order-success]").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldRequestFormLatin() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Ivan Ivanov");
        $("[data-test-id=phone] input").setValue("+78001231234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldRequestFormSymbols() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("$#*_?/");
        $("[data-test-id=phone] input").setValue("+78001231234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldRequestFormEmptyName() {
        open("http://localhost:9999");
        $("[data-test-id=phone] input").setValue("+78001231234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(
                exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRequestFormCheckBoxOff() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+78001231234");
        $("button.button").click();

        $("[data-test-id=agreement]").should(cssClass("input_invalid"));
    }

    @Test
    void shouldRequestFormNumberWithoutPlus() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("78001231234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldRequestFormNumberLessSymbols() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+7800123123");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldRequestFormNumberMoreSymbols() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+780012312345");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

}
