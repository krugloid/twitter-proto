package com.example.proto.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proto.database.TestData.posts
import com.example.proto.database.TestData.users
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

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "proto_database"
                ).addCallback(DatabasePrepopulateCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    class DatabasePrepopulateCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { appDatabase ->
                scope.launch {
                    // populate db
                    users.forEach {
                        appDatabase.userDao().addUser(it)
                    }
                    posts.forEach {
                        appDatabase.postDao().addPost(it)
                    }
                    // FIXME: the ugly way to cache a current user
                    appDatabase.userDao().updateCurrentUser(1L)
                }
            }
        }
    }
}

// TODO: replace with the generated data
object TestData {
    val user1 = User(
        name = "Anna",
        displayName = "anna"
    )
    val user2 = User(
        name = "Ben",
        displayName = "ben"
    )
    val user3 = User(
        name = "John",
        displayName = "johnny"
    )
    val post1 = Post(
        title = "Post 1",
        user = 1
    )
    val post2 = Post(
        title = "Post 2",
        user = 1
    )
    val post3 = Post(
        title = "Post 3",
        user = 2
    )
    val post4 = Post(
        title = "Post 4",
        user = 3
    )
    val post5 = Post(
        title = "Post 5",
        user = 2
    )
    val posts = listOf(post1, post2, post3, post4, post5)
    val users = listOf(user1, user2, user3)
}