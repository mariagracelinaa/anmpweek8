package com.ubaya.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//add entity (week 8)
@Entity
data class Todo(
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "notes")
    var note:String,
    @ColumnInfo(name = "priority")
    var priority : Int,
    @ColumnInfo(name = "is_done")
    var is_done : Int
){
    //auto increment kalau di database biasa
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}