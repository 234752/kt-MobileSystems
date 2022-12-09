package com.pog

data class ListItem(
    var Name: String,
    var Amount: Int,
    var Unit: String,
    var IsBought: Boolean = false,
)