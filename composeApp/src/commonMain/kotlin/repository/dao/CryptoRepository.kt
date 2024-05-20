package repository.dao

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CryptoRepository {

    @Insert
    fun saveLocal(){}

}