package com.example.noteapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ContactDao {
    @Insert
    fun insert(contact: Note_model)

    @Insert
    fun insertAll(contacts: List<Note_model>)

    @Query("SELECT * FROM contacts")
    fun getContact():List<Note_model>

    @Delete
    fun deletecontact(contact:Note_model)

    @Update
    fun update(contact: Note_model)

    @Query("DELETE FROM contacts")
    fun nukeTable()


    @Query("SELECT * FROM contacts WHERE name LIKE :query || '%'")
    fun searchUzWords(query: String): List<Note_model>

    @Query("SELECT * FROM contacts ORDER BY dead_line ASC")
    fun listOrderedByDeadLine():List<Note_model>

    @Query("SELECT * FROM contacts ORDER BY importance DESC")
    fun listOrderedByImportance():List<Note_model>

}