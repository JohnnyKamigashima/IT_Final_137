package Pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarrinhoPage extends BasePage {
    public CarrinhoPage(AndroidDriver android) {
        super(android);
    }


    public CarrinhoPage validaProdutoCarrinho(String produto) {
        WebElement tituloProdutoCarrinho = android.findElement(By.id("com.novapontocom.extra:id/tv_card_product_name"));
        assertTrue(tituloProdutoCarrinho.getText().contains(produto));
        return this;
    }

    public CarrinhoPage validaValorProdutoCarrinho(String valor) {
        WebElement valorProdutoCarrinho = android.findElement(By.id("com.novapontocom.extra:id/tv_cart_product_price"));
        assertTrue(valorProdutoCarrinho.getText().contains(valor));
        return this;
    }

    public CarrinhoPage clicarComcluirCompra() {
        WebElement botaoConcluirCompra = android.findElement(By.id("com.novapontocom.extra:id/buttonContinue"));
        botaoConcluirCompra.click();
        return this;
    }

    public CarrinhoPage scrollDown() {
        swipe(1000, 500);
        return this;
    }

    public void swipe(int yInicial, int yFinal) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 0);

        dragNDrop.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), 1, yInicial));
        dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), 1, yFinal));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        android.perform(Collections.singletonList(dragNDrop));
    }

}
