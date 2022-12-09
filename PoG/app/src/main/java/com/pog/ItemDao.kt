package com.pog

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: DBItem)

    @Update
    suspend fun update(item: DBItem)

    @Delete
    suspend fun delete(item: DBItem)

    @Query("SELECT * from item ORDER BY name ASC")
    fun getItems(): Flow<List<DBItem>>

}