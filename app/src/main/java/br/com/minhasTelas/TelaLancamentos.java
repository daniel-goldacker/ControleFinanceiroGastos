package br.com.minhasTelas;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.meusAdapters.MeuAdapterCategorias;
import br.com.minhasClasses.Categoria;
import br.com.minhasClasses.Lancamentos;
import com.example.saveMoney.R;


public class TelaLancamentos extends Activity {

	static Spinner spCategoria;
	RadioGroup rgTipoLancamento;
	EditText edDesLancamento, edValorLancamento;
	ImageButton btAddCategoria;
	ListView lvMinhaLista;
	static TextView tvDataLancamento;
	static int dia, mes, ano;
	static String stringDia, stringMes, stringAno, dataLancamento;
	static ArrayList<Categoria> ArrayListCategorias;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lancamentos);
		
		
	    /* Atribui os objetos da tela as variáveis abaixo */
		spCategoria        = (Spinner) findViewById(R.id.spCategoria);
		rgTipoLancamento   = (RadioGroup) findViewById(R.id.rgTipoLancamentos);
		edDesLancamento    = (EditText) findViewById(R.id.edDesLancamento);
		edValorLancamento  = (EditText) findViewById(R.id.edValorLancamento);	
		tvDataLancamento   = (TextView) findViewById(R.id.tvDataLancamento);
		btAddCategoria     = (ImageButton) findViewById(R.id.btAddCategoria);
		
		/* Carregar a lista de caregorias*/
		final Categoria categoria = new Categoria(0, "");
		ArrayListCategorias = categoria.BuscarCategoria(getApplicationContext(), "F");
		spCategoria.setAdapter(new MeuAdapterCategorias(getApplicationContext(), ArrayListCategorias, 1));
		
		spCategoria.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				/* Atualiza a lista de categorias, toda vez que for clicado no Spinner */		  
				ArrayListCategorias = categoria.BuscarCategoria(getApplicationContext(), "F");
				TelaLancamentos.spCategoria.setAdapter(new MeuAdapterCategorias(getApplicationContext(), ArrayListCategorias, 1));				
				return false;
			}
		});
				
    	// Controla a data de lançamento
    	ControlaDataLancamento();

		tvDataLancamento.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				 // Abre a tela para escolher a data de lançamento
				 DialogFragment newFragment = new DatePickerFragment();
				 newFragment.show(getFragmentManager(),"DatePicker");				
			}
		});	
		
		
		btAddCategoria.setOnClickListener(new OnClickListener() {
		    @Override
			public void onClick(View arg0) {
					Intent i = new Intent(getApplicationContext(), TelaCategoria.class);
					startActivity(i);			
			}		    		    		    		 
		});
		
		
	}
	
	
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflar o menu; isto acrescenta itens à barra de ação se ela estiver presente.
			getMenuInflater().inflate(R.menu.voltar_gravar, menu);		
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Item de ação Handle bar clica aqui. A barra de ação será
			// Tratar automaticamente os cliques no botão Início / Up , contanto
			// Como você especificar uma atividade pai em AndroidManifest.xml .
			int id = item.getItemId();
			switch (id) {	
			case R.id.gravar:
		        int inconsistencia = 0; //Verificar se ha algum valor inconsistente 
				
				if (edDesLancamento.getText().toString().trim().equals("")) {
					inconsistencia = 1;
				}
				
				if (edValorLancamento.getText().toString().trim().equals("")) {
					inconsistencia = 2;
				}
				
				if ((edDesLancamento.getText().toString().trim().equals("")) & (edValorLancamento.getText().toString().trim().equals(""))){
					inconsistencia = 3;
				}
				
				if (spCategoria.getCount() == 0){
					inconsistencia = 4;
				}
				
				if (inconsistencia == 0){		
					String descTipoLanc = ((RadioButton) findViewById(rgTipoLancamento.getCheckedRadioButtonId())).getText().toString();
					
					// Grava os Lançamentos
					Lancamentos lancamento = new Lancamentos(0, 
									descTipoLanc, 
									((Categoria) spCategoria.getSelectedItem()).CodCategoria, 
									edDesLancamento.getText().toString(), 
									Float.parseFloat(edValorLancamento.getText().toString()), 
									dataLancamento,
									stringMes,
									stringAno);

					// Inseri os Lançamentos 
					lancamento.InserirLancamentos(getApplicationContext());	
					
			    	// Controla a data de lançamento
					dia = 0; mes = 0; ano = 0;
			    	ControlaDataLancamento();					
					edDesLancamento.setText("");
					edValorLancamento.setText("");
				
				} else{
					switch (inconsistencia) {
					case 1:
						Toast.makeText(getApplicationContext(), "Deve ser informada uma descrição para o lançamento!", Toast.LENGTH_SHORT).show();
						break;
					case 2:
						Toast.makeText(getApplicationContext(), "Deve ser informado um valor para o lancamento!", Toast.LENGTH_SHORT).show();
						break;
					case 3:
						Toast.makeText(getApplicationContext(), "Devem ser informados valor e descrição para o lançamento!", Toast.LENGTH_SHORT).show();
						break;
					case 4:
						Toast.makeText(getApplicationContext(), "Deve ser informada uma categoria!", Toast.LENGTH_SHORT).show();
						break;
					}
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
	        DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, ano, mes, dia);
	        return dpd;
	    }

	    @Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
            // Retorna o dia, mês, ano selecionado
	    	dia = day;
	    	mes = month;
	    	ano = year;
	    	
	    	// Controla a data de lançamento
	    	ControlaDataLancamento();
	    }
	}
	
   // Controla a data de lançamento
   public static void ControlaDataLancamento(){
	   // Pega o dia, mês, ano atual do sistema com padrão
		if(dia == 0) dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		if(mes == 0) mes = Calendar.getInstance().get(Calendar.MONTH);
		if(ano == 0) ano = Calendar.getInstance().get(Calendar.YEAR);
		
		// Coloca zero nos dias com 1 digito 
		if (String.valueOf(dia).length() == 1){
			stringDia = "0" + String.valueOf(dia);
		} else {
			stringDia = String.valueOf(dia);
		}
		
		// Coloca zero nos mêses com 1 digito
		if (String.valueOf(mes+ 1).length() == 1){
			stringMes = "0" + String.valueOf(mes + 1);
		} else {
			stringMes = String.valueOf(mes + 1);
		}
		
		// Transforma o ano em String
		stringAno = String.valueOf(ano);
	
		// Monta a data completa
		dataLancamento = String.valueOf(stringDia + "/" + stringMes + "/" + stringAno);
		tvDataLancamento.setText(" "+dataLancamento);
   }
	
    public void Voltar(View V){
    	Intent intent = this.getIntent();
    	this.setResult(RESULT_OK, intent);
    	finish();		
	}

}