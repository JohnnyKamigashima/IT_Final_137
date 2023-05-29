import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndroidExtraTest {
    private AndroidDriver android;

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

        String produto = "Barbeador Philips Aquatouch 3D Seco Molhado - S112141 Bivolt";

        android.findElement(By.id("com.novapontocom.extra:id/tv_home_search"))
                .click();
        android.findElement(By.id("com.novapontocom.extra:id/editTextSearchProducts"))
                .sendKeys(produto);

        KeyEvent keybboardEnter = new KeyEvent(AndroidKey.ENTER);
        android.pressKey(keybboardEnter);

        String descricaoProduto = android.findElement(By.id("com.novapontocom.extra:id/textViewProductName")).getText();
        String valor = lerPreco();

        System.out.println(valor);

        android.findElements(By.id("com.novapontocom.extra:id/textViewProductName")).get(0).click();

        assertTrue(descricaoProduto.contains(produto));

        swipe(1000, 500);

        android.findElement(By.id("com.novapontocom.extra:id/button")).click();

        String descricaoProdutoCarrinho = android.findElement(By.id("com.novapontocom.extra:id/tv_card_product_name")).getText();
        String valorProdutoCarrinho = android.findElement(By.id("com.novapontocom.extra:id/tv_cart_product_price")).getText();

        assertTrue(descricaoProdutoCarrinho.contains(produto));
        assertTrue(valorProdutoCarrinho.contains(valor));

        swipe(1000, 500);

        android.findElement(By.id("com.novapontocom.extra:id/buttonContinue")).click();
        ;
    }

    @AfterEach
    public void tearDown() {
        //android.quit();
    }

    public String lerPreco() {
        WebElement preco = android.findElements(By.id("com.novapontocom.extra:id/textViewProductPrice")).get(0);

        assertTrue(preco.isDisplayed());

        String valor = preco.getText();

        Pattern padraoPreco = Pattern.compile("\\d{1,3},\\d{2}"); // 3 numeros uma virgula e dois numeros
        Matcher matcher = padraoPreco.matcher(valor);

        return matcher.find() ? matcher.group(0) : "Valor não encontrado";
    }

    public void swipe(int yInicial, int yFinal) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 0);

        dragNDrop.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),1,yInicial));
        dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(700),PointerInput.Origin.viewport(),1,yFinal));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        android.perform(Collections.singletonList(dragNDrop));
    }

}
