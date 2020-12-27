package br.com.funcoesGerais;

import java.math.BigDecimal;

public class FuncoesGerais {
	
	/* Função para arredondar valor */
	public static float arredondaValor(float valor){
	     BigDecimal ValorLancamentoArredondado = new BigDecimal(valor); 
		return ValorLancamentoArredondado.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); 
	}

}
