package it.drlele08.prelievoordini.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.OrdineController
import it.drlele08.prelievoordini.controller.carrello.CarrelloAdapter
import it.drlele08.prelievoordini.controller.carrello.CarrelloController
import it.drlele08.prelievoordini.controller.carrello.CarrelloDelegate
import it.drlele08.prelievoordini.model.ProdottoCarrello
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class CarrelloFragment : Fragment(),CarrelloDelegate
{
    private fun loadCart()
    {
        CarrelloController().showCart(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),queue,{ list ->
            vettCart=list
            listCart.layoutManager = LinearLayoutManager(requireContext())
            adapter=CarrelloAdapter(vettCart,this,requireContext())
            listCart.adapter=adapter
            if(list.size>0)
            {
                var totale=0.0
                vettCart.map { item->totale+=(item.getQntCart()*item.getPrezzoIvato()) }
                textTotale.text=getString(R.string.totale_cart,totale)
                textTotale.visibility=View.VISIBLE
                btnSvuota.visibility=View.VISIBLE
                btnAcquista.visibility=View.VISIBLE
                animationView.visibility=View.GONE
                textAvviso.visibility=View.GONE
            }
            else
            {
                textTotale.visibility=View.GONE
                btnSvuota.visibility=View.GONE
                btnAcquista.visibility=View.GONE
                animationView.visibility=View.VISIBLE
                textAvviso.visibility=View.VISIBLE
            }
        },{mess ->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }

    private fun deleteItemCart(idArticolo:Int)
    {
        CarrelloController().removeToCart(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),idArticolo,queue,{
            MotionToast.darkToast(requireActivity(),getString(R.string.fatto),getString(R.string.art_el),
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
            loadCart()
        },{mess ->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }

    private fun doAcquisto()
    {
        val dialog=Utilita.setProgressDialog(requireContext(),getString(R.string.attendere))
        dialog.show()
        OrdineController().doOrdine(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),"",queue,{
            dialog.hide()
            loadCart()
            MotionToast.darkToast(requireActivity(),getString(R.string.fatto),getString(R.string.ordine_effettuato),
                MotionToastStyle.SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        },{mess ->
            dialog.hide()
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }

    private fun clearCart()
    {
        val dialog=Utilita.setProgressDialog(requireContext(),getString(R.string.attendere))
        dialog.show()
        CarrelloController().clearCart(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),queue,{
            dialog.hide()
            loadCart()
        },{mess ->
            dialog.hide()
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_carrello, container, false)
    }

    private lateinit var listCart:RecyclerView
    private lateinit var textTotale:TextView
    private lateinit var btnAcquista:Button
    private lateinit var btnSvuota:Button
    private lateinit var vettCart:ArrayList<ProdottoCarrello>
    private lateinit var queue: RequestQueue
    private lateinit var adapter:CarrelloAdapter
    private lateinit var animationView:LottieAnimationView
    private lateinit var textAvviso:TextView
    private lateinit var viewPage:View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        vettCart= ArrayList()
        listCart=view.findViewById(R.id.listItemCart)
        textTotale=view.findViewById(R.id.textCartTot)
        btnAcquista=view.findViewById(R.id.btnCartAcquista)
        btnSvuota=view.findViewById(R.id.btnCartClear)
        viewPage=view
        textAvviso=view.findViewById(R.id.textAvvisoCart)
        animationView=view.findViewById(R.id.animationViewCart)
        queue=Volley.newRequestQueue(requireContext())
        textTotale.visibility=View.GONE
        btnSvuota.visibility=View.GONE
        btnAcquista.visibility=View.GONE
        loadCart()
        btnAcquista.setOnClickListener{
            MaterialDialog(requireContext()).show {
                title(R.string.conferma_ordine)
                message(R.string.conferma_ordine_quest)
                positiveButton(R.string.si) {
                    doAcquisto()
                }
                negativeButton(text = "No")
            }
        }

        btnSvuota.setOnClickListener{
            MaterialDialog(requireContext()).show {
                title(R.string.conferma_eliminazione)
                message(R.string.conferma_eliminazione_quest)
                positiveButton(R.string.si) {
                    clearCart()
                }
                negativeButton(text = "No")
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onItemClick(idProdotto: Int)
    {
        val item=listOf(getString(R.string.visualizza_prodotto),getString(R.string.elimina_dal_carre),getString(R.string.annulla))
        MaterialDialog(requireContext()).show {
            title(R.string.scegli_operazione)
            listItems(items = item) { _, index, _ ->
                if(index==0)
                {
                    val bundle=Bundle()
                    bundle.putInt("idArticolo",idProdotto)
                    Navigation.findNavController(viewPage).navigate(R.id.detailProdottoFragment,bundle)
                }
                else if(index==1)
                {
                    deleteItemCart(idProdotto)
                }
            }
        }
    }
}