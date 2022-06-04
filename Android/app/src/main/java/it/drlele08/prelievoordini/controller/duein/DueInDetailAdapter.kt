package it.drlele08.prelievoordini.controller.duein

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
import it.drlele08.prelievoordini.model.duein.DueInDetail
import java.util.ArrayList

class DueInDetailAdapter(private var list: ArrayList<DueInDetail>,private val context: Context): RecyclerView.Adapter<DueInDetailAdapter.ViewHolder>()
{
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView)
    {
        val textNome: TextView =ItemView.findViewById(R.id.textDetailDueNome)
        val textInfo: TextView =ItemView.findViewById(R.id.textDetailDueInfo)
        val imageProd: ImageView =ItemView.findViewById(R.id.imageDetailDue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_duein_detail,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item=list[position]
        Glide.with(context).load("${Utilita.host}/img/articoli/${item.getIdProdotto()}.jpg").circleCrop().into(holder.imageProd)
        holder.textNome.text=item.getNome()
        holder.textInfo.text="${item.getQntArrivo()}Pz - ${item.getPrezzo()}€"
    }

    override fun getItemCount(): Int
    {
        return list.size
    }
}