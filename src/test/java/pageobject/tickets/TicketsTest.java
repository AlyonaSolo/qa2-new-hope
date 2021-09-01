package pageobject.tickets;

import model.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pageobject.tickets.pages.*;

import java.math.BigDecimal;
import java.util.List;

public class TicketsTest {
    private final String URL = "qaguru.lv:8089/tickets";
    private Reservation reservation = new Reservation();
    private BaseFunc baseFunc = new BaseFunc();

    @BeforeEach
    public void prepreData() {
        reservation.setAfrom("RIX");
        reservation.setAto("SVO");
        reservation.setName("random");
        reservation.setSurname("The tester");
        reservation.setDiscount("CCCCCC");
        reservation.setAdults(4);
        reservation.setChildren(2);
        reservation.setBugs(2);
        reservation.setFullDate("16-05-2018");
        reservation.setSeat(21);
    }

    @Test
    public void checkReservation() {
        baseFunc.openPage(URL);
        HomePage homePage = new HomePage(baseFunc);


        homePage.selectAiroports(reservation.getAfrom(), reservation.getAto())
                .checkIfAirportAre(reservation.getAfrom(), reservation.getAto())
                .submitPassengerInfo(reservation)
                .checkIfName(reservation.getName())
                .checkIfTotalPriceIs(new BigDecimal(3600))
                .book()
                .selectSeat(reservation.getSeat())
                .checkIfSelectedSeatIs(reservation.getSeat())
                .book()
                .checkSuccessMessage();

//        Assertions.assertEquals(reservation.getName(), infoPage.getName(), "Wrong Name in info block!");
//        Assertions.assertEquals(new BigDecimal(3600), infoPage.getPrice(), "Wrong price in info block!");
//        SeatsPage seatsPage = infoPage.book();
//        seatsPage.selectSeat(reservation.getSeat());
//        Assertions.assertEquals(reservation.getSeat(), seatsPage.getSeatNr(), "Wrong seat Nr!");
//        SuccessPage successPage = seatsPage.book();
//        Assertions.assertEquals("Thank You for flying with us!", successPage.getMessage(), "Can't find success message!");
    }

    @AfterEach
    public void closeBrowser() {
        baseFunc.closeBrowser();
    }

}
