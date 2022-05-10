package com.ubaya.todoapp.model

import androidx.room.*

@Dao
interface TodoDAO {
    //anotasi untuk memasukkan data
    //kalau ada konflik di db / datanya sdh ada, maka replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:Todo)

    //tampil data yg is_done nya 0 aja
    @Query("SELECT * FROM todo WHERE is_done = 0 ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid = :id")
    suspend fun selectTodo(id:Int): Todo

    @Delete
    suspend fun deleteTodo(todo:Todo)

    //week 9
    @Query("UPDATE todo SET title = :title, notes = :notes, priority = :priority WHERE uuid = :id")
    suspend fun update(id : Int, title : String, notes : String, priority: Int)

    //week 9 tugas
    @Query("UPDATE todo SET is_done = 1 WHERE uuid = :id")
    suspend fun updateDone(id: Int)
}