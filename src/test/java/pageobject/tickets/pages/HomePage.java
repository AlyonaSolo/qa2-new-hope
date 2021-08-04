package pageobject.tickets.pages;

import org.openqa.selenium.By;

public class HomePage {
    private BaseFunc baseFunc;

    private final By FROM = By.id("afrom");
    private final By TO = By.id("bfrom");
    private final By GO_BTN = By.xpath(".//span [@class = 'gogogo']");

    public HomePage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public PassengerInfoPage selectAiroports(String from, String to) {
        baseFunc.select(FROM, from);
        baseFunc.select(TO, to);
        baseFunc.click(GO_BTN);

        return new PassengerInfoPage(baseFunc);
    }
}
