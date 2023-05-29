package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultadoPage extends BasePage {
    public ResultadoPage(WebDriver navegador) {
        super(navegador);
    }

    By preco = By.xpath("//span[contains(.,'Por R$ ')]");

    public String lerPreco() {

        Pattern padraoPreco = Pattern.compile("\\d{1,3},\\d{2}");
        Matcher matcher = padraoPreco.matcher(navegador.findElements(preco).get(0).getText());

        return matcher.find() ? matcher.group(0) : "Valor não encontrado";
    }

    public ProdutoPage clicarProduto(String produto) {
        By primeiroItemDaBusca = By.xpath("//h3[contains(text(),'" + produto + "')]");
        navegador.findElements(primeiroItemDaBusca).get(0).click();
        return new ProdutoPage(navegador);
    }
}
