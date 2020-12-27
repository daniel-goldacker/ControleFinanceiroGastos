package br.com.minhasClasses;

import java.io.Serializable;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import br.com.bancoDados.DB;

public class Lancamentos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public int CodLancamento;
	public String TipoLancamento;
	public int CodCategoria;
	public String DesLancamento;
	public float ValorLancamento;
	public String DataLancamento;
	public String Mes;
	public String Ano;
	
	public Lancamentos(int CodLancamento, String TipoLancamento, int CodCategoria, String DesLancamento, float ValorLancamento, String DataLancamento, String Mes, String Ano){
		this.CodLancamento = CodLancamento;
		this.TipoLancamento = TipoLancamento;
		this.CodCategoria = CodCategoria;
		this.DesLancamento = DesLancamento;
		this.ValorLancamento = ValorLancamento;
		this.DataLancamento = DataLancamento;
		this.Mes = Mes;
		this.Ano = Ano;
	}
	
	/* Inserir Lançamentos */
	public void InserirLancamentos(Context Contexto) {
		//Insert		
		DB banco = new DB(Contexto);
		SQLiteDatabase db = banco.getWritableDatabase();
		ContentValues content = new ContentValues();
		
		content.put("TIPO_LANCAMENTO", this.TipoLancamento);
		content.put("COD_CATEGORIA", this.CodCategoria);
		content.put("DES_LANCAMENTO", this.DesLancamento);
		content.put("VALOR_LANCAMENTO", this.ValorLancamento);
		content.put("DATA_LANCAMENTO", this.DataLancamento);
		content.put("MES", this.Mes);
		content.put("ANO", this.Ano);
		long id = db.insert("TB_LANCAMENTOS", null, content);

		if (id > 0) {
			Toast.makeText(Contexto, "Lançamento inserido com sucesso! ", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(Contexto, "Erro ao inserir lancamento!", Toast.LENGTH_SHORT).show();
		}
	}
	
	/* Buscar Todos os Lançamentos */
	public ArrayList<Lancamentos> BuscarLancamentosMesAtual(Context Contexto, String Mes, String Ano){
		
		ArrayList<Lancamentos> ArrayLancamentos = new ArrayList<Lancamentos>();
		Lancamentos dadosLancamentos; 
		
		//Inicia a busca na base SQLite
		DB banco = new DB(Contexto);
		SQLiteDatabase db= banco.getReadableDatabase();
		Cursor cursor  = db.query("TB_LANCAMENTOS" // Tabela
			        , new String[]{"COD_LANCAMENTO", "TIPO_LANCAMENTO", "COD_CATEGORIA", "DES_LANCAMENTO", "VALOR_LANCAMENTO", "DATA_LANCAMENTO", "MES", "ANO"} // Colunas
			        , "MES = ? and ANO = ?"
                    , new String[]{Mes, Ano}
                    , null //"_id"
                    , null
                    , "DATA_LANCAMENTO DESC" // ORDER BY
                    , "100");
		
		while(cursor.moveToNext()){
			/* Adiciona os Contatos */
			dadosLancamentos = new Lancamentos(cursor.getInt(cursor.getColumnIndex("COD_LANCAMENTO")), cursor.getString(cursor.getColumnIndex("TIPO_LANCAMENTO")), 
					                           cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA")), cursor.getString(cursor.getColumnIndex("DES_LANCAMENTO")), 
					                           cursor.getFloat(cursor.getColumnIndex("VALOR_LANCAMENTO")), cursor.getString(cursor.getColumnIndex("DATA_LANCAMENTO")),
					                           cursor.getString(cursor.getColumnIndex("MES")), cursor.getString(cursor.getColumnIndex("ANO")));
				
			dadosLancamentos.CodLancamento   = cursor.getInt(cursor.getColumnIndex("COD_LANCAMENTO"));
			dadosLancamentos.TipoLancamento  = cursor.getString(cursor.getColumnIndex("TIPO_LANCAMENTO")); 
			dadosLancamentos.CodCategoria    = cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA"));
			dadosLancamentos.DesLancamento   = cursor.getString(cursor.getColumnIndex("DES_LANCAMENTO"));
			dadosLancamentos.ValorLancamento = cursor.getFloat(cursor.getColumnIndex("VALOR_LANCAMENTO"));
			dadosLancamentos.DataLancamento  = cursor.getString(cursor.getColumnIndex("DATA_LANCAMENTO")); 
			dadosLancamentos.Mes             = cursor.getString(cursor.getColumnIndex("MES")); 
			dadosLancamentos.Ano             = cursor.getString(cursor.getColumnIndex("ANO")); 

			
			ArrayLancamentos.add(dadosLancamentos);
		}
		cursor.close();
		return ArrayLancamentos;
	}
	
	/* Buscar Lançamentos conforme filtro */
	public ArrayList<Lancamentos> ConsultarLancamentosFiltro(Context Contexto, int codCategoria, String tipoLancamento, String mes, String ano, boolean considerarData){

		/* Coloca zero nos mêses com 1 digito */
		if (mes.length() == 1){
			mes = "0" + mes;
		}
		
		ArrayList<Lancamentos> ArrayLancamentos = new ArrayList<Lancamentos>();
		Lancamentos dadosLancamentos; 
		
										
		//Inicia a busca na base SQLite
		DB banco = new DB(Contexto);
		SQLiteDatabase db= banco.getReadableDatabase();
		Cursor cursor  = db.query("TB_LANCAMENTOS" // Tabela
			        , new String[]{"COD_LANCAMENTO", "TIPO_LANCAMENTO", "COD_CATEGORIA", "DES_LANCAMENTO", "VALOR_LANCAMENTO", "DATA_LANCAMENTO", "MES", "ANO"} // Colunas
			        , null
                    , null
                    , null //"_id"
                    , null
                    , "DATA_LANCAMENTO" // ORDER BY
                    , "100");
		
		
		while(cursor.moveToNext()){							
			 if (((mes.length() > 0) & (ano.length() > 0)) & 		         
				 (((considerarData) & ((cursor.getString(cursor.getColumnIndex("MES")).equals(mes)) & (cursor.getString(cursor.getColumnIndex("ANO")).equals(ano)))) |
				 ((!considerarData) & (((cursor.getString(cursor.getColumnIndex("MES")).equals(mes)) | (!cursor.getString(cursor.getColumnIndex("MES")).equals(mes)))    & 
					  	                ((cursor.getString(cursor.getColumnIndex("ANO")).equals(ano)) | (!cursor.getString(cursor.getColumnIndex("ANO")).equals(ano)))))) & 					 
				 ((tipoLancamento.equals(cursor.getString(cursor.getColumnIndex("TIPO_LANCAMENTO")))) | (tipoLancamento.equals("Todos"))) &
				 ((cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA")) == codCategoria) | (codCategoria == 99999999))){
				
				/* Consulta os Lançamentos */
				dadosLancamentos = new Lancamentos(cursor.getInt(cursor.getColumnIndex("COD_LANCAMENTO")), cursor.getString(cursor.getColumnIndex("TIPO_LANCAMENTO")), 
					                               cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA")), cursor.getString(cursor.getColumnIndex("DES_LANCAMENTO")), 
	                                               cursor.getFloat(cursor.getColumnIndex("VALOR_LANCAMENTO")), cursor.getString(cursor.getColumnIndex("DATA_LANCAMENTO")),
	                                               cursor.getString(cursor.getColumnIndex("MES")), cursor.getString(cursor.getColumnIndex("ANO")));
									
				dadosLancamentos.CodLancamento   = cursor.getInt(cursor.getColumnIndex("COD_LANCAMENTO"));
				dadosLancamentos.TipoLancamento  = cursor.getString(cursor.getColumnIndex("TIPO_LANCAMENTO")); 
				dadosLancamentos.CodCategoria    = cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA"));
				dadosLancamentos.DesLancamento   = cursor.getString(cursor.getColumnIndex("DES_LANCAMENTO"));
				dadosLancamentos.ValorLancamento = cursor.getFloat(cursor.getColumnIndex("VALOR_LANCAMENTO"));
				dadosLancamentos.DataLancamento  = cursor.getString(cursor.getColumnIndex("DATA_LANCAMENTO"));
				dadosLancamentos.Mes             = cursor.getString(cursor.getColumnIndex("MES")); 
				dadosLancamentos.Ano             = cursor.getString(cursor.getColumnIndex("ANO"));
			
				ArrayLancamentos.add(dadosLancamentos);
		
			}
		}
		cursor.close();
		return ArrayLancamentos;
	}
	
	/* Valor Saldo Anterior ao mês corrente */
	public Float ValorSaldoMesAnteriores(Context Contexto, String Mes, String Ano){
	  float  fValorReceitas, fValorDespesas;
     
	  /* Inicializa Variaveis */
	  fValorReceitas = 0;
	  fValorDespesas = 0;
	    
	  /* Inicia a busca na base SQLite */
	  DB banco = new DB(Contexto);
	  SQLiteDatabase db = banco.getReadableDatabase();
		
	  /* Busca pelas receitas do mes/ano anterior ao atual*/		
	  Cursor cursorReceitas = db.rawQuery("SELECT TIPO_LANCAMENTO, VALOR_LANCAMENTO, MES, ANO FROM TB_LANCAMENTOS", null);			
	  while (cursorReceitas.moveToNext()) {	
	    if (cursorReceitas.getInt(cursorReceitas.getColumnIndex("ANO")) < Integer.parseInt(Ano)){

		  if (cursorReceitas.getString(cursorReceitas.getColumnIndex("TIPO_LANCAMENTO")).equals("Receita")){
			/* Soma o lançamento se for receita */ 
  		    fValorReceitas += cursorReceitas.getFloat(cursorReceitas.getColumnIndex("VALOR_LANCAMENTO"));
		  } else if (cursorReceitas.getString(cursorReceitas.getColumnIndex("TIPO_LANCAMENTO")).equals("Despesa")){
		    /* Soma o lançamento se for despesa */ 
			fValorDespesas += cursorReceitas.getFloat(cursorReceitas.getColumnIndex("VALOR_LANCAMENTO"));				
		  }		 		 
	    } else if ((cursorReceitas.getInt(cursorReceitas.getColumnIndex("ANO")) == Integer.parseInt(Ano)) &
				   (cursorReceitas.getFloat(cursorReceitas.getColumnIndex("MES")) < Float.parseFloat(Mes))){ 		
		  if (cursorReceitas.getString(cursorReceitas.getColumnIndex("TIPO_LANCAMENTO")).equals("Receita")){
		    /* Soma o lançamento se for receita */ 
	  		fValorReceitas += cursorReceitas.getFloat(cursorReceitas.getColumnIndex("VALOR_LANCAMENTO"));
		  } else if (cursorReceitas.getString(cursorReceitas.getColumnIndex("TIPO_LANCAMENTO")).equals("Despesa")){
		    /* Soma o lançamento se for despesa */ 
			fValorDespesas += cursorReceitas.getFloat(cursorReceitas.getColumnIndex("VALOR_LANCAMENTO"));				
		  }
	    }		
	  }
	  return (fValorReceitas - fValorDespesas);
	}
	
	/* Valor Total das Despesas */
	public Float ValorTotalDespesasMesAtual(Context Contexto, String Mes, String Ano){
		String sComandoSQL;
		float  fDespesas = 0;

		//Inicia a busca na base SQLite
		DB banco = new DB(Contexto);
		SQLiteDatabase db = banco.getReadableDatabase();
		Cursor cursorDespesas;
		
		if ((Mes != null) & (Ano!= null)){
		  sComandoSQL = "SELECT ANO, MES, VALOR_LANCAMENTO FROM TB_LANCAMENTOS WHERE TIPO_LANCAMENTO = 'Despesa' AND ANO >= '" + Ano + "'";							
    	  cursorDespesas = db.rawQuery(sComandoSQL, null);
    	  while (cursorDespesas.moveToNext()) {	
    	    if (cursorDespesas.getFloat(cursorDespesas.getColumnIndex("ANO")) > Float.parseFloat(Ano)){
    	    	fDespesas += cursorDespesas.getFloat(cursorDespesas.getColumnIndex("VALOR_LANCAMENTO")); 
    	    } else if (cursorDespesas.getFloat(cursorDespesas.getColumnIndex("MES")) >= Float.parseFloat(Mes)){
    	    	fDespesas += cursorDespesas.getFloat(cursorDespesas.getColumnIndex("VALOR_LANCAMENTO")); 	
    	    }
    	  }
    	  
    	  return fDespesas;
		} else {
			sComandoSQL = "SELECT SUM(VALOR_LANCAMENTO) FROM TB_LANCAMENTOS WHERE TIPO_LANCAMENTO = 'Despesa'";
	    	cursorDespesas = db.rawQuery(sComandoSQL, null);
	    	if (cursorDespesas.moveToFirst()) {
		      return cursorDespesas.getFloat(0);
			}
		}
		return (float) 0;
	}
	
	/* Valor Total das Receita */
	public Float ValorTotalReceitaMesAtual(Context Contexto, String Mes, String Ano){
		String sComandoSQL;
		float  fReceitas = 0;

		//Inicia a busca na base SQLite
		DB banco = new DB(Contexto);
		SQLiteDatabase db = banco.getReadableDatabase();
		Cursor cursorReceitas;
		
		if ((Mes != null) & (Ano!= null)){
		  sComandoSQL = "SELECT ANO, MES, VALOR_LANCAMENTO FROM TB_LANCAMENTOS WHERE TIPO_LANCAMENTO = 'Receita' AND ANO >= '" + Ano + "'";							
		  cursorReceitas = db.rawQuery(sComandoSQL, null);
    	  while (cursorReceitas.moveToNext()) {	
    	    if (cursorReceitas.getFloat(cursorReceitas.getColumnIndex("ANO")) > Float.parseFloat(Ano)){
    	    	fReceitas += cursorReceitas.getFloat(cursorReceitas.getColumnIndex("VALOR_LANCAMENTO")); 
    	    } else if (cursorReceitas.getFloat(cursorReceitas.getColumnIndex("MES")) >= Float.parseFloat(Mes)){
    	    	fReceitas += cursorReceitas.getFloat(cursorReceitas.getColumnIndex("VALOR_LANCAMENTO")); 	
    	    }
    	  }
    	 
    	  return fReceitas;
		} else {
			sComandoSQL = "SELECT SUM(VALOR_LANCAMENTO) FROM TB_LANCAMENTOS WHERE TIPO_LANCAMENTO = 'Receita'";
			cursorReceitas = db.rawQuery(sComandoSQL, null);
	    	if (cursorReceitas.moveToFirst()) {
		      return cursorReceitas.getFloat(0);
			}
		}
		return (float) 0;
	}
	
	/* Deletar Lnaçamento */
	public void DeletarLancamento(Context contexto){
		DB banco = new DB(contexto);
		SQLiteDatabase db= banco.getWritableDatabase();
		String whereClause = "COD_LANCAMENTO = ?"; 
		String[] whereArgs = new String[]{Integer.toString(CodLancamento)};
		long id = db.delete("TB_LANCAMENTOS", whereClause, whereArgs);
		
		if(id > 0){
			Toast.makeText(contexto, "Lançamento excluido com sucesso!", Toast.LENGTH_SHORT).show();
		} else{
			Toast.makeText(contexto,"Erro ao excluir o lançamento!", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	
	/* Valor Total das Despesas */
	public ArrayList<Lancamentos> BuscarTotaisLancamentoPorCategoriaMesAtual(Context Contexto, String Mes, String Ano, String TipoLancamento){
		//Inicia a busca na base SQLite
		DB banco = new DB(Contexto);
		SQLiteDatabase db = banco.getReadableDatabase();
		
		ArrayList<Lancamentos> ArrayLancamentos = new ArrayList<Lancamentos>();
		Lancamentos dadosLancamentos; 
	
        Cursor cursor = db.rawQuery("SELECT COD_CATEGORIA, SUM(VALOR_LANCAMENTO) AS VALOR_TOTAL FROM TB_LANCAMENTOS WHERE TIPO_LANCAMENTO = '" + TipoLancamento + "' AND MES = '" + Mes + "' AND ANO = '" + Ano + "' GROUP BY COD_CATEGORIA", null);
		while(cursor.moveToNext()) {
			
			/* Adiciona os Contatos */
			dadosLancamentos = new Lancamentos(0, "", cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA")), "", 
                                               cursor.getFloat(cursor.getColumnIndex("VALOR_TOTAL")), "",
                                               "", "");
			
			dadosLancamentos.CodCategoria    = cursor.getInt(cursor.getColumnIndex("COD_CATEGORIA"));
			dadosLancamentos.ValorLancamento = cursor.getFloat(cursor.getColumnIndex("VALOR_TOTAL"));
			ArrayLancamentos.add(dadosLancamentos);
		}
		return ArrayLancamentos;
	}
	
}
