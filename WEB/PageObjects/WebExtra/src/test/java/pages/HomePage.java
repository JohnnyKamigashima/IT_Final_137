package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver navegador) {
        super(navegador);
    }

    By procura = By.xpath("//input[@id='strBusca']");
    By botaoLupa = By.xpath("//button[@id='btnOK']");

    public HomePage visitaHomePage(String baseUrl) {
        navegador.get(baseUrl);
        return this;
    }

    public HomePage pesquisaProduto(String produto) {
        navegador.findElement(procura).sendKeys(produto);
        return this;
    }

    public ResultadoPage clicarBotaoLupa() {
        navegador.findElement(botaoLupa).click();
        return new ResultadoPage(navegador);
    }

}
