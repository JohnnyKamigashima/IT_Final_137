package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoPage extends BasePage {

    public ProdutoPage(WebDriver navegador) {
        super(navegador);
    }

    By botaoComprar = By.xpath("//button[contains(.,'Comprar')]");

    public ProdutoPage verificarProduto(String produto, String valor) {
        By nomeProduto = By.xpath("//h1[contains(text(),'" + produto + "')]");
        By precoProduto = By.xpath("//span[@id='product-price'][contains(text(), '" + valor + "')]");

        assertTrue(navegador.findElement(nomeProduto).isDisplayed());
        assertTrue(navegador.findElement(precoProduto).isDisplayed());
        return this;
    }

    public CarrinhoPage clicarBotaoComprar() {

        navegador.findElement(botaoComprar).click();
        return new CarrinhoPage(navegador);
    }
}
