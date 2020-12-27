package br.com.minhasClasses;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import br.com.bancoDados.DB;

public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	public int CodCategoria;
	public String DescCategoria; 
	
	public Categoria(int CodCategoria, String DescCategoria) {
		this.CodCategoria = CodCategoria;
		this.DescCategoria = DescCategoria;
	}
	
	public ArrayList<Categoria> GetListaCategoria(){
		return null;	
	}

	/* Inserir Categoria */
	public void InserirCategoria(Context Contexto) {
		//Insert		
		DB banco = new DB(Contexto);
		SQLiteDatabase db = banco.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put("DES_CATEGORIA", this.DescCategoria);
		
		long id = db.insert("TB_CATEGORIA", null, content);

		if (id > 0) {
			Toast.makeText(Contexto, "Categoria inserida com sucesso!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(Contexto, "Erro ao inserir a categoria!", Toast.LENGTH_SHORT).show();
		}
	}
	
	/* Alterar Categoria */
	public void AlterarCategoria(Context contexto){
		/* Pode Alterar Categoria */
	    if (!LancamentoPossuiCategoria(CodCategoria, contexto)){ 
		  DB banco = new DB(contexto);
		  SQLiteDatabase db= banco.getWritableDatabase();
		  ContentValues content = new ContentValues();
		  content.put("DES_CATEGORIA", DescCategoria);
		  String whereClause = "COD_CATEGORIA = ?"; 
		  String[] whereArgs = new String[]{Integer.toString(CodCategoria)};
		  long id = db.update( "TB_CATEGORIA"
		    			       , content
		    			       , whereClause
	    				       , whereArgs );
	  	  if(id > 0){
			Toast.makeText(contexto, "Categoria alterada com sucesso!", Toast.LENGTH_SHORT).show();
		  } else{
			Toast.makeText(contexto,"Erro ao alterar categoria!", Toast.LENGTH_SHORT).show();
		  }
	    }else{
		    Toast.makeText(contexto, "Não é possivel alterar está categoria, pois existe lançamento referenciando!", Toast.LENGTH_SHORT).show();	  
	    }
	}
	
	/* Buscar Categoria */
	public ArrayList<Categoria> BuscarCategoria(Context Contexto, String pInsereVazio){
		
		ArrayList<Categoria> ArrayCategorias = new ArrayList<Categoria>();
		Categoria dadosCategorias; 
		
		//Inicia a busca na base SQLite
		DB banco = new DB(Contexto);
		SQLiteDatabase db= banco.getReadableDatabase();
		Cursor cursor  = db.query("TB_CATEGORIA" // Tabela
			        , new String[]{"COD_CATEGORIA","DES_CATEGORIA"} // Colunas
			        , null // "us_codusu = ? "
                    , null // Sem parâmetros para o WHERE String[]{"us_codusu","us_nomusu"}
                    , null //"_id"
                    , null
                    , "COD_CATEGORIA" // ORDER BY
                    , "100");

		/* Se tiver que criar um registro vázio, cria aqui se houver alguma categoria cadastrada */
		if (pInsereVazio.equals("T")) {
			dadosCategorias = new Categoria(99999999, "Nenhuma Opção");
		    dadosCategorias.CodCategoria  = 99999999;
			dadosCategorias.DescCategoria = "Nenhuma Opção";
			ArrayCategorias.add(dadosCategorias);			
		}
			
		
		while(cursor.moveToNext()){
			/* Adiciona os Contatos */
			dadosCategorias = new Categoria(cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA")), cursor.getString(cursor.getColumnIndex("DES_CATEGORIA")));
				
		    dadosCategorias.CodCategoria  = cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA"));
			dadosCategorias.DescCategoria = cursor.getString(cursor.getColumnIndex("DES_CATEGORIA"));
			ArrayCategorias.add(dadosCategorias);
		}
						
		cursor.close();
		return ArrayCategorias;
	}
	
	/* Buscar Categoria por Código */
    public String BuscarCategoriaPorCodigo(int codigoCategoria, Context contexto){
    	String descricaoCategoria = "";
    	
    	//Busca Descrição da Categoria
    	DB banco = new DB(contexto);
    	SQLiteDatabase db= banco.getReadableDatabase();
    	Cursor cursor  = db.query("TB_CATEGORIA" // Tabela
    			        , new String[]{"COD_CATEGORIA", "DES_CATEGORIA"} // Colunas
    			        , "COD_CATEGORIA = ? "
    	                , new String[]{Integer.toString(codigoCategoria)}
    	                , null //"_id"
    	                , null
    	                , "COD_CATEGORIA" // ORDER BY
    	                , "100");
    	
    	while(cursor.moveToNext()){
		  descricaoCategoria = cursor.getString(cursor.getColumnIndex("DES_CATEGORIA"));
    	}
    	
    	return descricaoCategoria;
    }
    
	/* Pode Excluir Categoria */
    public Boolean LancamentoPossuiCategoria(int codigoCategoria, Context contexto){
    	Boolean LancamentoPossuiCategoria = false;
    	
    	//Busca Descrição da Categoria
    	DB banco = new DB(contexto);
    	SQLiteDatabase db= banco.getReadableDatabase();
    	Cursor cursor  = db.query("TB_LANCAMENTOS" // Tabela
    			        , new String[]{"COD_LANCAMENTO"} // Colunas
    			        , "COD_CATEGORIA = ? "
    	                , new String[]{Integer.toString(codigoCategoria)}
    	                , null //"_id"
    	                , null
    	                , "COD_CATEGORIA" // ORDER BY
    	                , "100");
    	
    	while(cursor.moveToNext()){
    		LancamentoPossuiCategoria = true;
    	}
   
    	return LancamentoPossuiCategoria;
    }
    
    
	/* Deletar Categoria */
	public void DeletarCategoria(Context contexto){
		
		/* Pode Excluir Categoria */
	    if (!LancamentoPossuiCategoria(CodCategoria, contexto)){ 
		  DB banco = new DB(contexto);
		  SQLiteDatabase db= banco.getWritableDatabase();
		  String whereClause = "COD_CATEGORIA = ?"; 
		  String[] whereArgs = new String[]{Integer.toString(CodCategoria)};
		  long id = db.delete("TB_CATEGORIA", whereClause, whereArgs);

		  if(id > 0){
		    Toast.makeText(contexto, "Categoria excluida com sucesso!", Toast.LENGTH_SHORT).show();
		  } else{
			Toast.makeText(contexto,"Erro ao excluir o categoria!", Toast.LENGTH_SHORT).show();
		  }
	    } else{
		    Toast.makeText(contexto, "Não é possivel exluir está categoria, pois existe lançamento referenciando!", Toast.LENGTH_SHORT).show();	    	
	    }
	}
}
