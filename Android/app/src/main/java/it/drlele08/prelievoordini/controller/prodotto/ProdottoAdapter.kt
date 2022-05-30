package it.drlele08.prelievoordini.controller.prodotto

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.Prodotto
import org.w3c.dom.Text
import java.util.ArrayList

class ProdottoAdapter(private var list: ArrayList<Prodotto>,private val delegate: ProdottoDelegate): RecyclerView.Adapter<ProdottoAdapter.ViewHolder>()
{
    @SuppressLint("NotifyDataSetChanged")
    fun updateVett(vett:ArrayList<Prodotto>)
    {
        list=vett
        notifyDataSetChanged()
    }
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    {
        val textNome: TextView =ItemView.findViewById(R.id.textProdNome)
        val textDetail:TextView=ItemView.findViewById(R.id.textProdDetail)
        val imageProd:ImageView=ItemView.findViewById(R.id.imageProdItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_prod,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item= list[position]
        holder.textNome.text=item.getDescrizione()
        Picasso.get().load("${Utilita.host}/img/articoli/${item.getIdArticolo()}.jpg").into(holder.imageProd)
        holder.textDetail.text="${item.getPrezzoIvato()}€"
        holder.itemView.setOnClickListener{
            delegate.onProductClick(item)
        }
    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}