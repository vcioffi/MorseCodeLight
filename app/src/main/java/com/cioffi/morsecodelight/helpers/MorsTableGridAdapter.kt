package com.cioffi.morsecodelight.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.cioffi.morsecodelight.R

class MorsTableGridAdapter : BaseAdapter(){

    private val list: List<Pair<Char, String?>> = CharToMorse.getMapMorsCode().keys.map { Pair(it, CharToMorse.getMapMorsCode()[it]) }

    /*
        **** reference source developer.android.com ***

        View getView (int position, View convertView, ViewGroup parent)
            Get a View that displays the data at the specified position in the data set. You can
            either create a View manually or inflate it from an XML layout file. When the View
            is inflated, the parent View (GridView, ListView...) will apply default layout
            parameters unless you use inflate(int, android.view.ViewGroup, boolean)
            to specify a root view and to prevent attachment to the root.
    */
    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{
        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_grid_view,null)

        // Get the custom view widgets reference
        val card = view.findViewById<CardView>(R.id.card_view)

        val tvChar = view.findViewById<TextView>(R.id.tv_txt_char)
        val tvMorsCode = view.findViewById<TextView>(R.id.tv_txt_morse_cose)


        // Display color name on text view
        tvChar.text = list[position].first.toString().uppercase()
        tvMorsCode.text = list[position].second.toString()


        // Set a click listener for card view
        card.setOnClickListener{
        }

        // Finally, return the view
        return view
    }



    /*
        **** reference source developer.android.com ***

        Object getItem (int position)
            Get the data item associated with the specified position in the data set.

        Parameters
            position int : Position of the item whose data we want within the adapter's data set.
        Returns
            Object : The data at the specified position.
    */
    override fun getItem(position: Int): Any? {
        return list[position]
    }



    /*
        **** reference source developer.android.com ***

        long getItemId (int position)
            Get the row id associated with the specified position in the list.

        Parameters
            position int : The position of the item within the adapter's data
                           set whose row id we want.
        Returns
            long : The id of the item at the specified position.
    */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    // Count the items
    override fun getCount(): Int {
        return list.size
    }

}