package com.example.medicinalbox.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicinalDatabaseDao {

    @Insert
    fun insertMedicinal(medicinal: Medicinal)

    @Update
    fun updateMedicinal(medicinal : Medicinal)

    @Delete
    fun deleteMedicinal(medicinal: Medicinal)

    @Insert
    fun insertGroup(group: Group)

    @Update
    fun updateGroup(group: Group)

    @Delete
    fun deleteGroup(group: Group)

    @Query("SELECT * FROM medicinal_table WHERE id = :key")
    fun getMedicinalById(key: Long): Medicinal?

    @Query("DELETE FROM medicinal_table")
    fun clear()

    @Query("SELECT * FROM medicinal_table ORDER BY id DESC")
    fun getMedicinals(): LiveData<List<Medicinal>>

    @Query("SELECT * FROM group_table ORDER BY id DESC")
    fun getGroups(): LiveData<List<Group>>

    /*@Query("SELECT name FROM group_table WHERE id = :key LIMIT 1")
    fun getGroupById(key: Long)*/

    @Query("SELECT id FROM group_table ORDER BY id DESC LIMIT 1")
    fun getLastGroup(): Long

    @Query("INSERT INTO group_medicinal_connection_table (id_group, id_medicinal) VALUES (:id, :medicinal_id)")
    fun addMedicinalToGroup(medicinal_id: Long, id: Long)

    @Query("DELETE FROM group_table WHERE id = :key")
    fun deleteGroupById(key: Long)

    @Query("DELETE FROM group_medicinal_connection_table WHERE id_group = :key")
    fun deleteConnectionsById(key: Long)

    @Query("SELECT * FROM medicinal_table WHERE name LIKE '%' + :search + '%'")
    fun filter(search: String) : LiveData<List<Medicinal>>

    @Query("SELECT * FROM group_medicinal_connection_table JOIN medicinal_table ON group_medicinal_connection_table.id_medicinal = medicinal_table.id WHERE group_medicinal_connection_table.id_group = :key")
    fun getMedicinalsByGroup(key: Long) : LiveData<List<Medicinal>>

}