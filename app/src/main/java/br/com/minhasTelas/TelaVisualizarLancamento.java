package br.com.minhasTelas;


import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.funcoesGerais.FuncoesGerais;
import br.com.meusAdapters.MeuAdapterVisualizarLancamentos;
import br.com.minhasClasses.Lancamentos;
import com.example.saveMoney.R;

public class TelaVisualizarLancamento extends Activity {

	 ListView lvMinhaLista;
     Lancamentos lancamentos;
 	 ArrayList<Lancamentos> ArrayListLancamentos;
 	 float fReceitasMesAtual, fDespesasMesAtual, fSaldoMesAnteriores;
 	 int iRegistroSelecionado, CodLancamento;
 	 TextView tvTituloTipoLancamento,tvTextoDespesaMesAtual, tvTextoReceitaMesAtual, 
 	          tvTextoSaldosAnt, tvValorSaldosAnt, tvValorReceitaMesAtual, tvValorDespesaMesAtual;
 	 static String stringMes, stringAno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisar_lancamentos); 
        
        /* Atribui os objetos da tela as variáveis abaixo */
        lvMinhaLista            = (ListView) findViewById(R.id.lvLancamentos); 
        tvTituloTipoLancamento  = (TextView) findViewById(R.id.tvTituloTipoLancamento);
        tvTextoSaldosAnt        = (TextView) findViewById(R.id.tvTxSaldo);
        tvTextoDespesaMesAtual  = (TextView) findViewById(R.id.tvTxDespesa);
        tvTextoReceitaMesAtual  = (TextView) findViewById(R.id.tvTxReceita);
        tvValorSaldosAnt        = (TextView) findViewById(R.id.tvVlSaldo); 
        tvValorReceitaMesAtual  = (TextView) findViewById(R.id.tvVlReceita); 
        tvValorDespesaMesAtual  = (TextView) findViewById(R.id.tvVlDespesa);
 
        /* Buscam as categorias que estão inseridas*/
        lancamentos = new Lancamentos(0, "", 0, "", 0, "", "", "");
        
        /* Busca a data atual do sistema */
       	BuscaDataAtual();

       	/* Busca Resumo */
      	fSaldoMesAnteriores = FuncoesGerais.arredondaValor(lancamentos.ValorSaldoMesAnteriores(getApplicationContext(), stringMes, stringAno));     	
       	fReceitasMesAtual   = FuncoesGerais.arredondaValor(lancamentos.ValorTotalReceitaMesAtual(getApplicationContext(), stringMes, stringAno));
        fDespesasMesAtual   = FuncoesGerais.arredondaValor(lancamentos.ValorTotalDespesasMesAtual(getApplicationContext(), stringMes, stringAno));
       	
       	/* Seta o Titulo da tela */
       	tvTituloTipoLancamento.setText("Lançamentos Mês (" +  stringMes + "/"+stringAno+")");
       	tvTextoSaldosAnt.setText("Saldo Anterior");
       	tvValorSaldosAnt.setText("R$ " + Float.toString(fSaldoMesAnteriores));
        tvTextoReceitaMesAtual.setText("Receitas");
       	tvValorReceitaMesAtual.setText("R$ " + Float.toString(fReceitasMesAtual));
       	tvTextoDespesaMesAtual.setText("Despesas");
       	tvValorDespesaMesAtual.setText("R$ " + Float.toString(fDespesasMesAtual));
       	      
        /* Carrega os todos os dados do Lançamento */
        ArrayListLancamentos = lancamentos.BuscarLancamentosMesAtual(getApplicationContext(),stringMes, stringAno);            
        
		/* Seta o conteudo para os ListViews*/
		lvMinhaLista.setAdapter(new MeuAdapterVisualizarLancamentos(getApplicationContext(), ArrayListLancamentos));
		 /* Comando atribuido no Click longo */
        lvMinhaLista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				/* Guarda a posição do registro selecionado */
				iRegistroSelecionado = position;
				
				/* Mensagem de Alerta para Exclusão*/
				MensagemAlertaExcluir();
				
				return false;
			}
		});
    }
    
    public void MensagemAlertaExcluir() {
    	new AlertDialog.Builder(this)
		.setTitle("Confirmar Exclusão")
		.setMessage("Deseja realmente excluir o registro ?")
		.setNegativeButton("Não", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "Operação Cancelada!", Toast.LENGTH_SHORT).show();
				
			}
		})
		.setPositiveButton("Sim", new android.content.DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface arg0, int arg1) {
				
				/* Verificas se possui um registro selecionado */
				if(iRegistroSelecionado >= 0){
					
					/* Busca a data atual do sistema */
			       	BuscaDataAtual();
			        					
					/* Pega o código do lançamento atual */
				    ArrayListLancamentos = lancamentos.BuscarLancamentosMesAtual(getApplicationContext(), stringMes, stringAno);
					CodLancamento = ArrayListLancamentos.get(iRegistroSelecionado).CodLancamento;
					
					lancamentos = new Lancamentos(CodLancamento, "", 0, "", 0, "", "", "");
					lancamentos.DeletarLancamento(getApplicationContext());
			        
			        /* Busca a data atual do sistema */
			       	BuscaDataAtual();
					
			      	/* Busca Resumo */
			      	fSaldoMesAnteriores = FuncoesGerais.arredondaValor(lancamentos.ValorSaldoMesAnteriores(getApplicationContext(), stringMes, stringAno));     	
			       	fReceitasMesAtual   = FuncoesGerais.arredondaValor(lancamentos.ValorTotalReceitaMesAtual(getApplicationContext(), stringMes, stringAno));
			        fDespesasMesAtual   = FuncoesGerais.arredondaValor(lancamentos.ValorTotalDespesasMesAtual(getApplicationContext(), stringMes, stringAno));
			       	
			        /* Seta o Titulo da tela */
			      	tvTituloTipoLancamento.setText("Lançamentos Mês (" +  stringMes + "/"+stringAno+")");
			       	tvTextoSaldosAnt.setText("Saldo Anterior");
			       	tvValorSaldosAnt.setText("R$ " + Float.toString(fSaldoMesAnteriores));
			        tvTextoReceitaMesAtual.setText("Despesas");
			       	tvValorReceitaMesAtual.setText("R$ " + Float.toString(fReceitasMesAtual));
			       	tvTextoDespesaMesAtual.setText("Receitas");
			       	tvValorDespesaMesAtual.setText("R$ " + Float.toString(fDespesasMesAtual));
			       	      
			       	/* Atualiza ListViews Lançamentos */
					ArrayListLancamentos = lancamentos.BuscarLancamentosMesAtual(getApplicationContext(), stringMes, stringAno);
					
			        lvMinhaLista.setAdapter(new MeuAdapterVisualizarLancamentos(getApplicationContext(), ArrayListLancamentos));
			        CodLancamento = 0;
				}
			}
		}).show();
	}
    
    @Override
   	public boolean onCreateOptionsMenu(Menu menu) {
   		// Inflar o menu; isto acrescenta itens à barra de ação se ela estiver presente.
   		getMenuInflater().inflate(R.menu.voltar_grafico, menu);		
   		return true;
   	}

   	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		// Item de ação Handle bar clica aqui. A barra de ação será
   		// Tratar automaticamente os cliques no botão Início / Up , contanto
   		// Como você especificar uma atividade pai em AndroidManifest.xml .
   		int id = item.getItemId();
   		switch (id) {
   		case R.id.grafico:	
   	   			
   			/* Buscam as categorias que estão inseridas */
   	        lancamentos = new Lancamentos(0, "", 0, "", 0, "", "", "");
   	        
   	        if (lancamentos.BuscarTotaisLancamentoPorCategoriaMesAtual(getApplicationContext(), stringMes, stringAno, "Despesa").size() > 0){
   	   	      Intent i = new Intent(getApplicationContext(), TelaGraficoVisualizarLancamentos.class);
   	          startActivity(i);
   		      break;		   	    	 
   	        } else{
				Toast.makeText(getApplicationContext(), "Não existem registros no mês atual!", Toast.LENGTH_SHORT).show();
				break;
   	        }
   	        
   		case R.id.voltar:			
   			Intent intent = this.getIntent();
   	    	this.setResult(RESULT_OK, intent);
   	    	finish();
   	    	break;			
   		}
   		return super.onOptionsItemSelected(item);
   	}
     
    /* Busca a data atual do sistema */
   	public static void BuscaDataAtual(){
      	// Coloca zero nos mêses com 1 digito
   	 		if (String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+ 1).length() == 1){
   	 			stringMes = "0" + String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+ 1);
   	 		} else {
   	 			stringMes = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+ 1);
   	 		}
   	 		
   	 		// Transforma o ano em String
   	 		stringAno = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));   	 	
   	}
   	
   	
    public void Voltar(View V){
    	Intent intent = this.getIntent();
    	this.setResult(RESULT_OK, intent);
    	finish();		
	}
}
