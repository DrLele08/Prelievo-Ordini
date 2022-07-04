package it.drlele08.prelievoordini.controller.duein

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.model.duein.DueIn
import java.util.ArrayList

class DueInAdapter(private var list: ArrayList<DueIn>, private val delegate: DueInDelegate,private val context: Context): RecyclerView.Adapter<DueInAdapter.ViewHolder>()
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
        holder.textId.text=context.getString(R.string.ordine_num,item.getIdDue())
        holder.textData.text=context.getString(R.string.data_arrivo,item.getDataArrivo())
        holder.textDesc.text=item.getDescrizione()
        holder.textNum.text=context.getString(R.string.qnt_prod_due,item.getNumProdotti())
        holder.itemView.setOnClickListener{
            delegate.onItemClick(item)
        }
    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}