package com.stanica.speedoxtream.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * [RoomDatabase] implementation adapted for SpeedoXtream.
 *
 * @author stanica
 * @date  11/04/2021
 */
@Database(version = 1, entities = [RoomAvContent::class])
abstract class SpeedoXDatabase : RoomDatabase() {

    /**
     * Gets the [AvContentDao] responsible for accessing data regarding contents.
     *
     * @return the DAO responsible for audiovisual content
     */
    abstract fun avContentDao(): AvContentDao

    companion object {

        @Volatile
        private var instance: SpeedoXDatabase? = null
        private const val DATABASE_NAME = "speedoxtream"

        /*
        Singleton pattern code taken from the architecture components sample here:
        https://github.com/googlesamples/android-architecture-components/blob/master/BasicRxJavaSampleKotlin/app/src/main/java/com/example/android/observability/persistence/UsersDatabase.kt
        */

        fun getInstance(context: Context): SpeedoXDatabase =
                 instance ?: synchronized(this) {
                    instance ?: buildDatabase(context).also { instance = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        SpeedoXDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
    }
}