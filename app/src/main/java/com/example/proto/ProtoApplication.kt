package com.example.proto

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.proto.database.AppDatabase
import com.example.proto.database.DataGenerator
import com.example.proto.di.appModule
import kotlinx.coroutines.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ProtoApplication : Application(), ImageLoaderFactory {

    private val applicationScope = CoroutineScope(SupervisorJob())

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch(Dispatchers.IO) {
            database = AppDatabase.getDatabase(
                this@ProtoApplication,
                applicationScope,
                DataGenerator.getInstance()
            )
            // FIXME: to trigger pre-population of the db there should be a dummy DAO call initiated.
            //  Need to wrap it up in a better place.
            database.userDao().getAllUsers()

            startKoin {
                androidLogger(Level.DEBUG)
                androidContext(this@ProtoApplication)
                modules(appModule)
            }
        }
    }


    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .crossfade(true)
            .dispatcher(Dispatchers.IO)
            .interceptorDispatcher(Dispatchers.IO)
            .build()
}