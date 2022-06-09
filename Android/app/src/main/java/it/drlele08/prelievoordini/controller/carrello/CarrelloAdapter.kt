package it.drlele08.prelievoordini.controller.carrello

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.model.ProdottoCarrello
import java.util.ArrayList

class CarrelloAdapter(private val list:ArrayList<ProdottoCarrello>,private val delegate: CarrelloDelegate,private val context: Context): RecyclerView.Adapter<CarrelloAdapter.ViewHolder>()
{
    class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView)
    {
        val imageProd:ImageView=ItemView.findViewById(R.id.imageCartProd)
        val textNome:TextView=ItemView.findViewById(R.id.textCartNome)
        val textQnt:TextView=ItemView.findViewById(R.id.textCartQnt)
        val btnDelete:ImageView=ItemView.findViewById(R.id.imageDelItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item= list[position]
        holder.textNome.text=item.getDescrizione()
        holder.textQnt.text=""+item.getQntCart()+" Pz"
        Glide.with(context).load("${Utilita.host}/img/articoli/${item.getIdArticolo()}.jpg").into(holder.imageProd)
        holder.btnDelete.setOnClickListener{
            delegate.onItemClick(item.getIdArticolo())
        }
    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}