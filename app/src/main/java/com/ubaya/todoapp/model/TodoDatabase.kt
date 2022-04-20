package com.ubaya.todoapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya.todoapp.util.MIGRATION_1_2

@Database(entities = arrayOf(Todo::class), version = 2)
abstract class TodoDatabase : RoomDatabase() {
    //karena interface, jd lgsg panggil dgn abstract
    abstract fun todoDao() : TodoDAO

    //implement singleton -> hanya ada 1 object
    companion object {
        //volatile -> menandakan field ini jika diisi akan disadari threads lain
        @Volatile private var instance : TodoDatabase ?= null
        //
        private val LOCK = Any()

        //untuk membuat database
        private fun buildDatabase(context : Context) =
            Room.databaseBuilder(
                        context.applicationContext,
                TodoDatabase::class.java,
                "newtododb").addMigrations(MIGRATION_1_2).build()

        //Menandakan operasi yg di dlm akan dilakukan dirinya(theads) sendiri
        operator fun invoke(context:Context){
            if(instance != null){
                synchronized(LOCK){
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}