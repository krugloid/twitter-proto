package com.example.proto.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proto.model.Post
import com.example.proto.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Post::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope, generator: DataGenerator): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "proto_database"
                ).addCallback(DatabasePrepopulateCallback(scope, generator))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    class DatabasePrepopulateCallback(private val scope: CoroutineScope, private val generator: DataGenerator) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { appDatabase ->
                scope.launch {
                    // populate db
                    appDatabase.userDao().addUser(*generator.generateUsers().toTypedArray())
                    appDatabase.postDao().addPost(*generator.generatePosts().toTypedArray())

                    // FIXME: the ugly way to cache a current user
                    appDatabase.userDao().updateCurrentUser(1L)
                }
            }
        }
    }
}