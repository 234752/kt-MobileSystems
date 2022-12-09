package com.pog

import android.app.Application
import androidx.room.Room

class DBApp : Application(){
    val database: ItemDatabase by lazy { ItemDatabase.getDatabase(applicationContext) }
}