package Pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePage extends BasePage {
    public HomePage(AndroidDriver android) {
        super(android);
    }

    KeyEvent keybboardEnter = new KeyEvent(AndroidKey.ENTER);
    WebElement campoProcura = android.findElement(By.id("com.novapontocom.extra:id/tv_home_search"));
    WebElement primeiroItemDaPesquisa;

    public HomePage procuraProduto(String produto) {
        campoProcura.click();
        WebElement campoProcuraTexto = android.findElement(By.id("com.novapontocom.extra:id/editTextSearchProducts"));
        campoProcuraTexto.sendKeys(produto);
        return this;
    }

    public HomePage clicarBotaoProcura() {
        android.pressKey(keybboardEnter);
        return this;
    }

    public HomePage validaBuscaProduto(String produto) {
        primeiroItemDaPesquisa = android.findElements(By.id("com.novapontocom.extra:id/textViewProductName")).get(0);
        String descricaoProduto = primeiroItemDaPesquisa.getText();
        assertTrue(descricaoProduto.contains(produto));
        return this;
    }

    public ProdutoPage clicarProduto() {
        primeiroItemDaPesquisa.click();
        return new ProdutoPage(android);
    }

    public String obtemValorDoProduto() {
        WebElement preco = android.findElements(By.id("com.novapontocom.extra:id/textViewProductPrice")).get(0);
        assertTrue(preco.isDisplayed());
        String valor = preco.getText();

        Pattern padraoPreco = Pattern.compile("\\d{1,3},\\d{2}"); // 3 numeros uma virgula e dois numeros
        Matcher matcher = padraoPreco.matcher(valor);

        return matcher.find() ? matcher.group(0) : "Valor não encontrado";
    }


}
