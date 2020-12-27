package br.com.minhasTelas;

import com.example.saveMoney.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;

public class TelaSobre extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
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


}
