package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarrinhoPage extends BasePage {
    public CarrinhoPage(WebDriver navegador) {
        super(navegador);
    }

    public CarrinhoPage verificarProdutoCarrinho(String produto, String valor) {
        WebElement carrinhoNomeProduto = navegador.findElement(By.xpath("//a[@data-id='link'][contains(.,'" + produto + "')]"));
        WebElement carrinhoPrecoProduto = navegador.findElement(By.xpath("//strong[contains(.,'" + valor + "')]"));

        assertTrue(carrinhoNomeProduto.isDisplayed());
        assertTrue(carrinhoPrecoProduto.isDisplayed());
        return this;
    }

    public CarrinhoPage clicarBotaoContinuar() {
        WebElement botaoContinuar = navegador.findElement(By.xpath("//button[contains(.,'Continuar a compra')]"));
        botaoContinuar.click();
        return this;
    }
}
