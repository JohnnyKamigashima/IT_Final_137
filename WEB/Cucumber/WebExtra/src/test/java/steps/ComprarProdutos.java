package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComprarProdutos {
    static ChromeDriver navegador;
    static String chromeDriverPath = "/opt/homebrew/bin/chromedriver";//Configura caminho do chromedriver
    String produto;
    String valor;

    @Before
    public static void setUp() {

        // Configura o Chrome Driver
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //#Adding argument to disable the AutomationControlled flag
        options.addArguments("--disable-blink-features=AutomationControlled");

        navegador = new ChromeDriver(options);

        //Maximiza a janela do navegador
        navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        navegador.manage().window().maximize();
    }

    @After
    public static void tearDown() throws InterruptedException {
        navegador.quit();
    }


    @Dado("que eu esteja no site do {string}")
        public void que_eu_esteja_no_site_do(String baseUrl) {
            navegador.get(baseUrl);
        }

    @Quando("procurar pelo {string}")
    public void procurar_pelo(String produto) {
            WebElement procura = navegador.findElement(By.xpath("//input[@id='strBusca']"));
            WebElement botaoLupa = navegador.findElement(By.xpath("//button[@id='btnOK']"));

            procura.sendKeys(produto);
            botaoLupa.click();
    }

    @Então("eu devo poder colocar o {string} no carrinho")
    public void eu_devo_poder_colocar_o_no_carrinho(String produto) {
            WebElement primeiroItemDaBusca = navegador.findElements(By.xpath("//h3[contains(text(),'" + produto + "')]")).get(0);
            assertTrue(primeiroItemDaBusca.isDisplayed());

            valor = lerPreco();
            primeiroItemDaBusca.click();

            WebElement nomeProduto = navegador.findElement(By.xpath("//h1[contains(text(),'" + produto + "')]"));
            WebElement precoProduto = navegador.findElement(By.xpath("//span[@id='product-price'][contains(text(), '" + valor + "')]"));

            assertTrue(nomeProduto.isDisplayed());
            assertTrue(precoProduto.isDisplayed());

            WebElement botaoComprar = navegador.findElement(By.xpath("//button[contains(.,'Comprar')]"));
            botaoComprar.click();

            WebElement carrinhoNomeProduto = navegador.findElement(By.xpath("//a[@data-id='link'][contains(.,'" + produto + "')]"));
            WebElement carrinhoPrecoProduto = navegador.findElement(By.xpath("//strong[contains(.,'" + valor + "')]"));

            assertTrue(carrinhoNomeProduto.isDisplayed());
            assertTrue(carrinhoPrecoProduto.isDisplayed());

            WebElement botaoContinuar = navegador.findElement(By.xpath("//button[contains(.,'Continuar a compra')]"));
            botaoContinuar.click();
        }

    public String lerPreco() {
        WebElement preco = navegador.findElements(By.xpath("//span[contains(.,'Por R$ ')]")).get(0);

        assertTrue(preco.isDisplayed());

        valor = preco.getText();

        Pattern padraoPreco = Pattern.compile("\\d{1,3},\\d{2}"); // 3 numeros uma virgula e dois numeros
        Matcher matcher = padraoPreco.matcher(valor);

        return  matcher.find() ? matcher.group(0) : "Valor não encontrado";
    }
}