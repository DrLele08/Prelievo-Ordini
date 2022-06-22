package it.drlele08.prelievoordini.view.prodotto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import it.drlele08.prelievoordini.R
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.prodotto.ProdottoAdapter
import it.drlele08.prelievoordini.controller.prodotto.ProdottoController
import it.drlele08.prelievoordini.controller.prodotto.ProdottoDelegate
import it.drlele08.prelievoordini.model.Prodotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import javax.crypto.Cipher
import kotlin.math.max


class ProdottiFragment : Fragment(),ProdottoDelegate
{
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode==454)
        {
            val uri: Uri = data?.data!!
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val image= InputImage.fromFilePath(requireContext(),uri)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    if(visionText.text.isNotEmpty())
                    {
                        var text=""
                        for(block in visionText.textBlocks)
                        {
                            for(line in block.lines)
                            {
                                if(line.text.length>3)
                                    text+="${line.text.trim()} "
                            }
                        }
                        getProdTags(text)
                    }
                    else
                    {
                        MotionToast.darkToast(requireActivity(),getString(R.string.errore),getString(R.string.no_corr_imm),
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                    }
                }
                .addOnFailureListener { e ->
                    MotionToast.darkToast(requireActivity(),getString(R.string.errore),e.toString(),
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                }
        }
        else if (resultCode == Activity.RESULT_OK && requestCode==455)
        {
            val uri: Uri = data?.data!!
            val scanner = BarcodeScanning.getClient()
            val image= InputImage.fromFilePath(requireContext(),uri)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if(barcodes.size>0)
                    {
                        val bar=barcodes[0].rawValue!!
                        ProdottoController().getProdottoByEan(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),bar,queue,{prodotto, _ ->
                            val b=Bundle()
                            b.putInt("idArticolo",prodotto.getIdArticolo())
                            Navigation.findNavController(viewGlo).navigate(R.id.detailProdottoFragment,b)
                        },{err ->
                            MotionToast.darkToast(requireActivity(),getString(R.string.errore),err,
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                        })
                    }
                    else
                    {
                        MotionToast.darkToast(requireActivity(),getString(R.string.errore),getString(R.string.no_ean_trovato),
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                    }
                }
                .addOnFailureListener {
                    MotionToast.darkToast(requireActivity(),getString(R.string.errore),it.toString(),
                        MotionToastStyle.ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                }
        }
    }

    private fun getProdTags(tags:String)
    {
        pagina=0
        ProdottoController().getProdottiByTags(Utilita.user!!.getIdUtente(),Utilita.user!!.getTokenAuth(),tags,queue,{vettProd ->
            listProdotti=vettProd
            adapter.updateVett(listProdotti)
        },{mess->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
    private fun showFilter()
    {
        MaterialDialog(requireContext(), BottomSheet()).show {
            title(R.string.filtri)
            customView(R.layout.filter_modal, scrollable = true, horizontalPadding = true)
            positiveButton(R.string.applica) { dialog ->
                val spinnerCategoria:Spinner=dialog.getCustomView().findViewById(R.id.spinnerFilterCategoria)
                val spinnerOrdina:Spinner=dialog.getCustomView().findViewById(R.id.spinnerFilterOrdinamento)
                val selectedCategoria=spinnerCategoria.selectedItemId.toInt()
                val selectedOrdina=spinnerOrdina.selectedItemId.toInt()
                categoria=selectedCategoria
                filtro=selectedOrdina+1
                pagina=0
                listProdotti.clear()
                getProdotti()
            }
            negativeButton(R.string.annulla)
        }
    }

    private fun getProdotti()
    {
        var idUtente=-1
        var tokenAuth=""
        if(Utilita.user != null)
        {
            idUtente=Utilita.user!!.getIdUtente()
            tokenAuth=Utilita.user!!.getTokenAuth()
        }
        ProdottoController().getProdotti(idUtente,tokenAuth,pagina,filtro,desc,categoria,queue,{ list->
            maxPagina = list.size <= 0
            for(item in list)
            {
                listProdotti.add(item)
            }
            adapter.updateVett(listProdotti)
        },{mess->
            MotionToast.darkToast(requireActivity(),getString(R.string.errore),mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }

    private fun showTextRec()
    {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start(454)
    }

    private fun scanBarcode()
    {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start(455)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View
    {
        return inflater.inflate(R.layout.fragment_prodotti,container,false)
    }
    private lateinit var viewProdotti:RecyclerView
    private lateinit var btnFilter:ImageView
    private lateinit var btnEan:ImageView
    private lateinit var btnTextRec:ImageView
    private lateinit var textNome:EditText
    private lateinit var listProdotti:ArrayList<Prodotto>
    private lateinit var queue: RequestQueue
    private lateinit var adapter:ProdottoAdapter
    private lateinit var viewGlo:View
    private var pagina=0
    private var filtro=6
    private var desc=""
    private var categoria=0
    private var maxPagina=false
    private lateinit var layoutManager: LinearLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        pagina=0
        filtro=6
        desc=""
        categoria=0
        maxPagina=false
        viewProdotti=view.findViewById(R.id.listProdotti)
        btnFilter=view.findViewById(R.id.btnFiltroProd)
        btnEan=view.findViewById(R.id.btnEanProd)
        viewGlo=view
        btnTextRec=view.findViewById(R.id.btnTextRecProd)
        textNome=view.findViewById(R.id.inputDescProd)
        queue=Volley.newRequestQueue(requireContext())
        layoutManager=LinearLayoutManager(requireContext())
        viewProdotti.layoutManager = layoutManager
        listProdotti= ArrayList()
        adapter=ProdottoAdapter(listProdotti,this,requireContext())
        viewProdotti.adapter=adapter
        if(Utilita.user != null)
        {
            btnTextRec.setOnClickListener{
                showTextRec()
            }
            btnEan.setOnClickListener{
                scanBarcode()
            }
        }
        else
        {
            btnTextRec.visibility=View.GONE
            btnEan.visibility=View.GONE
        }
        btnFilter.setOnClickListener{
            showFilter()
        }

        getProdotti()
        textNome.doOnTextChanged { text, _, _, _ ->
            if(text.toString()!=desc)
            {
                pagina=0
                listProdotti.clear()
                desc = if(text.toString().length>2)
                    text.toString()
                else
                    ""
                getProdotti()
            }
        }

        viewProdotti.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val maxVisible=layoutManager.findLastVisibleItemPosition()
                val total = adapter.itemCount
                if(total>5)
                {
                    if ((pastVisibleItem>(total-8) || (total-maxVisible)<3) && !maxPagina)
                    {
                        pagina++
                        getProdotti()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
    override fun onProductClick(prodotto: Prodotto)
    {
        if(Utilita.user != null)
        {
            val b=Bundle()
            b.putInt("idArticolo",prodotto.getIdArticolo())
            Navigation.findNavController(viewGlo).navigate(R.id.detailProdottoFragment,b)
        }
    }

}