package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoPage extends BasePage {

    public ProdutoPage(WebDriver navegador) {
        super(navegador);
    }

    public ProdutoPage verificarProduto(String produto, String valor) {
        WebElement nomeProduto = navegador.findElement(By.xpath("//h1[contains(text(),'" + produto + "')]"));
        WebElement precoProduto = navegador.findElement(By.xpath("//span[@id='product-price'][contains(text(), '" + valor + "')]"));

        assertTrue(nomeProduto.isDisplayed());
        assertTrue(precoProduto.isDisplayed());
        return this;
    }

    public CarrinhoPage clicarBotaoComprar() {
        WebElement botaoComprar = navegador.findElement(By.xpath("//button[contains(.,'Comprar')]"));
        botaoComprar.click();
        return new CarrinhoPage(navegador);
    }


}
