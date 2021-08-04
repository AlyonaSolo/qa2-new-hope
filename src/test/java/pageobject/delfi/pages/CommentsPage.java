package pageobject.delfi.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobject.tickets.pages.BaseFunc;

import java.util.List;

public class CommentsPage {
    private final By TITLE = By.xpath(".//h1[@class='article-title']");
    private final By COMMENTS = By.xpath(".//span[@class='type-cnt']");


    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private BaseFunc baseFunc;

    public CommentsPage(BaseFunc baseFunc) {
        this.baseFunc = baseFunc;
    }

    public String getTitle() {
        LOGGER.info("Getting article title");
        return baseFunc.getText(TITLE);
    }

    public WebElement getCommentsById(int id) {
        LOGGER.info("Getting comments count Nr. " + (id + 1));
        List<WebElement> comments = baseFunc.findElements(COMMENTS);
        Assertions.assertFalse(comments.isEmpty(), "There are no comments");
        Assertions.assertTrue(comments.size() > id, "Comments mount is less than id");
        return comments.get(id);
    }

    public int getCommentsCount(int id) {
        if (baseFunc.findElements(getCommentsById(id), COMMENTS).isEmpty()) {
            return 0;
        } else {
            String commentsCountToParse = baseFunc.getText(getCommentsById(id), COMMENTS);
            commentsCountToParse = commentsCountToParse.substring(1, commentsCountToParse.length() - 1);
            return Integer.parseInt(commentsCountToParse);
        }

    }
}

