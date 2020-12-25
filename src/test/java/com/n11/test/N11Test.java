package com.n11.test;


import com.n11.pageObjects.*;
import org.testng.Assert;
import org.testng.annotations.Test;



public class N11Test extends TestBase {

    HomePage homePage;
    LoginPage loginPage;
    SearchList searchList;
    FavouriteList favouriteList;
    Favourite favourite;

    @Test(testName ="Anasayfa Giriş Kontrolü",priority = 1)
    public void verifyHomePage() throws InterruptedException {

        homePage = new HomePage(driver);
        Assert.assertEquals(driver.getTitle(), "n11.com - Hayat Sana Gelir");
    }

    @Test(testName = "Kullanıcı Giriş Kontrolü",priority = 2)
    public void verifyLogin() throws InterruptedException {

        homePage.SignIn();
        loginPage = new LoginPage(driver);
        loginPage.setEmail("Buraya geçerli bir e-posta giriniz.");
        loginPage.setPassword("Buraya geçerli bir şifre giriniz.");
        loginPage.login();

    }

    @Test(testName = "Ürün Arama Kontrolü",priority = 3)
    public void verifySearchBar() throws InterruptedException {

        homePage.setSearch("samsung");
        searchList = new SearchList(driver);

        Assert.assertEquals(searchList.getResultText(), "bulundu.");
    }

    @Test(testName = "2.Sayfa Kontrolü",priority = 4)
    public void verifyPageTwo() throws InterruptedException {

        searchList.clickPage();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.n11.com/arama?q=samsung&pg=2");
    }

    @Test(testName = "Favori Ürün Ekleme Kontrolü",priority = 5)
    public void verifyFavouriteProduct() throws InterruptedException {

        searchList.clickFavourite();
        String expectedProductName = searchList.expectedProductName();
        homePage.FavouriteListMenu();
        favouriteList = new FavouriteList(driver);
        favouriteList.showDetailFavourite();
        favourite = new Favourite(driver);

        Assert.assertEquals(favourite.actualProductName(), expectedProductName);
    }

    @Test(testName = "Favori Ürün Silme Kontrolü",priority = 6)
    public void verifyDeleteProduct() throws InterruptedException {

        favourite.deleteFavourite();
        Assert.assertTrue(favourite.isFavouriteProduct());
    }
}

