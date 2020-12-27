package br.com.bancoDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB extends SQLiteOpenHelper{
	
	public DB( Context contexto ) {
		super( contexto
		     , "ControleFinanceiroGastos"
			 , null
			 , 2 ); 
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {   
        /* Cria Tabela de Categorias */	
        db.execSQL("CREATE TABLE TB_CATEGORIA(" +
        		      "COD_CATEGORIA INTEGER PRIMARY KEY AUTOINCREMENT," +
        		      "DES_CATEGORIA TEXT)");
        
        /* Inserir Dados na Tabela Categorias */	
        db.execSQL("INSERT INTO TB_CATEGORIA(DES_CATEGORIA) VALUES ('Alimentação')");
        db.execSQL("INSERT INTO TB_CATEGORIA(DES_CATEGORIA) VALUES ('Lazer')");
        db.execSQL("INSERT INTO TB_CATEGORIA(DES_CATEGORIA) VALUES ('Dependentes')");
        db.execSQL("INSERT INTO TB_CATEGORIA(DES_CATEGORIA) VALUES ('Transporte')");
        
        /* Cria Tabela de Lançamentos */
        db.execSQL("CREATE TABLE TB_LANCAMENTOS("+
        		      "COD_LANCAMENTO INTEGER PRIMARY KEY AUTOINCREMENT,"+
        		      "TIPO_LANCAMENTO TEXT,"+
        		      "COD_CATEGORIA INTEGER," +
        		      "DES_LANCAMENTO TEXT," +
        		      "VALOR_LANCAMENTO DECIMAL," +
        		      "DATA_LANCAMENTO  TEXT," +
        		      "ANO TEXT," +
        		      "MES TEXT," +
        		      "FOREIGN KEY(COD_CATEGORIA) REFERENCES TB_CATEGORIA(COD_CATEGORIA))");
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE tb_usuario");
		onCreate(db);
	}
}
