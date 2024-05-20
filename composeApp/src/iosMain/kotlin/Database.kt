import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory
import repository.database.AppDatabase
import kotlin.reflect.cast
import repository.database.instantiateImpl

//import repository.database.instantiateImpl
fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/crypto.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory =  { AppDatabase::class.instantiateImpl() }
        //factory = {AppDatabase::class.cast(null)}
    )
}