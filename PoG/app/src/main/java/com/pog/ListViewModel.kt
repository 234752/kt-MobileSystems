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
import androidx.appcompat.app.AppCompatActivity


class ListViewModel : AppCompatActivity() {

    private var itemList: ArrayList<ListItem>? = null
    private lateinit var listView: ListView
    private lateinit var itemsTable: ItemsTable
    private lateinit var addButton: Button
    private var newItemName = "not pog"
    private var newItemAmount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view)
        title = "YOUR GROCERY LIST"

        listView = findViewById<View>(R.id.itemListView) as ListView
        itemList = ArrayList<ListItem>()
        itemList!!.add(ListItem("it1", 1))
        itemList!!.add(ListItem("it2", 26564))
        itemList!!.add(ListItem("it3", 359))
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
            builder.setTitle("Enter the name of item:")


            val view = LinearLayout(this)
            view.orientation = LinearLayout.VERTICAL


            val itemName = EditText(this)
            val itemAmount = EditText(this)
            val itemUnit = EditText(this)
            itemName.setRawInputType(InputType.TYPE_CLASS_TEXT)
            itemAmount.setRawInputType(InputType.TYPE_CLASS_NUMBER)
            itemUnit.setRawInputType(InputType.TYPE_CLASS_TEXT)
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
                        itemList!!.add(ListItem(newItemName, newItemAmount))
                        itemsTable.notifyDataSetChanged()

                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()

        }

    }



}
