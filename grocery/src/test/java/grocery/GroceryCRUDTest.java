package grocery;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class GroceryCRUDTest {

	private WebDriver driver;

	@Before
	public void setup() {
		// Configuração do driver do Chrome
		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void testGroceryCRUD() throws InterruptedException {
		// Acessa a página
		driver.get("https://www.grocerycrud.com/v1.x/demo/my_boss_is_in_a_hurry/bootstrap");

		// Seleciona a versão Bootstrap V4 Theme
		WebElement selectElement = driver.findElement(By.id("switch-version-select"));
		Select select = new Select(selectElement);
		select.selectByVisibleText("Bootstrap V4 Theme");

		// Clica no botão Add Customer
		driver.findElement(By.cssSelector("a[href*='add']")).click();

		// Preenche os campos do formulário
		driver.findElement(By.id("field-customerName")).sendKeys("Teste Sicredi");
		driver.findElement(By.id("field-contactLastName")).sendKeys("Teste");
		driver.findElement(By.id("field-contactFirstName")).sendKeys("Seu Nome");
		driver.findElement(By.id("field-phone")).sendKeys("51 9999-9999");
		driver.findElement(By.id("field-addressLine1")).sendKeys("Av Assis Brasil, 3970");
		driver.findElement(By.id("field-addressLine2")).sendKeys("Torre D");
		driver.findElement(By.id("field-city")).sendKeys("Porto Alegre");
		driver.findElement(By.id("field-state")).sendKeys("RS");
		driver.findElement(By.id("field-postalCode")).sendKeys("91000-000");
		driver.findElement(By.id("field-country")).sendKeys("Brasil");
		driver.findElement(By.id("field-salesRepEmployeeNumber")).sendKeys("5");
		driver.findElement(By.id("field-creditLimit")).sendKeys("200");

		// Clica no botão Save
		driver.findElement(By.id("form-button-save")).click();
		Thread.sleep(5000); // Espera 5 segundo para a página carregar
		// validar mensagem
		String mensagem = driver.findElement(By.id("report-success")).getText();
		Assert.assertEquals("Your data has been successfully stored into the database. Edit Record or Go back to list",
				mensagem);

		driver.findElement(By.linkText("Go back to list")).click();

		// Encontra o campo pelo nome e preenche com o valor desejado
		WebElement campo = driver.findElement(By.name("customerName"));
		campo.sendKeys("Teste Sicredi");
		Thread.sleep(5000); // Espera 5 segundo para a página carregar
		/// Localiza e marca o checkbox
		WebElement checkbox = driver.findElement(By.cssSelector(".select-all-none"));
		checkbox.click();

		WebElement botao = driver.findElement(By.className("delete-selected-button"));
		botao.click();
		// validar mensagem de excluir
		String expectedMessage = "\"Are you sure that you want to delete this 1 item?\"";
		String actualMessage = "<p class=\"alert-delete-multiple-one\">\"Are you sure that you want to delete this 1 item?\"</p>";

		assertEquals(expectedMessage, actualMessage.replaceAll("<[^>]*>", ""));

		// Confirma a exclusão
		WebElement confirmButton = driver.findElement(By.cssSelector(".delete-multiple-confirmation-button"));
		confirmButton.click();

		Thread.sleep(5000); // Espera 5 segundo para carrregar

		// validar mensagem de sucesso
		WebElement successMessage = driver.findElement(By.cssSelector("span[data-growl='message'] p"));
		String actualSuccessMessage = successMessage.getText();

		String expectedSuccessMessage = "Your data has been successfully deleted from the database.";
		assertEquals(expectedSuccessMessage, actualSuccessMessage);
	}

	@After
	public void tearDown() {
		// fechar pagina web
		driver.quit();

	}

}
