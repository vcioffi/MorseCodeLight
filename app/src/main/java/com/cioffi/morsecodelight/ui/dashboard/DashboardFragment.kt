package com.cioffi.morsecodelight.ui.dashboard

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cioffi.morsecodelight.databinding.FragmentDashboardBinding
import com.cioffi.morsecodelight.helpers.CharToMorse
import com.cioffi.morsecodelight.helpers.MorsTableGridAdapter


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val griView =  binding.gridViewMorsTable        // Get an instance of base adapter
        val adapter = MorsTableGridAdapter()
        val startTranslate: Button = binding.btnTranslateToMors
        val textTranslated: TextView = binding.txtTranslatedText
        textTranslated.setMovementMethod(ScrollingMovementMethod())

        startTranslate.setOnClickListener {
            var textToEncode: EditText = binding.editTextTextPhraseToMors
            val phraseToTra = textToEncode.text.toString()
            try {
                textTranslated.setText(CharToMorse.convertPhraseToMors(phraseToTra))
            }catch (illE : IllegalArgumentException ){
                Toast.makeText(context, "${illE.message}", Toast.LENGTH_LONG).show()
            }
            textTranslated.setMovementMethod(ScrollingMovementMethod())
        }



        // Set the grid view adapter
        griView.adapter = adapter

        // Configure the grid view
        griView.numColumns = 1
        griView.horizontalSpacing = 15
        griView.verticalSpacing = 15
        griView.stretchMode = GridView.STRETCH_COLUMN_WIDTH


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}