package com.ubaya.todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ubaya.todoapp.model.TodoDatabase

val DB_NAME = "newtododb"

//kalau ada migrasi, ditambahkan addMigration() sebelum build
fun buildDb(context : Context) =
    Room.databaseBuilder(context, TodoDatabase::class.java,
        "newtododb").addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()

//Membuat migrasi database nya
val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
    }
}

//Migrasi db week 9 tugas
//Karena tipe boolean sedikit susah digunakan dalam pengembangan aplikasi(?)
//bisa terbaca sebagai NULL jika tidak dituliskan secara eksplisit, bisa dibaca sebagai string atau juga -1 dibeberapa platform
//is_done memakai integer karena pada database nantinya boolean akan diubah menjadi tinyint.
val MIGRATION_2_3 = object : Migration(2, 3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
    }
}
