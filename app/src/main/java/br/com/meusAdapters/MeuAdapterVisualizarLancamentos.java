package br.com.meusAdapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.minhasClasses.Categoria;
import br.com.minhasClasses.Lancamentos;
import com.example.saveMoney.R;


public class MeuAdapterVisualizarLancamentos extends BaseAdapter{
	ArrayList<Lancamentos> arrListaLancamentos;
	Context            contexto;
	int tipoLista; //0 listado em consulta de categorias, 1 listado por Spinner		
	Categoria categoria;
	
	public MeuAdapterVisualizarLancamentos(Context _contexto, ArrayList<Lancamentos> _arrListaLancamentos) {
		arrListaLancamentos = _arrListaLancamentos;
		contexto            = _contexto;
	}

	@Override
	public int getCount() {
		return arrListaLancamentos.size();
	}

	@Override
	public Object getItem(int position) {
		return arrListaLancamentos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return arrListaLancamentos.get(position).hashCode();
	}

	@Override
	@SuppressLint("ViewHolder") 
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		
		View linhaAdaptada;
		TextView  tvTipoLancamento, tvDescLancamento, tvCategoriaLan, tvDataLancamento, tvValorLancamento;
		TextView  tvTipLan, tvDescLan, tvCatLan, tvDatLan, tvValLan;
		ImageView ivTipoLancamento;
		
		linhaAdaptada = inflater.inflate(R.layout.linha_para_visualizar_lancamentos, parent , false);
		/* Atribui os objetos da tela as variáveis abaixo - Campos com informação Váriavel */
		tvTipoLancamento  = (TextView) linhaAdaptada.findViewById(R.id.tvTipoLancamento);
        tvDescLancamento  = (TextView) linhaAdaptada.findViewById(R.id.tvImagemValorLancamento);
        tvCategoriaLan    = (TextView) linhaAdaptada.findViewById(R.id.tvCategoria);
        tvDataLancamento  = (TextView) linhaAdaptada.findViewById(R.id.tvDataLancamento);
        tvValorLancamento = (TextView) linhaAdaptada.findViewById(R.id.tvValorLancamento);
        ivTipoLancamento  = (ImageView) linhaAdaptada.findViewById(R.id.ivTipoLancamento);
        
        /* Atribui os objetos da tela as variáveis abaixo - Campos com informação Fixal */
        tvTipLan  = (TextView) linhaAdaptada.findViewById(R.id.tvTituloTipoLancamento);
        tvDescLan = (TextView) linhaAdaptada.findViewById(R.id.tvDescLan);
        tvCatLan  = (TextView) linhaAdaptada.findViewById(R.id.tvCatLan);
        tvDatLan  = (TextView) linhaAdaptada.findViewById(R.id.tvDatLan);
        tvValLan  = (TextView) linhaAdaptada.findViewById(R.id.tvValorLan);
        
        /* Buscar Categoria por Código */
		categoria = new Categoria(0, "");
		String sDescCategoria = categoria.BuscarCategoriaPorCodigo(arrListaLancamentos.get(position).CodCategoria, contexto);
		 
        tvTipoLancamento.setText(arrListaLancamentos.get(position).TipoLancamento);
        tvDescLancamento.setText(arrListaLancamentos.get(position).DesLancamento);
        tvValorLancamento.setText(Float.toString(arrListaLancamentos.get(position).ValorLancamento));
        tvDataLancamento.setText(arrListaLancamentos.get(position).DataLancamento);
        tvCategoriaLan.setText(sDescCategoria);
        
        /* Se o tipo de lancamento for Despesa coloca o texto como vermelho */
        if (tvTipoLancamento.getText().equals("Despesa")){          
        	tvTipoLancamento.setTextColor(Color.parseColor("#ff0000"));
        	tvDescLancamento.setTextColor(Color.parseColor("#ff0000"));
        	tvValorLancamento.setTextColor(Color.parseColor("#ff0000"));
        	tvDataLancamento.setTextColor(Color.parseColor("#ff0000"));
        	tvCategoriaLan.setTextColor(Color.parseColor("#ff0000"));
        	tvTipLan.setTextColor(Color.parseColor("#ff0000"));
        	tvDescLan.setTextColor(Color.parseColor("#ff0000"));
        	tvCatLan.setTextColor(Color.parseColor("#ff0000"));
        	tvDatLan.setTextColor(Color.parseColor("#ff0000"));
        	tvValLan.setTextColor(Color.parseColor("#ff0000"));
        	ivTipoLancamento.setImageResource(R.drawable.ic_despesa);
        } else if(tvTipoLancamento.getText().equals("Receita")) {
        	/* Se o tipo de lancamento for Receita coloca o texto como azul */
          	tvTipoLancamento.setTextColor(Color.parseColor("#4a73bd"));
        	tvDescLancamento.setTextColor(Color.parseColor("#4a73bd"));
        	tvValorLancamento.setTextColor(Color.parseColor("#4a73bd"));
        	tvDataLancamento.setTextColor(Color.parseColor("#4a73bd"));
        	tvCategoriaLan.setTextColor(Color.parseColor("#4a73bd"));
        	tvTipLan.setTextColor(Color.parseColor("#4a73bd"));
        	tvDescLan.setTextColor(Color.parseColor("#4a73bd"));
        	tvCatLan.setTextColor(Color.parseColor("#4a73bd"));
        	tvDatLan.setTextColor(Color.parseColor("#4a73bd"));
        	tvValLan.setTextColor(Color.parseColor("#4a73bd"));
        	ivTipoLancamento.setImageResource(R.drawable.ic_receita);
        }
            
        
        
		return linhaAdaptada; 
	}
}
