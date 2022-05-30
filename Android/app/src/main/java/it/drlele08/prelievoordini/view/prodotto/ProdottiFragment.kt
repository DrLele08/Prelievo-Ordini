package it.drlele08.prelievoordini.view.prodotto

import android.app.Activity
import android.content.Intent
import android.net.Uri
import it.drlele08.prelievoordini.R

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import it.drlele08.prelievoordini.Utilita
import it.drlele08.prelievoordini.controller.prodotto.ProdottoAdapter
import it.drlele08.prelievoordini.controller.prodotto.ProdottoController
import it.drlele08.prelievoordini.controller.prodotto.ProdottoDelegate
import it.drlele08.prelievoordini.model.Prodotto
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.ArrayList

class ProdottiFragment : Fragment(),ProdottoDelegate
{
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
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
                        MotionToast.darkToast(requireActivity(),"Errore","Nessuna corrispondenza con l'immagine",
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
                    }
                }
                .addOnFailureListener { e ->
                    MotionToast.darkToast(requireActivity(),"Errore",e.toString(),
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
            MotionToast.darkToast(requireActivity(),"Errore",mess,
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
        })
    }
    private fun showFilter()
    {
        MaterialDialog(requireContext(), BottomSheet()).show {

        }
    }

    private fun getProdotti(pagina:Int,filtro:Int,desc:String,categoria:String,queue: RequestQueue)
    {
        var idUtente=-1
        var tokenAuth=""
        if(Utilita.user != null)
        {
            idUtente=Utilita.user!!.getIdUtente()
            tokenAuth=Utilita.user!!.getTokenAuth()
        }
        ProdottoController().getProdotti(idUtente,tokenAuth,pagina,filtro,desc,categoria,queue,{ list->
            listProdotti=list
            adapter.updateVett(listProdotti)
        },{mess->
            MotionToast.darkToast(requireActivity(),"Errore",mess,
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
            .start()
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
    private val filtro=0
    private var desc=""
    private val categoria=""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewProdotti=view.findViewById(R.id.listProdotti)
        btnFilter=view.findViewById(R.id.btnFiltroProd)
        btnEan=view.findViewById(R.id.btnEanProd)
        viewGlo=view
        btnTextRec=view.findViewById(R.id.btnTextRecProd)
        textNome=view.findViewById(R.id.inputDescProd)
        queue=Volley.newRequestQueue(requireContext())
        viewProdotti.layoutManager = LinearLayoutManager(requireContext())
        listProdotti= ArrayList()
        adapter=ProdottoAdapter(listProdotti,this)
        viewProdotti.adapter=adapter
        btnFilter.setOnClickListener{
            showFilter()
        }
        btnTextRec.setOnClickListener{
            showTextRec()
        }
        getProdotti(pagina,filtro,desc,categoria,queue)
        textNome.doOnTextChanged { text, _, _, _ ->
            listProdotti.clear()
            desc = if(text.toString().length>2)
                text.toString()
            else
                ""
            getProdotti(pagina,filtro,desc,categoria,queue)
        }
    }

    override fun onProductClick(prodotto: Prodotto)
    {
        val b=Bundle()
        b.putInt("idArticolo",prodotto.getIdArticolo())
        Navigation.findNavController(viewGlo).navigate(R.id.detailProdottoFragment,b)
    }

}