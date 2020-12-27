package br.com.minhasTelas;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.com.meusAdapters.MeuAdapterCategorias;
import br.com.minhasClasses.Categoria;
import com.example.saveMoney.R;

public class TelaCategoria extends Activity {
    int       iRegistroSelecionado;
	EditText  edtCategoria;
	ListView  lvMinhaLista;
	ArrayList<Categoria> ArrayListCategorias;
	Categoria categoria;
	int CodCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias); 
        CodCategoria = 0;
        
        /* Atribui os objetos da tela as variáveis abaixo */
        edtCategoria   = (EditText)findViewById(R.id.editCategoria);
        lvMinhaLista   = (ListView)findViewById(R.id.lvLancamentos);
  
        iRegistroSelecionado = -1;
        
         /* Buscam as categorias que estão inseridas*/
        categoria = new Categoria(CodCategoria, edtCategoria.getText().toString());
        
        /* Seta o conteudo para os ListViews*/
        ArrayListCategorias = categoria.BuscarCategoria(getApplicationContext(), "F");                
        lvMinhaLista.setAdapter(new MeuAdapterCategorias(getApplicationContext(), ArrayListCategorias, 0));        
        lvMinhaLista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				/* Carrega a categoria selecionada e o codigo da categoria */
		        ArrayListCategorias = categoria.BuscarCategoria(getApplicationContext(), "F");
				edtCategoria.setText(ArrayListCategorias.get(position).DescCategoria);
				CodCategoria = ArrayListCategorias.get(position).CodCategoria;
			}
		  });
        
        
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
		.setNegativeButton("Não", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "Operação Cancelada!", Toast.LENGTH_SHORT).show();
				
			}
		})
		.setPositiveButton("Sim", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				/* Verificas se possui um registro selecionado */
				if(iRegistroSelecionado >= 0){
					
					/* Pega o c�digo da categoria atual */
					ArrayListCategorias = categoria.BuscarCategoria(getApplicationContext(), "F");
					CodCategoria = ArrayListCategorias.get(iRegistroSelecionado).CodCategoria;
					
				    categoria = new Categoria(CodCategoria, edtCategoria.getText().toString());
					categoria.DeletarCategoria(getApplicationContext());
					
					/* Atualiza ListViews Categorias */
					ArrayListCategorias = categoria.BuscarCategoria(getApplicationContext(), "F");
			        lvMinhaLista.setAdapter(new MeuAdapterCategorias(getApplicationContext(), ArrayListCategorias, 0));
			        edtCategoria.setText("");
			        CodCategoria = 0;
				}
			}
		}).show();
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
			/* Verifica se existe categoria informada */
			if (!edtCategoria.getText().toString().trim().equals("")){
			  categoria = new Categoria(CodCategoria, edtCategoria.getText().toString());
			  
			  if (CodCategoria != 0){
				  /* Se o codigo da categoria for diferente de 0, Altera Registro */
				  categoria.AlterarCategoria(getApplicationContext());
				  edtCategoria.setText("");
				  CodCategoria = 0;
			  } else {
				  /* Se o codigo da categoria for 0, insere o registro */						
				  categoria.InserirCategoria(getApplicationContext());
				  edtCategoria.setText("");
				  CodCategoria = 0;
			  }
			  
			/* Seta o conteudo para os ListViews*/
			lvMinhaLista.setAdapter(new MeuAdapterCategorias(getApplicationContext(), categoria.BuscarCategoria(getApplicationContext(), "F"), 0));
			} else {
				Toast.makeText(getApplicationContext(), "É necessário informar uma descrição válida!", Toast.LENGTH_SHORT).show();
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
    
    
    public void Voltar(View V){
    	Intent intent = this.getIntent();
    	this.setResult(RESULT_OK, intent);    	
    	finish();		
	}
    
}
