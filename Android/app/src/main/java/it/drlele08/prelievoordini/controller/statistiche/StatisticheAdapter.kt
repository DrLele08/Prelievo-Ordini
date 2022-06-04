package it.drlele08.prelievoordini.controller.statistiche

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.model.UtenteMini

class StatisticheAdapter(private val list:ArrayList<UtenteMini>,private val delegate: StatisticheDelegate): RecyclerView.Adapter<StatisticheAdapter.ViewHolder>()
{
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    {
        val textNome: TextView =ItemView.findViewById(R.id.textUserStatNome)
        val textDetail: TextView =ItemView.findViewById(R.id.textUserStatDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_user_stat,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item=list[position]
        holder.textNome.text=item.getNome()
        holder.textDetail.setOnClickListener{
            delegate.onItemClick(item)
        }
    }

    override fun getItemCount(): Int
    {
        return list.size
    }

}