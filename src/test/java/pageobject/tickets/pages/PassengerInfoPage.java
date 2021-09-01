package pageobject.tickets.pages;

import model.Reservation;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

public class PassengerInfoPage {
    private BaseFunc baseFunc;

    private final By INFO_TXT = By.xpath(".//span[@class='bTxt']");
    private final By RESPONSE_BLOCK = By.id("response");
    private final By BOOK_BTN = By.id("book2");

    private final By NAME = By.id("name");
    private final By SURNAME = By.id("surname");
    private final By DISCOUNT = By.id("discount");
    private final By ADULT = By.id("adults");
    private final By CHILDREN = By.id("children");
    private final By LUGGAGE = By.id("bugs");
    private final By FLIGHT = By.id("flight");
    private final By GET_PRICE_LINK = By.xpath(".//div[@id = 'fullForm']/span[@style = 'cursor: pointer;']");

    public PassengerInfoPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public List<WebElement> getAirports() {
        return baseFunc.findElements(INFO_TXT);
    }

    public PassengerInfoPage checkIfAirportAre(String from, String to) {
        List<WebElement> airports = getAirports();

        Assertions.assertEquals(from, airports.get(0).getText(), "Wrong departure airport ");
        Assertions.assertEquals(to, airports.get(1).getText(), "Wrong arrival airport");
        return this;

    }

    public PassengerInfoPage checkIfName(String name) {
        Assertions.assertEquals(name, getName(), "Wrong Name in info block!");
        return this;
    }

    public PassengerInfoPage checkIfTotalPriceIs(BigDecimal price) {
        Assertions.assertEquals(price, getPrice(), "Wrong price in info block!");
        return this;
    }

    public PassengerInfoPage submitPassengerInfo(Reservation reservation) {
        baseFunc.type(NAME, reservation.getName());
        baseFunc.type(SURNAME, reservation.getSurname());
        baseFunc.type(DISCOUNT, reservation.getDiscount());
        baseFunc.type(ADULT, reservation.getAdults());
        baseFunc.type(CHILDREN, reservation.getChildren());
        baseFunc.type(LUGGAGE, reservation.getBugs());
        baseFunc.select(FLIGHT, reservation.getFullDate());

        baseFunc.click(GET_PRICE_LINK);

        return this;
    }

    public String getName() {
        return baseFunc.getText(RESPONSE_BLOCK, INFO_TXT).replaceAll("!", "");
    }

    public BigDecimal getPrice() {
        String fullText = baseFunc.getText(RESPONSE_BLOCK);
        String price = StringUtils.substringBetween(fullText, "for ", " EUR");
        return new BigDecimal(price);
    }

    public SeatsPage book() {
        baseFunc.click(BOOK_BTN);
        return new SeatsPage(baseFunc);
    }
}
