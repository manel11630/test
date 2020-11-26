package selenium.driver;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(

		features = { "features" }, plugin = { "pretty",
				"json:target/Cucumber-Json/cucumber.json" },glue= {"selenium/driver"})

public class Runner {

}
