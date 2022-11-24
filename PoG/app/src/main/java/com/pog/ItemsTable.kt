package com.pog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import java.util.*

class ItemsTable(val ItemsList: ArrayList<ListItem>, mContext: Context) :
    ArrayAdapter<ListItem>(mContext, R.layout.items_table_view, ItemsList) {
    private class Row {
        lateinit var rowTextView: TextView
        lateinit var rowCheckBox: CheckBox
    }
    override fun getView(position: Int, inputView: View?, parent: ViewGroup): View {
        var convertView = inputView
        val row: Row
        val result: View
        if (convertView == null) {
            row = Row()
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.items_table_view, parent, false)
            row.rowTextView = convertView.findViewById(R.id.rowTextView)
            row.rowCheckBox = convertView.findViewById(R.id.rowCheckBox)
            result = convertView
            convertView.tag = row
        } else {
            row = convertView.tag as Row
            result = convertView
        }
        val item: ListItem = ItemsList[position]
        row.rowTextView.text = item.Name
        row.rowCheckBox.isChecked = item.IsBought
        return result
    }
}