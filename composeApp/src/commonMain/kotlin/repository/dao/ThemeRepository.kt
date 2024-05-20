package repository.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface ThemeRepository {
    @Update
    suspend fun changeTheme(value: Boolean): Boolean
    @Query("")
    suspend fun getTheme(): Boolean
}