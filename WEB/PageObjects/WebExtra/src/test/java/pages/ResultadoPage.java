package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultadoPage extends BasePage {
    public ResultadoPage(WebDriver navegador) {
        super(navegador);
    }

    public String lerPreco() {
        WebElement preco = navegador.findElements(By.xpath("//span[contains(.,'Por R$ ')]")).get(0);

        Pattern padraoPreco = Pattern.compile("\\d{1,3},\\d{2}"); // 3 numeros uma virgula e dois numeros
        Matcher matcher = padraoPreco.matcher(preco.getText());

        return matcher.find() ? matcher.group(0) : "Valor não encontrado";
    }

    public ProdutoPage clicarProduto(String produto) {
        WebElement primeiroItemDaBusca = navegador.findElements(By.xpath("//h3[contains(text(),'" + produto + "')]")).get(0);
        primeiroItemDaBusca.click();
        return new ProdutoPage(navegador);
    }
}
