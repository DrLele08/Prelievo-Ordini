package it.drlele08.prelievoordini.controller.duein

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.model.duein.DueIn
import java.util.ArrayList

class DueInAdapter(private var list: ArrayList<DueIn>, private val delegate: DueInDelegate): RecyclerView.Adapter<DueInAdapter.ViewHolder>()
{
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    {
        val textId: TextView =ItemView.findViewById(R.id.textDueId)
        val textData: TextView =ItemView.findViewById(R.id.textDueData)
        val textNum: TextView =ItemView.findViewById(R.id.textDueNumProd)
        val textDesc: TextView =ItemView.findViewById(R.id.textDueDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_duein,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item=list[position]
        holder.textId.text="Ordine #${item.getIdDue()}"
        holder.textData.text="Data arrivo: ${item.getDataArrivo()}"
        holder.textDesc.text=item.getDescrizione()
        holder.textNum.text="${item.getNumProdotti()} prodotti"
        holder.itemView.setOnClickListener{
            delegate.onItemClick(item)
        }
    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}