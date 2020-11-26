package selenium.driver;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class StepDefinition {

	public WebDriver driver;
	static By firstName = By.id("firstname-384ed391-59a1-4016-bc91-62bb1307edb2_6770");
	static By lastName = By.id("lastname-384ed391-59a1-4016-bc91-62bb1307edb2_6770");
	static By mail = By.id("email-384ed391-59a1-4016-bc91-62bb1307edb2_6770");
	static By companySize = By.id("company_size__c-384ed391-59a1-4016-bc91-62bb1307edb2_6770");
	static By country = By.id("country-384ed391-59a1-4016-bc91-62bb1307edb2_6770");
	static By cookies_accept = By.id("ccc-notify-accept");
	WebDriverWait wait;

	@Before
	public void hooks() {
		if (System.getProperty("browser").equalsIgnoreCase("chrome")) {
			driver = WebDriverUtility.getWebDriver(Browser.CHROME);
		} else if (System.getProperty("browser").equalsIgnoreCase("firefox")) {
			driver = WebDriverUtility.getWebDriver(Browser.FIREFOX);
		}

	}

	@Given("^The app is displayed$")
	public void OpenTheApp() throws IOException, InterruptedException {
		driver.get("https://www.google.com");
		wait = new WebDriverWait(driver, 30);
		driver.switchTo().frame(0);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("introAgreeButton")));
		driver.findElement(By.id("introAgreeButton")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				(By.cssSelector("input[class='gLFyf gsfi']"))));
		driver.findElement(By.cssSelector("input[class='gLFyf gsfi']")).sendKeys("aodocs.com");
		driver.findElement(By.cssSelector("input[class='gLFyf gsfi']")).sendKeys(Keys.ENTER);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				(By.xpath("//a[@href ='https://www.aodocs.com/']"))));
	
		driver.findElement(By.xpath("//a[@href ='https://www.aodocs.com/']")).click();
		driver.switchTo().defaultContent();

	}

	@Then("^Accept the cookies$")
	public void Accept_the_cookies() throws Throwable {
		wait.until(ExpectedConditions.visibilityOfElementLocated(cookies_accept));
		Actions actionProvider = new Actions(driver);
		actionProvider.moveToElement(driver.findElement(cookies_accept)).click().perform();
	}

	@Then("^Click on '(.*)' button$")
	public void Click_buttonByText(String button) throws Throwable {
		WebElement element = driver.findElement(By.linkText(button));
		wait.until(ExpectedConditions.visibilityOf((element)));
		Actions actionProvider = new Actions(driver);
		actionProvider.moveToElement(element).click().perform();
		wait.until(ExpectedConditions.invisibilityOf((element)));

	}


	@Then("^Fill the '(.*)' field with the value '(.*)'$")
	public void Set_fiels(String field, String text) throws Throwable {
		By element = null;
		if(field.equalsIgnoreCase("first name"))
			element=firstName;
		else if(field.equalsIgnoreCase("last name")) 
			element=lastName;
		else if(field.equalsIgnoreCase("email")&& text.equals("random")  ) 
		{element=mail;
	
	  text=getRandomString().concat("@gmail.com");

		}
		else throw new Exception("The field ".concat(field).concat(" doesn't exist, please verify your entry"));
	
		wait.until(ExpectedConditions.visibilityOf((driver.findElement(element))));
		driver.findElement(element).sendKeys(text);
}
	@Then("^Choose a value in '(.*)'$")
	public void ChooseValue(String value) throws Exception {
		if(value.equalsIgnoreCase("company size"))
		{	wait.until(ExpectedConditions.visibilityOf((driver.findElement(companySize))));
		Select companySizeDropdown = new Select(driver.findElement(companySize));
		companySizeDropdown.selectByIndex(2);
		}
		else throw new Exception("The field ".concat(value).concat(" doesn't exist, please verify your entry"));

	}
	
	
	@Then("^Click on '(.*)' button and verify the error messages$")
	public void VerifErrorMessages(String button) throws Exception {
		By submitButton =By.cssSelector("input[value='"+button+"']");
		driver.findElement(submitButton).click();
		wait.until(ExpectedConditions.visibilityOf((driver.findElement(submitButton))));
		Assert.assertTrue(driver.getPageSource().contains("Please"));
		wait.until(ExpectedConditions.visibilityOf((driver.findElement(By.cssSelector("label[class=hs-error-msg]")))));
		List<WebElement> elements=driver.findElements(By.cssSelector("label[class=hs-error-msg]"));
		System.out.println("++++++++++++++++++"+elements.size());
		for  (WebElement webElement : elements) {
			String attribute=webElement.getAttribute("data-reactid");
				
			if (attribute.contains(("last")))
			{
				System.out.println("last name error!!");
				throw new Exception("Please complete the last name field");
			}
			else if (attribute.contains(("first")))
			{
				System.out.println("first name error!!");
				throw new Exception("Please complete the first name field");
			}
			else if (attribute.contains(("country")))
			if (attribute.contains(("country")))
			{
				System.out.println("country error!!");
				throw new Exception("Please complete the country field");
			}
			
				break;
			}
		
	
		

	}
	
	@After()
	public void closeBrowser() {
		WebDriverUtility.closeWebDriver(driver);
	}

	public static String getRandomString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(8);
		return (generatedstring);

	}
}
