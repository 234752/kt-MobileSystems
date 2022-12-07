package com.pog

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ListViewModel : AppCompatActivity() {

    private var itemList: ArrayList<ListItem>? = null
    private lateinit var listView: ListView
    private lateinit var itemsTable: ItemsTable
    private lateinit var addButton: Button
    private var newItemName = "error"
    private var newItemAmount = 1
    private var newItemUnit = "pieces"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view)
        title = "YOUR GROCERY LIST"

        listView = findViewById<View>(R.id.itemListView) as ListView
        itemList = ArrayList<ListItem>()
        itemList!!.add(ListItem("it1", 1, "pieces"))
        itemList!!.add(ListItem("it2", 26564, "kg"))
        itemList!!.add(ListItem("it3", 359, "ml"))
        itemsTable = ItemsTable(itemList!!, applicationContext)
        listView.adapter = itemsTable
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val item: ListItem = itemList!![position] as ListItem
            item.IsBought = !item.IsBought
            itemsTable.notifyDataSetChanged()
        }

        addButton = findViewById<Button>(R.id.addButton) as Button
        addButton.setOnClickListener {

            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)

            val view = LinearLayout(this)
            view.orientation = LinearLayout.VERTICAL

            val itemNameLabel = TextView(this)
            val itemName = EditText(this)
            val itemAmountLabel = TextView(this)
            val itemAmount = EditText(this)
            val itemUnitLabel = TextView(this)
            val itemUnit = EditText(this)
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
            view.addView(itemAmount)
            view.addView(itemUnit)

            itemAmount.setText("1")
            itemUnit.setText("pieces")
            builder.setView(view)

            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->
                        newItemName = itemName.text.toString()
                        newItemAmount = itemAmount.text.toString().toInt()
                        newItemUnit = itemUnit.text.toString()
                        itemList!!.add(ListItem(newItemName, newItemAmount, newItemUnit, false))
                        itemsTable.notifyDataSetChanged()
                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()

        }

    }



}
