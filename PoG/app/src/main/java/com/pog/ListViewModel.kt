package com.pog

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.InputType
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList


class ListViewModel : AppCompatActivity() {

    private var itemList: ArrayList<ListItem>? = null
    private lateinit var listView: ListView
    private lateinit var itemsTable: ItemsTable
    private lateinit var addButton: Button
    private lateinit var micButton: Button
    private var newItemName = "error"
    private var newItemAmount = 1
    private var newItemUnit = "pieces"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view)
        title = "YOUR GROCERY LIST"

        listView = findViewById<View>(R.id.itemListView) as ListView
        itemList = ArrayList<ListItem>()
        itemsTable = ItemsTable(itemList!!, this)
        listView.adapter = itemsTable

        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val item: ListItem = itemList!![position] as ListItem

            //item.IsBought = !item.IsBought
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
            view.addView(itemAmountLabel)
            view.addView(itemAmount)
            view.addView(itemUnitLabel)
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

        micButton = findViewById<Button>(R.id.micButton) as Button
        micButton.setOnClickListener {

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            }
            // This starts the activity and populates the intent with the speech text.
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0) ?: ""
                }

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
            view.addView(itemAmountLabel)
            view.addView(itemAmount)
            view.addView(itemUnitLabel)
            view.addView(itemUnit)

            itemName.setText(spokenText)
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
        super.onActivityResult(requestCode, resultCode, data)
    }
}
