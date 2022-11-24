package com.pog

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ListViewModel : AppCompatActivity() {

    private var itemList: ArrayList<ListItem>? = null
    private lateinit var listView: ListView
    private lateinit var itemsTable: ItemsTable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view)
        title = "YOUR GROCERY LIST"
        listView = findViewById<View>(R.id.itemListView) as ListView
        itemList = ArrayList<ListItem>()
        itemList!!.add(ListItem("it1", 1))
        itemList!!.add(ListItem("it2", 2))
        itemList!!.add(ListItem("it3", 3))
        itemsTable = ItemsTable(itemList!!, applicationContext)
        listView.adapter = itemsTable
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val dataModel: ListItem = itemList!![position] as ListItem
            dataModel.IsBought = !dataModel.IsBought
            itemsTable.notifyDataSetChanged()
        }
    }

}
