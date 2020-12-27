package br.com.minhasTelas;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.meusAdapters.MeuAdapterCategorias;
import br.com.meusAdapters.MeuAdapterVisualizarLancamentos;
import br.com.minhasClasses.Categoria;
import br.com.minhasClasses.Lancamentos;
import com.example.saveMoney.R;


public class TelaConsultarLancamento extends Activity {

	TextView tvCategoria, tvReceitaValor, tvDespesaValor;
	static TextView tvDataConsulta;
	static Spinner spCategorias;
	CheckBox cbConsiderarData;
	int iRegistroSelecionado, CodLancamento;
	RadioGroup rgTipoLancamento;	
	ListView lvConsultaLancamentos;
	static int dia, mes, ano;
	static String stringMes, stringAno, dataConsulta;
	static ArrayList<Categoria> ArrayListCategorias;
	Lancamentos lancamentos;	
	ArrayList<Lancamentos> ArrayListLancamentos;
	Categoria categoria;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lancamentos); 
        
        /* Atribui os objetos da tela as variáveis abaixo */
        rgTipoLancamento      = (RadioGroup)findViewById(R.id.rgTipoLancamentos2);
        lvConsultaLancamentos = (ListView)findViewById(R.id.lvConsLancamento);
		spCategorias          = (Spinner)findViewById(R.id.spCategoria2);
		tvDataConsulta        = (TextView)findViewById(R.id.tvDataConsulta);   
		cbConsiderarData      = (CheckBox)findViewById(R.id.cbConsideraData);
				
		/* Atualiza a lista de categorias, toda vez que for clicado no Spinner */		  
		categoria = new Categoria(0, "");
		ArrayListCategorias = categoria.BuscarCategoria(getApplicationContext(), "T");
		spCategorias.setAdapter(new MeuAdapterCategorias(getApplicationContext(), ArrayListCategorias, 1));
						
    	// Controla a data de consulta
		dia = 0; mes = 0; ano = 0;
		ControlaDataConsulta();

		cbConsiderarData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View cb) {
				if (!((CheckBox) cb).isChecked()) {
					dia = 0; mes = 0; ano = 0;
					stringMes = "00";
					stringAno = "0000";
					
					dataConsulta = String.valueOf(stringMes + "/" + stringAno);
					tvDataConsulta.setText(" " + dataConsulta);

				} else {
        	      // Controla a data de consulta
				  dia = 0; mes = 0; ano = 0;
				  ControlaDataConsulta();					
				}
			}
		});
		
    	tvDataConsulta.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				 // Abre a tela para escolher a data de lançamento
				 DialogFragment newFragment = new DatePickerFragment();
				 newFragment.show(getFragmentManager(),"DatePicker");				
			}
		});
    	       	    
    }
    
    
 
     
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflar o menu; isto acrescenta itens à barra de ação se ela estiver presente.
			getMenuInflater().inflate(R.menu.voltar_consultar, menu);		
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Item de ação Handle bar clica aqui. A barra de ação será
			// Tratar automaticamente os cliques no botão Início / Up , contanto
			// Como você especificar uma atividade pai em AndroidManifest.xml .
			int id = item.getItemId();
			switch (id) {	
			case R.id.consulta:
				/* Verifica se existe uma categoria selecionada */
        		if (categoria.BuscarCategoria(getApplicationContext(), "T").size() == 0){	
        		  Toast.makeText(getApplicationContext(), "Não existe uma categoria definida para realizar a consulta!", Toast.LENGTH_SHORT).show();
                } else {
				
				  /* Consultar os lançamentos */
			   	  Lancamentos lancamentos = new Lancamentos(0, "", 0, "", 0, "", "", "");
				  String descTipoLanc = ((RadioButton) findViewById(rgTipoLancamento.getCheckedRadioButtonId())).getText().toString();
				
		          ArrayListLancamentos = lancamentos.ConsultarLancamentosFiltro(getApplicationContext(), ((Categoria) spCategorias.getSelectedItem()).CodCategoria, descTipoLanc, stringMes, stringAno, cbConsiderarData.isChecked());
				  
				  //Exibir mensagem caso a consulta não traga nada
				  if (ArrayListLancamentos.size() == 0){
				  	Toast.makeText(getApplicationContext(), "Não há registros!", Toast.LENGTH_SHORT).show();
			  	  }				
		          		       
		          lvConsultaLancamentos.setAdapter(new MeuAdapterVisualizarLancamentos(getApplicationContext(), ArrayListLancamentos));

		          /* Comando atribuido no Click longo */
		          lvConsultaLancamentos.setOnItemLongClickListener(new OnItemLongClickListener() {

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
				break;
				
			case R.id.voltar:			
				Intent intent = this.getIntent();
		    	this.setResult(RESULT_OK, intent);
		    	finish();
		    	break;
			}
			return super.onOptionsItemSelected(item);
		}
		
		public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		  @Override
		  public Dialog onCreateDialog(Bundle savedInstanceState){  				
		    DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, ano, mes, dia){
		      
		      @Override
		      protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        int day = getContext().getResources().getIdentifier("android:id/day", null, null);
		        if(day != 0){
		          View dayPicker = findViewById(day);
		          if (dayPicker != null){
		            // Definir a visibilidade de exibição Dia Off/Gone.
		            dayPicker.setVisibility(View.GONE);
		          }
		        }
		      }
		    };
		    return dpd; 
		  }
		    
		  @Override
		  public void onDateSet(DatePicker view, int year, int month, int day) {
	        // Retorna mês, ano selecionado		    	
		    mes = month;
		    ano = year;
		  	
		    // Controla a data de consula
		    ControlaDataConsulta();
		  }
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
							// Controla a data de consulta
					        ControlaDataConsulta();					        					      

					        // Busca a descrição do tipo de lançamento
					        String descTipoLanc = ((RadioButton) findViewById(rgTipoLancamento.getCheckedRadioButtonId())).getText().toString();							 
					        
					        // Busca o código do lançamento a ser excluido
					        lancamentos = new Lancamentos(0, "", 0, "", 0, "", "", "");							
					        ArrayListLancamentos = lancamentos.ConsultarLancamentosFiltro(getApplicationContext(), ((Categoria) spCategorias.getSelectedItem()).CodCategoria, descTipoLanc, stringMes, stringAno, cbConsiderarData.isChecked());
							CodLancamento = ArrayListLancamentos.get(iRegistroSelecionado).CodLancamento;

							// Passa o código do lançamento a ser excluido
					        lancamentos = new Lancamentos(CodLancamento, "", 0, "", 0, "", "", "");							
							lancamentos.DeletarLancamento(getApplicationContext());		
													
  						    // Carrega os lançamentos que estão disponiveis após a exclusão						
					        ArrayListLancamentos = lancamentos.ConsultarLancamentosFiltro(getApplicationContext(), ((Categoria) spCategorias.getSelectedItem()).CodCategoria, descTipoLanc, stringMes, stringAno, cbConsiderarData.isChecked());
						    lvConsultaLancamentos.setAdapter(new MeuAdapterVisualizarLancamentos(getApplicationContext(), ArrayListLancamentos));
						    cbConsiderarData.setChecked(true);
						    CodLancamento = 0;
						}
					}
				}).show();
			}
		
		
		  
		 // Controla a data de lançamento
		   public static void ControlaDataConsulta(){
			    // Pega o dia, mês, ano atual do sistema com padrão
			    if(dia == 0) dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);				
			    if(mes == 0) mes = Calendar.getInstance().get(Calendar.MONTH);
				if(ano == 0) ano = Calendar.getInstance().get(Calendar.YEAR);
								
				// Coloca zero nos mêses com 1 digito
				if (String.valueOf(mes+ 1).length() == 1){
					stringMes = "0" + String.valueOf(mes + 1);
				} else {
					stringMes = String.valueOf(mes+ 1);
				}
				
				// Transforma o ano em String
				stringAno = String.valueOf(ano);
			
				// Monta a data completa
				dataConsulta = String.valueOf(stringMes + "/" + stringAno);
				tvDataConsulta.setText(" " + dataConsulta);
		 }		
		
    
    public void Voltar(View V){
    	Intent intent = this.getIntent();
    	this.setResult(RESULT_OK, intent);
    	finish();		
	}
}
