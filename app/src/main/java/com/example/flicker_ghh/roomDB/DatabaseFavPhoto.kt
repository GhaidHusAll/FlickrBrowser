package com.example.flicker_ghh.roomDB

import android.content.Context
import androidx.room.*


@Database(entities = [PhotoRoom::class],version = 1,exportSchema = false)
abstract class DatabaseFavPhoto: RoomDatabase() {

    abstract fun myDao(): PhotoDao
    companion object{
      private  var dbObj: DatabaseFavPhoto? = null

        fun setUpDatabase(context:Context):DatabaseFavPhoto{
            val checkDB = dbObj
            if (checkDB != null){return checkDB}
            synchronized(this){
                val newDBObj = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseFavPhoto::class.java,
                    "photo"
                ).fallbackToDestructiveMigration().build()
                dbObj = newDBObj
                return newDBObj
            }
        }
    }
}