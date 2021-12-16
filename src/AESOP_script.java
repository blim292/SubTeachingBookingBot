import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class AESOP_script {

	public static void main(String[] args) {
		
		
		String aesop = "https://www.aesoponline.com";
		UserInfo heidi = new UserInfo();
		commonFunctions func = new commonFunctions();
		
		SSLEmail msg = new SSLEmail();
		
		String projectLocation = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectLocation + "/lib/chromedriver/chromedriver");
		
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver,120);
		
		driver.get(aesop);
		
		
//		Automate login
		wait.until(ExpectedConditions.elementToBeClickable(By.id("Username")));
		driver.findElement(By.id("Username")).sendKeys(heidi.getUserName());
		driver.findElement(By.id("Password")).sendKeys(heidi.getPassword());

		driver.findElement(By.id("qa-button-login")).click();;
		
		boolean valid = true;
		
		while (valid) {
//			Randomize refresh time between 1-3 minutes.
			int randomNum = ThreadLocalRandom.current().nextInt(60000, 300000 + 1);

//			Evaluate each date for available sub spot.
			wait.until(ExpectedConditions.elementToBeClickable(By.className("k-link")));
			if (driver.findElement(By.id("months")) != null) {
				List<WebElement> allDates=driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//td"));
				
				for(WebElement ele:allDates)
				{
					String date = ele.getAttribute("class");
					
					if (date.contains("av")) {
						Matcher fullDate = Pattern.compile("\\d+").matcher(date);
						fullDate.find();
						String printFullDate = fullDate.group();
						func.printCurrentDateTime();
						System.out.println("Found: " + printFullDate);
						msg.sendEmail(printFullDate);
						System.out.println();
					}				
				}
			}
			else {
				System.out.println("Cannot process months. Something went wrong.");
			}
			
//			Wait random duration, then refresh page.
			func.wait(randomNum);
//			double printTime = randomNum/60000;
//			System.out.println("The time waited until refresh was " + printTime + " minutes.");
//			System.out.println();
			driver.navigate().refresh();
		}
	}
}
