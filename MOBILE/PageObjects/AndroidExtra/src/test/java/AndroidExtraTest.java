import Pages.HomePage;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidExtraTest {
    private AndroidDriver android;
    String produto = "Barbeador Philips Aquatouch 3D Seco Molhado - S112141 Bivolt";
    String valor;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:deviceName", "Redmi 8");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:udid", "1dcc3adb");
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);
        desiredCapabilities.setCapability("appium:appPackage", "com.novapontocom.extra");
        desiredCapabilities.setCapability("appium:appActivity", "br.concrete.base.ui.SplashActivity");

        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        android = new AndroidDriver(remoteUrl, desiredCapabilities);
        android.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void compraProdutos() throws InterruptedException {
        HomePage homePage = new HomePage(android);
        valor = homePage.procuraProduto(produto)
                .clicarBotaoProcura()
                .validaBuscaProduto(produto)
                .obtemValorDoProduto();
        homePage.clicarProduto()
                .validaNomeProduto(produto)
                .validaValorProduto(valor)
                .clicarContinuarCompra()
                .validaProdutoCarrinho(produto)
                .validaValorProdutoCarrinho(valor)
                .scrollDown()
                .clicarComcluirCompra()
        ;
    }

    @AfterEach
    public void tearDown() {
        android.quit();
    }
}
