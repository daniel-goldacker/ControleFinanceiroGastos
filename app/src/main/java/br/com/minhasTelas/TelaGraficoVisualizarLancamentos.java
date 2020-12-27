package br.com.minhasTelas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import br.com.funcoesGerais.FuncoesGerais;
import br.com.minhasClasses.Categoria;
import br.com.minhasClasses.Lancamentos;
import com.example.saveMoney.R;

public class TelaGraficoVisualizarLancamentos extends Activity {	
	ArrayList<Lancamentos> totaisLancamentoPorCategoriaMesAtualDespesas;    
	static String stringMes, stringAno;
	Lancamentos lancamentos;
	Categoria categoria;
	private View mChart;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grafico_visualizar_lancamentos);

		/* Busca a data atual do sistema */
       	BuscaDataAtual();
       	
		/* Buscam as categorias que estão inseridas */
        lancamentos = new Lancamentos(0, "", 0, "", 0, "", "", "");
    	categoria = new Categoria(0, "");    	
        
        /* Carrega os Lançamento por Categoria do Mês Atual - Despesas */
    	totaisLancamentoPorCategoriaMesAtualDespesas = lancamentos.BuscarTotaisLancamentoPorCategoriaMesAtual(getApplicationContext(), stringMes, stringAno, "Despesa");
    	    	
        /* Quantidade de Registros que vai ser inserido */
        int nQtdRegistrosD = (totaisLancamentoPorCategoriaMesAtualDespesas.size());
               
        /* Declaração das variaveis para gerar o gráfico */
    	String[] descricaoGraficos = new String[nQtdRegistrosD];
    	float[] datosGraficos = new float[nQtdRegistrosD];
    	int[] dadosCor = new int[nQtdRegistrosD];
 
    	/* Monta o Gráfico*/
    	for(int i=0; i < nQtdRegistrosD; i++){    	  
    	  /* Adiciona os Valores no gráfico */
  		  descricaoGraficos[i] = categoria.BuscarCategoriaPorCodigo(totaisLancamentoPorCategoriaMesAtualDespesas.get(i).CodCategoria, getApplicationContext());  		    		  		    		
    	  datosGraficos[i]     = FuncoesGerais.arredondaValor(totaisLancamentoPorCategoriaMesAtualDespesas.get(i).ValorLancamento);     	
		  
		  /* Começa gerar as cores automaticamente, após o 9º registro*/
		  if (i < 8){
			  /* Traz os 10 primeiros registros de cor definidos */
		      if(i == 0) dadosCor[0] = Color.parseColor("#FF008000"); /* Green */
		      if(i == 1) dadosCor[1] = Color.parseColor("#FFFFA500"); /* Orange */
		      if(i == 2) dadosCor[2] = Color.parseColor("#FF0000FF"); /* Blue */
		      if(i == 3) dadosCor[3] = Color.parseColor("#FFFF0000"); /* Red */ 
		      if(i == 4) dadosCor[4] = Color.parseColor("#FFE9967A"); /* DarkSalmon */
		      if(i == 5) dadosCor[5] = Color.parseColor("#FF800000"); /* Maroon */
		      if(i == 6) dadosCor[6] = Color.parseColor("#FFDEB887"); /* Gold */
		      if(i == 7) dadosCor[7] = Color.parseColor("#FFFFDEAD"); /* Tea */
		      if(i == 8) dadosCor[8] = Color.parseColor("#FF00BFFF"); /* LightSteelBlue */  			  
		  } else {
			  /* Gera cor randomicamente de forma automatica */
	  		  dadosCor[i] = geraCorAutomaticamente();
		  }
    	}
      
		 /* Instanciar CategorySeries para traçar Pie Chart */
		 CategorySeries datosGraficosSeries = new CategorySeries("Despesas");
		 for (int i = 0; i < datosGraficos.length; i++) {
			 
		 /* Adicionando uma fatia com seus valores e nome para o gráfico de pizza */
		 datosGraficosSeries.add(descricaoGraficos[i] + " (R$ "+datosGraficos[i]+") ", datosGraficos[i]);
		 }
		 	 
		 /* Instanciar um representante para o gráfico de pizza */
		 DefaultRenderer defaultRenderer = new DefaultRenderer();
		 for (int i = 0; i < datosGraficos.length; i++) {
		 SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
		 seriesRenderer.setDisplayBoundingPoints(false);
		 seriesRenderer.setColor(dadosCor[i]);
		 
		/* Adicionando dadosCor para o gráfico */
		 defaultRenderer.setApplyBackgroundColor(true);
		 
		 /* Adicionando um renderizador para uma fatia */
		 defaultRenderer.addSeriesRenderer(seriesRenderer);

		 DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
		 float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, metrics);
		 
         /* Aumentando o Texto da legenda */		 
		 defaultRenderer.setLegendTextSize(val);
		 defaultRenderer.setLabelsTextSize(val);

		 
		 
		 
		 defaultRenderer.setShowLegend(false);
		 defaultRenderer.setShowLabels(true);

		 /*
		 defaultRenderer.setShowLegend(true);
		 defaultRenderer.setShowLabels(false);
		 */
		 }

		 defaultRenderer.setLabelsColor(Color.BLACK); 
		// defaultRenderer.setChartTitle("Gerenciador de Despesas");
		// defaultRenderer.setChartTitleTextSize(60);
		// defaultRenderer.setShowAxes(false);


		 /* Declara o campo */
		 LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
		 
		 /* Remover quaisquer pontos de vista antes u pintar o quadro */
		 chartContainer.removeAllViews();
		 
		 /* Desenho gráfico de pizza */
		 mChart = ChartFactory.getPieChartView(getBaseContext(),
		 datosGraficosSeries, defaultRenderer);
		 
		 /* Adicionando a visão para a LinearLayout */
		 chartContainer.addView(mChart);        
        }
                

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* Inflar o menu; isto acrescenta itens à barra de ação se ela estiver presente.*/
		getMenuInflater().inflate(R.menu.voltar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/* Item de ação Handle bar clica aqui. A barra de ação será 
		 Tratar automaticamente os cliques no botão Início / Up , contanto 
		 Como você especificar uma atividade pai em AndroidManifest.xml .*/
		int id = item.getItemId();
		switch (id) {
		case R.id.voltar:
			Intent intent = this.getIntent();
			this.setResult(RESULT_OK, intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/* Busca a data atual do sistema */
	public static void BuscaDataAtual() {
		/* Coloca zero nos mêses com 1 digito */
		if (String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1).length() == 1) {
			stringMes = "0"+ String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
		} else {
			stringMes = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
		}

		/* Transforma o ano em String */
		stringAno = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	}
	
    public void Voltar(View V){
    	Intent intent = this.getIntent();
    	this.setResult(RESULT_OK, intent);
    	finish();		
	}
    
    /* Gera cor randomicamente  de forma automatica */
    public int geraCorAutomaticamente(){
    	int vermelho, verde, azul; 
	   	Random corRandomica = new Random();  

		vermelho = corRandomica.nextInt(255);
		verde    = corRandomica.nextInt(255);
		azul     = corRandomica.nextInt(255);

		return Color.rgb(vermelho, verde ,azul);
    }
}