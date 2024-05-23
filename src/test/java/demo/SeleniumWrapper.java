package demo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWrapper {

       public static void button_clikeble(ChromeDriver driver, WebElement elmentToClick){

        try {
            if(elmentToClick!=null && elmentToClick.isDisplayed()){
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.visibilityOf(elmentToClick));
                elmentToClick.click();
            }

            else
                System.out.println("Element is NOT Displayed");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception: "+e.getMessage());
        }

    }


    public static void enter_text(WebElement inputBox, String keysToSend){
        try {
            if (inputBox!=null && inputBox.isDisplayed()) {
                inputBox.sendKeys(keysToSend);  
            }else
                System.out.println("Element is Not Displayed for key: "+keysToSend);
                  
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception: "+e.getMessage());
        }
    }

    public static void navigateTo(ChromeDriver driver,String url){
        try {
            if(!driver.getCurrentUrl().equals(url))
                driver.get(url);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exceptions: "+e.getMessage());
        }
    }
    
}
