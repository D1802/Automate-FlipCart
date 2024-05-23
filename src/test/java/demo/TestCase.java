package demo;


import org.testng.annotations.*;

import com.google.j2objc.annotations.Weak;

import dev.failsafe.internal.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.bouncycastle.util.Integers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.internal.WebElementToJsonConverter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCase {

    ChromeDriver driver;


    @BeforeSuite
    public void createDriver(){
        System.out.println("Create a Driver");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();  // Use the class-level driver variable
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
    
    }

    /*
    Navigate to URL: Flipcart
     *Search the text in Search Bar : Washing Machine
     *sort the itms by Click on the popularity option
     *wait fot the Page to load 
     * get the text of the list of items have rating less than equal to 4

     */

    @Test(enabled = true ,description =" Search “Washing Machine”. Sort by popularity and print the count of items with rating less than or equal to 4 stars.")
    public void TestCase01() throws InterruptedException{
        System.out.println("TestCase started");

        try {
        int count=0;
        JavascriptExecutor js = (JavascriptExecutor) driver;  // Java Script Executor required to Scroll down for
        WebDriverWait wait = new  WebDriverWait(driver,java.time.Duration.ofSeconds(30));
      
        SeleniumWrapper.navigateTo(driver, "https://www.flipkart.com/");

        //Search the Box text in  the search Box
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title,'Search')]"));
        SeleniumWrapper.enter_text(searchBox,"Washing machine");
        
        // Go to the Page after clking on the search icon 
        WebElement enterSearch = driver.findElement(By.xpath("//button[contains(@title,'Search')]"));
        SeleniumWrapper.button_clikeble(driver,enterSearch);

        // Sort by the Popularity for Clicking to element 
        WebElement popularity = driver.findElement(By.xpath("//div[text()='Popularity']"));
        SeleniumWrapper.button_clikeble(driver, popularity);

        // wait for the Last Element of the Page to Loade
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/span[text()='Advertise']")));

       // Get the list of ratings on the page
       List<WebElement> ratings = driver.findElements(By.xpath("//span/div[@class='XQDdHH']"));
       for (int i = 0; i < ratings.size(); i++) { // 
           try {
               WebElement ratingElement = ratings.get(i); // Get the webelemtnt using index
               String ratingText = ratingElement.getText(); // get the Text of rating element using ratingText
               if (!ratingText.isEmpty()) { // Cheak for the Text is not empty
                   float ratingValue = Float.parseFloat(ratingText); // convet stinbg to float
                   if (ratingValue <= 4 && ratingValue > 0) { // count the rating value is less than equal to 4
                       count++;
                   }
               }
           } catch (Exception e) {
               // Re-locate the element if it becomes staleelement Exception and count the elements
               ratings = driver.findElements(By.xpath("//span/div[@class='XQDdHH']")); // 
               WebElement ratingElement = ratings.get(i);
               String ratingText = ratingElement.getText();
               if (!ratingText.isEmpty()) {
                   float ratingValue = Float.parseFloat(ratingText);
                   if (ratingValue <= 4 && ratingValue > 0) {
                       count++;
                   }
               }
           }
       }
        System.out.println("Count items Rating less than equal to 4 stars: "+ count);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
       
    }

    /*
     * Naviaget to url 
     * search iphone in seach bar 
     * get the list of iphone having discount more  than 17%
     * print the list of iphone names along with discount %
     */

    @Test(enabled = true,description="Search “iPhone”, print the Titles and discount % of items with more than 17% discount")
    public void TestCase02(){
        try {
        
            SeleniumWrapper.navigateTo(driver, "https://www.flipkart.com/");
            // below code ensure about the LoginWindow is appear On the Screen 
            // if(driver.findElement(By.xpath("//div[@class='JFPqaw']")).isDisplayed())
            //     driver.findElement(By.xpath("//div[@class='JFPqaw']/span")).click();

            WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title,'Search')]"));
            SeleniumWrapper.enter_text(searchBox, "iPhone"); // seache "iphone"

            // Go to the Page after clking on the search icon 
            WebElement enterSearch = driver.findElement(By.xpath("//button[contains(@title,'Search')]"));
            SeleniumWrapper.button_clikeble(driver,enterSearch);

            // Get the WebElement of percentag and title of the Iphione 
            List<WebElement> percentages = driver.findElements(By.xpath("//span[contains(text(),'%')]"));
            List<Integer> perctInt = new ArrayList<Integer>(); 
            List<WebElement> title = driver.findElements(By.xpath("//div[@class='KzDlHZ']"));

            for (WebElement web : percentages) {
                perctInt.add(Integer.parseInt(web.getText().replace("% off",""))); // convert the string to integer 
            }
            
            for(int i=0; i<title.size();i++){ // Count the percentag
                
                if(perctInt.get(i)>17 ) // Discount should be more than 17%
                    System.out.println("Title: "+title.get(i).getText()+" Discount: "+perctInt.get(i)+"%");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    /*
     * Navigate to URL
     * search coffe mug on the seach bar
     * get the list of reviews coorsponding to its name,
     * sort the list of reviews such that top 5 highest review should be printed on the screen
     */
    @Test(enabled = true, description = "Search “Coffee Mug”, select 4 stars and above, and print the Title and image URL of the 5 items with highest number of reviews")
    public void TestCase03() {
        try {
            int count = 0;
          
            SeleniumWrapper.navigateTo(driver, "https://www.flipkart.com/");
            if(driver.findElement(By.xpath("//div[@class='JFPqaw']")).isDisplayed())
                driver.findElement(By.xpath("//div[@class='JFPqaw']/span")).click();

    
            WebElement searchBox = driver.findElement(By.xpath("//input[contains(@title,'Search')]"));             
            SeleniumWrapper.enter_text(searchBox,"Coffe Mug");

            // Go to the Page after clking on the search icon 
            WebElement enterSearch = driver.findElement(By.xpath("//button[contains(@title,'Search')]"));
            SeleniumWrapper.button_clikeble(driver,enterSearch);

            //get the list of review with title
            List<WebElement> review = driver.findElements(By.xpath("//span[contains(@id,'productRating')]/../span[2]"));
            List<WebElement> titleName = driver.findElements(By.xpath("//a[@class='wjcEIp']")); // corrected class name
            
          
            //Tree map used to sort the element in reverse order cooresonding to review 
            TreeMap<Integer, String> treeMap = new TreeMap<>(Collections.reverseOrder());
    
            for (int i = 0; i < review.size(); i++) {
                // replcae the string such as (1,546) to 1546 in integer
                String rv = review.get(i).getText().replace("(", "").replace(")", "");
    
                if (rv.contains(",")) {
                    // add the review 
                    treeMap.put(Integer.parseInt(rv.replace(",", "").trim()), titleName.get(i).getText()); 
                } else {
                    treeMap.put(Integer.parseInt(rv.trim()), titleName.get(i).getText());
                }
            }

            // Cheak the TreeMap is Empty or not
            Assert.isTrue(treeMap.size()!=0, "Item is NOT present"); 

            for (Integer i : treeMap.keySet()) {
                // print the first 5 highest review
                System.out.println("Title: " + treeMap.get(i) + " Review: " + i);
                count++;
                if (count == 5) {
                    break;
                }
            }
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Close the Suite 
    @AfterSuite(enabled = true)
    public void closeDriver(){
        System.out.println("Close Driver");
        if (driver != null) {
            driver.quit();
        }
    }
}
