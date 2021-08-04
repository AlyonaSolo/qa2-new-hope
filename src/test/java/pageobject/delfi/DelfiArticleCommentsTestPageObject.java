package pageobject.delfi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageobject.delfi.pages.ArticlePage;
import pageobject.delfi.pages.CommentsPage;
import pageobject.delfi.pages.HomePage;
import pageobject.tickets.pages.BaseFunc;

public class DelfiArticleCommentsTestPageObject {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final int ARTICLE_ID = 1;
    private final int REGISTRATED_COMMENTS_ID = 0;
    private final int ANONIMUS_COMMENTS_ID = 1;

    private BaseFunc baseFunc;

    @Test
    public void titleAndCommentCount() {
//        System.setProperty("webdriver.chrome.driver", "C://Users/User/IdeaProjects/chromedriver.exe");
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
        baseFunc = new BaseFunc();

//        driver.get("http://delfi.lv");
        baseFunc.openPage("delfi.lv");

        //-----------------------HOME PAGE________________________________________________
        HomePage homePage = new HomePage(baseFunc);
        homePage.acceptCookies();
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_COOKIE_BTN)); //move to base func

//        driver.findElement(ACCEPT_COOKIE_BTN).click();

        homePage.getArticleById(ARTICLE_ID);
        //        List<WebElement> articles = driver.findElements(HOME_PAGE_ARTICLE);
//        WebElement article = articles.get(4);

        String homePageTitle = homePage.getTitle(ARTICLE_ID);
        int homePageCommentCount = homePage.getCommentsCount(ARTICLE_ID);
        //        String homePageTitle = article.findElement(HOME_PAGE_TITLE).getText();
//        int homePageCommentCount = getCommentsCount(article, HOME_PAGE_COMMENTS);

        ArticlePage articlePage = homePage.openArticle(ARTICLE_ID);
//        article.findElement(HOME_PAGE_TITLE).click();

        // -------------------------------------ARTICLE PAGE-----------------------------
//        ArticlePage articlePage = new ArticlePage(baseFunc);

        String articlePageTitle = articlePage.getTitle();
        int articlePageCommentsCount = articlePage.getCommentsCount();
//        String articlePageTitle = driver.findElement(ARTICLE_PAGE_TITLE).getText();
//        int articlePageCommentsCount = getCommentsCount(ARTICLE_PAGE_COMMENTS);

        Assertions.assertEquals(homePageTitle, articlePageTitle, "Wrong title!");
        Assertions.assertEquals(homePageCommentCount, articlePageCommentsCount, "Wrong comment count!");
//
        articlePage.openCommentsPage();
//       driver.findElement(ARTICLE_PAGE_COMMENTS).click();
        //------------------------COMMENTS PAGE-------------------------------------------------
        CommentsPage commentsPage = new CommentsPage(baseFunc);

        String commentsPageTitle = commentsPage.getTitle();

        commentsPage.getCommentsById(REGISTRATED_COMMENTS_ID);
        commentsPage.getCommentsById(ANONIMUS_COMMENTS_ID);

        int commentsPageRegistratedCommentsCount = commentsPage.getCommentsCount(REGISTRATED_COMMENTS_ID);
        int commentsPageAnonimCommentsCount = commentsPage.getCommentsCount(ANONIMUS_COMMENTS_ID);

        Assertions.assertEquals(homePageTitle, commentsPageTitle, "Wrong title!");
        Assertions.assertEquals(homePageCommentCount, commentsPageAnonimCommentsCount + commentsPageRegistratedCommentsCount, "Wrong comment count!");


    }

    @AfterEach
    public void CloseBrowser() {
        baseFunc.closeBrowser();
    }
}
