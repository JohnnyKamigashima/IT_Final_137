package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarrinhoPage extends BasePage {

    By botaoContinuar = By.xpath("//button[contains(.,'Continuar a compra')]");
    public CarrinhoPage(WebDriver navegador) {
        super(navegador);
    }

    public CarrinhoPage verificarProdutoCarrinho(String produto, String valor) {
        By carrinhoNomeProduto = By.xpath("//a[@data-id='link'][contains(.,'" + produto + "')]");
        By carrinhoPrecoProduto = By.xpath("//strong[contains(.,'" + valor + "')]");

        assertTrue(navegador.findElement(carrinhoNomeProduto).isDisplayed());
        assertTrue(navegador.findElement(carrinhoPrecoProduto).isDisplayed());
        return this;
    }

    public CarrinhoPage clicarBotaoContinuar() {
        navegador.findElement(botaoContinuar).click();
        return this;
    }
}
