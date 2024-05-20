package repository.dao


import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserRepository {

    @Insert
    suspend fun saveUser(){

    }
}