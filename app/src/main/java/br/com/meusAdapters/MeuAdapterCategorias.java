package br.com.meusAdapters;

import java.util.ArrayList;

import com.example.saveMoney.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.minhasClasses.Categoria;


public class MeuAdapterCategorias extends BaseAdapter{
	ArrayList<Categoria> arrListaCategoria;
	Context            contexto;
	int tipoLista; //0 listado em consulta de categorias, 1 listado por Spinner			  
	
	public MeuAdapterCategorias(Context _contexto, ArrayList<Categoria> _arrListaCategoria, int _tipoLista) {
		arrListaCategoria = _arrListaCategoria;
		contexto          = _contexto;
		tipoLista		  = _tipoLista;
	}

	@Override
	public int getCount() {
		return arrListaCategoria.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListaCategoria.get(position);
	}

	@Override
	public long getItemId(int position) {
		return arrListaCategoria.get(position).hashCode();
	}

	@Override
	@SuppressLint("ViewHolder") 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		
		View linhaAdaptada;
		TextView  Categoria, DescCategoria;
		if (tipoLista == 0) {
			linhaAdaptada = inflater.inflate(R.layout.linha_para_categorias, parent , false);
			/* Atribui os objetos da tela as variáveis abaixo */
			Categoria     = (TextView) linhaAdaptada.findViewById(R.id.tvCategoria);
			DescCategoria = (TextView) linhaAdaptada.findViewById(R.id.tvDescCategoria);
		} else { //Quando usado no spinner
			linhaAdaptada = inflater.inflate(R.layout.linha_para_categorias_2, parent , false);
			/* Atribui os objetos da tela as variáveis abaixo */
			Categoria     = (TextView) linhaAdaptada.findViewById(R.id.tvCategoria2);
			DescCategoria = (TextView) linhaAdaptada.findViewById(R.id.tvDescCategoria2);
		}
				
		Categoria.setText(Integer.toString(arrListaCategoria.get(position).CodCategoria));
		DescCategoria.setText(arrListaCategoria.get(position).DescCategoria);
		
		return linhaAdaptada; 
	}
}
