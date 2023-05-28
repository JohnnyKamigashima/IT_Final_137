package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    public HomePage(WebDriver navegador) {
        super(navegador);
    }

    public HomePage visitaHomePage(String baseUrl) {
        navegador.get(baseUrl);
        return this;
    }

    public HomePage pesquisaProduto(String produto) {

        WebElement procura = navegador.findElement(By.xpath("//input[@id='strBusca']"));
        procura.sendKeys(produto);
        return this;
    }

    public ResultadoPage clicarBotaoLupa() {
        WebElement botaoLupa = navegador.findElement(By.xpath("//button[@id='btnOK']"));
        botaoLupa.click();
        return new ResultadoPage(navegador);
    }

}
