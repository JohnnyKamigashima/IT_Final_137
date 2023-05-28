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
import pages.HomePage;
import pages.ResultadoPage;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompraProduto {
    static ChromeDriver navegador;
    static String chromeDriverPath = "/opt/homebrew/bin/chromedriver";//Configura caminho do chromedriver
    String valor;
    static HomePage homePage;

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

        homePage = new HomePage(navegador);
    }

    @After
    public static void tearDown() throws InterruptedException {
        navegador.quit();
    }


    @Dado("que eu esteja no site do {string}")
    public void que_eu_esteja_no_site_do(String baseUrl) {
        homePage.visitaHomePage(baseUrl);
    }

    @Quando("procurar pelo {string}")
    public void procurar_pelo(String produto) {
        homePage.pesquisaProduto(produto)
                .clicarBotaoLupa();
    }

    @Então("eu devo poder colocar o {string} no carrinho")
    public void eu_devo_poder_colocar_o_no_carrinho(String produto) {
        ResultadoPage resultadoPage = new ResultadoPage(navegador);
        valor = resultadoPage.lerPreco();
        resultadoPage
                .clicarProduto(produto)
                .verificarProduto(produto, valor)
                .clicarBotaoComprar()
                .verificarProdutoCarrinho(produto, valor)
                .clicarBotaoContinuar()
                ;
    }
}