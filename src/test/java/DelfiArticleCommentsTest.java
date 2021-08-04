import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DelfiArticleCommentsTest {
    private final By ACCEPT_COOKIE_BTN = By.xpath(".//button[@mode='primary']");
    private final By HOME_PAGE_TITLE = By.xpath(".//h1[contains(@class, 'headline__title')]");
    private final By HOME_PAGE_ARTICLE = By.tagName("article");
    private final By HOME_PAGE_COMMENTS = By.xpath(".//a[contains(@class,'comment-count')]");

    private final By CLOSE_ADVERTISING = By.xpath(".//div[@id='Close']");

    private final By ARTICLE_PAGE_TITLE = By.xpath(".//h1[contains(@class,'text-size-md-30')]");
    private final By ARTICLE_PAGE_COMMENTS = By.xpath(".//a[contains(@class,'text-size-md-28')]"); //move to Article page class

    private final By COMMENTS_PAGE_TITLE = By.xpath(".//h1[@class='article-title']");
    private final By COMMENTS_PAGE_COMMENTS = By.xpath(".//span[@class='type-cnt']");

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private WebDriver driver;

    @Test
    public void titleAndCommentCountCheck() {
        LOGGER.info("This test is cheking titles and comments count on home/article/comments page");

        LOGGER.info("Setting driver location");
        System.setProperty("webdriver.chrome.driver", "C://Users/User/IdeaProjects/chromedriver.exe");

        LOGGER.info("Opening browser window");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        LOGGER.info("Opening Home Page");
        driver.get("http://delfi.lv");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        LOGGER.info("waiting for accept cookies");
        wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_COOKIE_BTN));

        LOGGER.info("Accepting cookies");
        driver.findElement(ACCEPT_COOKIE_BTN).click();

        List<WebElement> titles = driver.findElements(HOME_PAGE_TITLE);

        //---------------------FOR------------------------------------------------------
        for (int i = 0; i < titles.size(); i++) {  //i =i+1  i++
            if (!titles.get(i).getText().isEmpty()) {   //!true = false !false=true
                System.out.println(i + ": " + titles.get(i).getText());
            }
        }
        //---------------------FOREACH------------------------------------------------------
        for (WebElement we : titles) {
//            if (!we.getText().isEmpty()){
//                System.out.println(we.getText());
//            }else{
//                System.out.println("----------------------------!--------------------");
//            }

            // условия ? true : false (else)
            System.out.println(we.getText().isEmpty() ? "---------------" : we.getText());

        }
    }

    @Test
    public void titleAndCommentCount() {
        System.setProperty("webdriver.chrome.driver", "C://Users/User/IdeaProjects/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://delfi.lv");

        //-----------------------HOME PAGE________________________________________________
        WebDriverWait wait = new WebDriverWait(driver, 10);
        LOGGER.info("Waiting for accept cookies button");
        wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_COOKIE_BTN));

        LOGGER.info("Accepting cookies");
        driver.findElement(ACCEPT_COOKIE_BTN).click();

        LOGGER.info("Closing advertising .");
        wait.until(ExpectedConditions.elementToBeClickable(CLOSE_ADVERTISING));
        driver.findElement(CLOSE_ADVERTISING).click();

        List<WebElement> articles = driver.findElements(HOME_PAGE_ARTICLE);
        WebElement article = articles.get(4);

        LOGGER.info("Getting article title and comments count");
        String homePageTitle = article.findElement(HOME_PAGE_TITLE).getText();
        int homePageCommentsCount = getCommentsCount(article, HOME_PAGE_COMMENTS);
        LOGGER.info("Title is: " + homePageTitle + " and comments count is: " + homePageCommentsCount);
        //        int homePageCommentsCount = 0;
//        if (!article.findElements(HOME_PAGE_COMMENTS).isEmpty()) {
//            String commentsCount = article.findElement(HOME_PAGE_COMMENTS).getText(); //(36) <- String
//             commentsCount = commentsCount.substring(1, commentsCount.length() -1); //36 <- String
//           homePageCommentsCount = Integer.parseInt(commentsCount);  //String -> int
//        }

        LOGGER.info("Opening article");
        article.findElement(HOME_PAGE_TITLE).click();

        //-------------------------ARTICLE PAGE----------------------------------------------------------
        String articlePageTitle = driver.findElement(ARTICLE_PAGE_TITLE).getText();
        int articlePageCommentsCount = getCommentsCount(ARTICLE_PAGE_COMMENTS);
