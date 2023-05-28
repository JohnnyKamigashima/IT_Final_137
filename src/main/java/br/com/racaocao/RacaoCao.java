package br.com.racaocao;

import br.com.racaocao.enums.Tamanho;

public class RacaoCao {
    private RacaoCao() {

    }
    public static int calcularRacaoCao(Tamanho tamanho, Double peso) {
        int limiteInferiorPeso = 0;
        int limiteSuperiorPeso = 100000;

        if (peso <= limiteInferiorPeso) {
            throw new IllegalArgumentException("O peso deve ser maior que " + limiteInferiorPeso + " kg");
        } else if (peso > limiteSuperiorPeso) {
            throw new IllegalArgumentException("O peso deve ser menor que " + limiteSuperiorPeso + " kg");
        }

        int quantidadeRacaoEmGramas;
        int pesoEmGramas = (int) Math.round(peso * 1000);
        final int P = 10;
        final int M = 20;
        final int G = 30;

        quantidadeRacaoEmGramas = pesoEmGramas * switch (tamanho) {
            case P -> P;
            case M -> M;
            case G -> G;
        };

        return quantidadeRacaoEmGramas;
    }
}
