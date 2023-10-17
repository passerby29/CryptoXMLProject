package dev.passerby.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.passerby.data.models.db.CoinDbModel
import dev.passerby.data.models.db.FavoriteDbModel

@Dao
interface FavoriteDao {

    @Query("select * from favorites")
    fun getFavoritesList(): LiveData<List<CoinDbModel>>

    @Query("delete from favorites where id = :id")
    suspend fun deleteFavorite(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteItem: FavoriteDbModel)
}