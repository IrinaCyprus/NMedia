package ru.netology.nmedia.db

import android.R.bool
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [PostEntity::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract val postDao: PostDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context, AppDb::class.java, "app.db"
            ).allowMainThreadQueries()                         //??????
                .build()
    }

//    open fun DeleteDatabase(): bool? {
//        val dbName = "app.db"
//        val documentDirectoryPath: Unit =
//            System.Environment.GetFolderPath(System.Environment.SpecialFolder.Personal)
//        val path: Unit = Path.Combine(documentDirectoryPath, dbName)
//        return Android.Database.Sqlite.SQLiteDatabase.DeleteDatabase(File(path))
//    }
}