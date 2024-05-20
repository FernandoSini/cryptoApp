package repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import models.CryptoCoin
import models.User
import repository.dao.CryptoRepository
import repository.dao.ThemeRepository
import repository.dao.UserRepository
import utlis.Converters

@Database(entities = [User::class, CryptoCoin::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserRepository
    abstract fun getCryptoDao(): CryptoRepository
    //abstract fun getTheme(): ThemeRepository
}
/*
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getDao(): UserRepository

    companion object {
        @Volatile
        private var Instance: UserDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): UserDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .build().also { Instance = it }
            }

        }
    }
}*/
