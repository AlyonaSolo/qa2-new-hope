package pageobject.tickets.pages;

import org.openqa.selenium.By;

public class SuccessPage {
    private final By SUCCESS_TXT = By.xpath(".//div[@class = 'finalTxt']");
    private BaseFunc baseFunc;

    public SuccessPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public String getMessage() {
        return baseFunc.getText(SUCCESS_TXT);
    }
}
