package com.pog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class DBItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    var Name: String,
    @ColumnInfo(name = "amount")
    var Amount: Int,
    @ColumnInfo(name = "unit")
    var Unit: String,
    @ColumnInfo(name = "isbought")
    var IsBought: String
)