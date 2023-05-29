package Pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoPage extends BasePage {
    public ProdutoPage(AndroidDriver android) {
        super(android);
    }

    public ProdutoPage validaNomeProduto(String nome) {
        String nomeProduto = android.findElement(By.id("com.novapontocom.extra:id/textViewProductName")).getText();
        assertTrue(nomeProduto.contains(nome));
        return this;
    }

    public ProdutoPage validaValorProduto(String valor) {
        String valorProduto = android.findElement(By.id("com.novapontocom.extra:id/text_view_product_price")).getText();
        assertTrue(valorProduto.contains(valor));
        return this;
    }

    public CarrinhoPage clicarContinuarCompra() {
        WebElement botaoContinuar = android.findElement(By.id("com.novapontocom.extra:id/button"));
        botaoContinuar.click();
        return new CarrinhoPage(android);
    }
}
