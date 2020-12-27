package br.com.minhasTelas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.saveMoney.R;


public class TelaMenu extends Activity {
    
	 Button TelaCategoria, TelaLancamentos, TelaVisualizarLancamentos, TelaConsultarLancamentos;
	 TextView edNomeUsuario;
     String Nome;
     SharedPreferences settings;
     Button btVoltar, btSalvarLancamento;
     
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
       // Exibe menu secundário no menu principal 
  	   MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu_secundario, menu);
       return true;
     } 
	 
	 @Override
     public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
         case R.id.itSobre:
           Intent i = new Intent(getApplicationContext(), TelaSobre.class);
		   startActivity(i);			        	 
           return true;
         default:
           return super.onOptionsItemSelected(item);
       }
     }
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal); 
        
        TelaCategoria             = (Button) findViewById(R.id.btMenuCategoria);
        TelaLancamentos           = (Button) findViewById(R.id.btMenuLancamentos);
        TelaVisualizarLancamentos = (Button) findViewById(R.id.btMenuVisualizarLancamentos);
        TelaConsultarLancamentos  = (Button) findViewById(R.id.btConsultaLancamentos);
        edNomeUsuario             = (TextView) findViewById(R.id.tvDesenvolvedorUsu);
    	
        //Restaura as preferencias gravadas  
        settings = getSharedPreferences("NOME_USUARIO", 0);
        Nome = settings.getString("NomeUsu", "");
        
        if(!Nome.equals("")){
        	edNomeUsuario.setText("Olá, seja bem vindo(a) " + Nome + ". ");
        }
        
        TelaLancamentos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), TelaLancamentos.class);
				startActivity(i);
			}
		});
        
        
        TelaCategoria.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), TelaCategoria.class);
				startActivity(i);			
			}
		});
        
        TelaVisualizarLancamentos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			
			 
				Intent i = new Intent(getApplicationContext(), TelaVisualizarLancamento.class);
				startActivity(i);
				
				
			}
		});
        
        TelaConsultarLancamentos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
               Intent i = new Intent(getApplicationContext(), TelaConsultarLancamento.class);
               startActivity(i);
			}
		});
          
    }
}
