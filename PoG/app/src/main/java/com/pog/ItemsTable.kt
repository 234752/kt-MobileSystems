package com.pog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Paint
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ItemsTable(val ItemsList: ArrayList<ListItem>, mContext: Context) :
    ArrayAdapter<ListItem>(mContext, R.layout.table_row_view, ItemsList) {
    public class Row {
        lateinit var rowTextView: TextView
        lateinit var rowAmountView: TextView
        lateinit var rowCheckBox: CheckBox
        lateinit var rowEditButton: Button
    }
    override fun getView(position: Int, inputView: View?, parent: ViewGroup): View {
        var convertView = inputView
        val row: Row
        val result: View
        if (convertView == null) {
            row = Row()
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.table_row_view, parent, false)
            row.rowTextView = convertView.findViewById(R.id.rowTextView)
            row.rowAmountView = convertView.findViewById(R.id.rowAmountView)
            row.rowCheckBox = convertView.findViewById(R.id.rowCheckBox)
            row.rowCheckBox.setOnCheckedChangeListener { _, isChecked ->

                val i = ItemsList[position]
                i.IsBought = isChecked

                if(isChecked) {
                    row.rowTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    row.rowTextView.paintFlags = 0
                }
            }
            row.rowEditButton = convertView.findViewById(R.id.rowEditButton)
            row.rowEditButton.setOnClickListener {

                val i = ItemsList[position]

                val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
                val view = LinearLayout(context)
                view.orientation = LinearLayout.VERTICAL

                val itemNameLabel = TextView(context)
                val itemName = EditText(context)
                val itemAmountLabel = TextView(context)
                val itemAmount = EditText(context)
                val itemUnitLabel = TextView(context)
                val itemUnit = EditText(context)
                itemName.setRawInputType(InputType.TYPE_CLASS_TEXT)
                itemAmount.setRawInputType(InputType.TYPE_CLASS_NUMBER)
                itemUnit.setRawInputType(InputType.TYPE_CLASS_TEXT)
                itemNameLabel.text = "Name of new item: "
                itemAmountLabel.text = "Amount: "
                itemUnitLabel.text = "Unit: "
                itemNameLabel.textAlignment = View.TEXT_ALIGNMENT_CENTER
                itemName.textAlignment = View.TEXT_ALIGNMENT_CENTER
                itemAmountLabel.textAlignment = View.TEXT_ALIGNMENT_CENTER
                itemAmount.textAlignment = View.TEXT_ALIGNMENT_CENTER
                itemUnitLabel.textAlignment = View.TEXT_ALIGNMENT_CENTER
                itemUnit.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.addView(itemNameLabel)
                view.addView(itemName)
                view.addView(itemAmountLabel)
                view.addView(itemAmount)
                view.addView(itemUnitLabel)
                view.addView(itemUnit)

                itemName.setText(i.Name)
                itemAmount.setText(i.Amount.toString())
                itemUnit.setText(i.Unit)
                builder.setView(view)

                builder.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        i.Name = itemName.text.toString()
                        i.Amount = itemAmount.text.toString().toInt()
                        i.Unit = itemUnit.text.toString()
                        this.notifyDataSetChanged()
                    })
                builder.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()
            }

            result = convertView
            convertView.tag = row
        } else {
            row = convertView.tag as Row
            result = convertView
        }
        val item: ListItem = ItemsList[position]
        row.rowTextView.text = item.Name
        row.rowAmountView.text = "${item.Amount} [${item.Unit}]"
        row.rowCheckBox.isChecked = item.IsBought

        return result
    }
}