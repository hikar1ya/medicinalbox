package com.example.medicinalbox.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Medicinal::class, CustomList::class, CustomListMedicinalConnection::class],
    version = 1, exportSchema = false)
abstract class MedicinalDatabase : RoomDatabase() {

    abstract fun getMedicinalDatabaseDao(): MedicinalDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MedicinalDatabase? = null

        fun getInstance(context: Context): MedicinalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        MedicinalDatabase::class.java, "medicinal_db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}