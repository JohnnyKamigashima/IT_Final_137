import br.com.racaocao.enums.Tamanho;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static br.com.racaocao.RacaoCao.calcularRacaoCao;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RacaoCaoTest {
    @Test
    @DisplayName("Teste positivo cao Pequeno de 10 kg")
    void calcularRacaoCaoTestPq10kg() {

        Tamanho tamanho = Tamanho.P;
        Double peso = 3.0;
        int valorEsperadoEmGramas = 30000;

        int resultado = calcularRacaoCao(tamanho, peso);

        assertEquals(valorEsperadoEmGramas, resultado);
    }

    @Test
    @DisplayName("Teste negativo cao Pequeno de 0 kg")
    void calcularRacaoCaoTestPq0kg() {

        Tamanho tamanho = Tamanho.P;
        Double peso = 0.0;
        String mensagemEsperada = "O peso deve ser maior que 0 kg";

        try {
            calcularRacaoCao(tamanho, peso);
        } catch (IllegalArgumentException erro) {
            assertEquals(mensagemEsperada, erro.getMessage());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "P, 0.0, O peso deve ser maior que 0 kg",
            "M, -1.0, O peso deve ser maior que 0 kg",
            "G, 101.0, O peso deve ser menor que 100 kg",
            "G, 100.01, O peso deve ser menor que 100 kg",
            "P, -0.01, O peso deve ser maior que 0 kg"
    })
    @DisplayName("Testes negativos com lista CSV")
    void calcularRacaoCaoTestNegativoLista(Tamanho tamanho, Double peso, String mensagemEsperada) {

        try {
            calcularRacaoCao(tamanho, peso);
        } catch (IllegalArgumentException erro) {
            assertEquals(mensagemEsperada, erro.getMessage());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "RacaoCao.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Testes positivos com arquivo CSV")
    void calcularRacaoCaoTestPositivoComLista(Tamanho tamanho, Double peso, int valorEsperadoEmGramas) {

        int resultado = calcularRacaoCao(tamanho, peso);

        assertEquals(valorEsperadoEmGramas, resultado);
    }
}
