package com.example.proto.database

import com.example.proto.model.Post
import com.example.proto.model.User
import java.time.ZonedDateTime
import java.util.*

class DataGenerator {

    companion object {

        @Volatile
        private var INSTANCE: DataGenerator? = null

        fun getInstance(): DataGenerator {
            return DataGenerator.INSTANCE ?: synchronized(this) {
                val instance = DataGenerator()
                DataGenerator.INSTANCE = instance
                instance
            }
        }
    }

    private val MAX_NAME_LENGTH = 10
    private val MAX_POST_NUMBER = 300
    private val ACCOUNT_CNT = 50
    private val START_TIME= Date.from(ZonedDateTime.now().minusYears(5).toInstant()).time
    private val END_TIME = Date.from(ZonedDateTime.now().minusYears(1).toInstant()).time
    private val CHAR_POOL = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val WORD_POOL = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
            "eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
            " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
            "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa " +
            "qui officia deserunt mollit anim id est laborum."

    fun generateUsers(): List<User> {
        return MutableList(ACCOUNT_CNT) { index ->
            User(
                name = "John Doe $index",
                displayName = "${randomString()}_$index",
                createdAt = Date((START_TIME..END_TIME).random())
            )
        }
    }

    fun generatePosts(): List<Post> {
        return mutableListOf<Post>().apply {
            (0 until ACCOUNT_CNT).forEach { index ->
                val postCnt = kotlin.random.Random.nextInt(1, MAX_POST_NUMBER)
                addAll(
                    MutableList(postCnt) {
                        val textFrom = kotlin.random.Random.nextInt(0, WORD_POOL.length)
                        val textTo = kotlin.random.Random.nextInt(textFrom, WORD_POOL.length)
                        Post(
                            title = randomString(),
                            body = WORD_POOL.substring(textFrom, textTo),
                            image = null,
                            updatedAt = Date((START_TIME..END_TIME).random()),
                            user = kotlin.random.Random.nextInt(1, ACCOUNT_CNT).toLong()
                        )
                    }
                )
            }
        }
    }

    private fun randomString(): String =
        (1..MAX_NAME_LENGTH)
            .map { kotlin.random.Random.nextInt(0, CHAR_POOL.size) }
            .map(CHAR_POOL::get)
            .joinToString("")

}