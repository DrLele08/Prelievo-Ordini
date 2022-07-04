package it.drlele08.prelievoordini.controller.lettura

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.model.lettura.InfoLettura
import java.util.ArrayList

class LetturaAdapter(private val list:ArrayList<InfoLettura>,private val delegate: LetturaDelegate,private val context: Context): RecyclerView.Adapter<LetturaAdapter.ViewHolder>()
{
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    {
        val textOrdine: TextView =ItemView.findViewById(R.id.textLetturaOrdine)
        val textCliente: TextView =ItemView.findViewById(R.id.textLetturaCliente)
        val textQnt: TextView =ItemView.findViewById(R.id.textLetturaQnt)
        val textData: TextView =ItemView.findViewById(R.id.textLetturaData)
        val progressOrdine: ProgressBar =ItemView.findViewById(R.id.progressBarLettura)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_lettura,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item= list[position]
        holder.textOrdine.text=context.getString(R.string.ordine_num,item.getIdOrdine())
        holder.textCliente.text=item.getNomeCliente()
        holder.textQnt.text="${item.getPzEvasi()}/${item.getPzTotali()}"
        holder.textData.text="${item.getData()} ${item.getOrario()}"
        holder.progressOrdine.max=item.getPzTotali()
        holder.progressOrdine.progress=item.getPzEvasi()
        holder.itemView.setOnClickListener{
            delegate.onItemClick(item)
        }
    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}