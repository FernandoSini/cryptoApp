import androidx.room.Room
import androidx.room.RoomDatabase
import repository.database.AppDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "crypto.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}