package br.com.minhasTelas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.saveMoney.R;

public class TelaBemVindo extends Activity {

	Button    btSalvar;
	EditText  edtNome;
    String    Nome;
    SharedPreferences settings;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo); 

 
        /* Atribui os objetos da tela as variáveis abaixo */
        btSalvar = (Button)findViewById(R.id.btSalvarNom);
        edtNome  = (EditText) findViewById(R.id.edtNome);
        
          //Restaura as preferencias gravadas    
         settings = getSharedPreferences("NOME_USUARIO", 0);
         Nome = settings.getString("NomeUsu", "");	
        
         if (!Nome.equals("")){
        	  Intent i = new Intent(getApplicationContext(), TelaMenu.class);
			  startActivity(i);
			  finish();
         }         
         
         
         
        btSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		
			  if (!edtNome.getText().toString().trim().equals("")){
		         SharedPreferences.Editor editor = settings.edit();
		         editor.putString("NomeUsu", edtNome.getText().toString());
		     
		           //Confirma a gravação dos dados
		           editor.commit();
		           
		          Intent i = new Intent(getApplicationContext(), TelaMenu.class);
				  startActivity(i);
				  finish();
			  } else {
				  Toast.makeText(getApplicationContext(), "É necessário informar um nome válido!", Toast.LENGTH_SHORT).show();
			  }
			}
		});

	} 
  
}
