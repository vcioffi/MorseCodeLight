package com.cioffi.morsecodelight.ui.dashboard

import android.R
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cioffi.morsecodelight.databinding.FragmentDashboardBinding
import com.cioffi.morsecodelight.helpers.CharToMorse


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

        initMorsTab();

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun initMorsTab() {
        val stk: TableLayout = binding.tableMors
        val tbrow0 = TableRow(this.context)

        val tv0 = TextView(this.context)
        tv0.text = getString(com.cioffi.morsecodelight.R.string.txt_char)
        tv0.setTextColor(Color.WHITE)

        tbrow0.addView(tv0)

        val tv1 = TextView(this.context)
        tv1.text = getString(com.cioffi.morsecodelight.R.string.txt_morse_cose)
        tv1.setTextColor(Color.WHITE)
        tbrow0.addView(tv1)

        stk.addView(tbrow0);

         var mapTabCodes = CharToMorse.getMapMorsCode()

        mapTabCodes.forEach { (key, value) ->
            print("$key : $value")
            val tbrow = TableRow(this.context)
            val tv0 = TextView(this.context)
            tv0.text = "$key"
            tv0.setTextColor(Color.WHITE)
            tv0.gravity = Gravity.CENTER
            tbrow.addView(tv0)

            val tv1 = TextView(this.context)
            tv1.text = "$value"
            tv1.setTextColor(Color.WHITE)
            tv1.gravity = Gravity.CENTER
            tbrow.addView(tv1)

            stk.addView(tbrow)
        }
    }

}