//        int articlePageCommentsCount = 0;
//        if (!driver.findElements(ARTICLE_PAGE_COMMENTS).isEmpty()) {
//            String commentsCount = driver.findElement(HOME_PAGE_COMMENTS).getText(); //(36) <- String
//          commentsCount = commentsCount.substring(1, commentsCount.length() -1); //36 <- String
//          articlePageCommentsCount = Integer.parseInt(commentsCount);  //String -> int
//        }
        Assertions.assertEquals(homePageTitle, articlePageTitle, "Wrong title!");
        Assertions.assertEquals(homePageCommentsCount, articlePageCommentsCount, "Wrong comments count!");

        //-------------------------COMMENTS PAGE---------------------------------------------------------

        String commentsPageTitle = driver.findElement(COMMENTS_PAGE_TITLE).getText();

        List<WebElement> commentsCount = driver.findElements(COMMENTS_PAGE_COMMENTS);
        WebElement anonimCommentsCount = commentsCount.get(0);
        WebElement registratedCommentsCount = commentsCount.get(1);

        int commentsPageRegistratedCommentsCount = getCommentsCount(anonimCommentsCount, COMMENTS_PAGE_COMMENTS);
        int commentsPageAnonimCommentsCount = getCommentsCount(registratedCommentsCount, COMMENTS_PAGE_COMMENTS);

        Assertions.assertEquals(homePageTitle, commentsPageTitle, "Wrong title!");
        Assertions.assertEquals(homePageCommentsCount, commentsPageAnonimCommentsCount + commentsPageRegistratedCommentsCount, "Wrong comment count!");
//
//        List<WebElement> commentsPageCommentsCount = driver.findElements(COMMENTS_PAGE_COMMENTS);
//        WebElement commentsPageCommentsAnonimous = commentsPageCommentsCount.get(0);
//        int commentsPageCommentsAnonimous = getCommentsCount(COMMENTS_PAGE_COMMENTS);
//
////        if (!commentsPageCommentsAnonimous.findElement(COMMENTS_PAGE_COMMENTS).isEmpty()) {
////            String commentsCountAnonimous =driver.findElement(COMMENTS_PAGE_COMMENTS).getText();
////            commentsCountAnonimous = commentsCountAnonimous.substring(1, commentsCountAnonimous.length()-1);
////            commentsPageCommentsAnonimous= Integer.parseInt(commentsCountAnonimous);
////
////        }
//        WebElement commentsPageCommentsRegistrated = commentsPageCommentsCount.get(1);
//        int commentsPageCommentsRegistrated = getCommentsCount(COMMENTS_PAGE_COMMENTS);
////        int comments = commentsAnonimous + commentsRegistrated ;
////        int commentsAnonimus = 0;
////        if (!comments.findElements(COMMENTS_PAGE_COMMENTS).isEmpty()) {
////            String commentsAnonimus =driver.findElement()
////
////        }
//
//
//
//        //...
    }

    private int getCommentsCount(By locator) {
        int commentsCount = 0;
        if (!driver.findElements(locator).isEmpty()) {
            commentsCount = removeBrackets(driver.findElement(locator));
        }
        return commentsCount;
    }

    private int getCommentsCount(WebElement we, By locator) {
        int commentsCount = 0;
        if (!we.findElements(locator).isEmpty()) {
            commentsCount = removeBrackets(we.findElement(locator));
        }
        return commentsCount;
    }

    private int removeBrackets(WebElement we) {
        String commentsCountText = we.getText();
        commentsCountText = commentsCountText.substring(1, commentsCountText.length() - 1); // (36) -> 36 (String)
        return Integer.parseInt(commentsCountText); // 36 (String) -> 36 (int)
    }

    @AfterEach
    public void closeBrowser() {
        driver.close();

    }
}
