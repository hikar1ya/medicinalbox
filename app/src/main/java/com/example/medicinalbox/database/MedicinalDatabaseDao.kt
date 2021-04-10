package com.example.medicinalbox.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicinalDatabaseDao {

    @Insert
    fun insertMedicinal(medicinal: Medicinal)

    @Update
    fun updateMedicinal(medicinal: Medicinal)

    @Delete
    fun deleteMedicinal(medicinal: Medicinal)

    @Insert
    fun insertCustomList(group: CustomList)

    @Update
    fun updateCustomList(group: CustomList)

    @Delete
    fun deleteCustomList(group: CustomList)

    @Query("SELECT * FROM medicinal_table WHERE id = :key")
    fun getMedicinalById(key: Long): Medicinal?

    @Query("DELETE FROM medicinal_table")
    fun clear()

    @Query("SELECT * FROM medicinal_table ORDER BY id DESC")
    fun getMedicinals(): LiveData<List<Medicinal>>

    @Query("SELECT * FROM custom_list_table ORDER BY id DESC")
    fun getCustomLists(): LiveData<List<CustomList>>

    /*@Query("SELECT name FROM custom_list_table WHERE id = :key LIMIT 1")
    fun getCustomListById(key: Long)*/

    @Query("SELECT id FROM custom_list_table ORDER BY id DESC LIMIT 1")
    fun getLastCustomList(): Long

    @Query("INSERT INTO custom_list_medicinal_connection_table (custom_list_id, medicinal_id) VALUES (:id, :medicinal_id)")
    fun addMedicinalToCustomList(medicinal_id: Long, id: Long)

    @Query("DELETE FROM custom_list_table WHERE id = :key")
    fun deleteCustomListById(key: Long)

    @Query("DELETE FROM custom_list_medicinal_connection_table WHERE custom_list_id = :key")
    fun deleteConnectionsById(key: Long)

    @Query("SELECT * FROM medicinal_table WHERE name LIKE '%' + :search + '%'")
    fun filter(search: String): LiveData<List<Medicinal>>

    @Query("SELECT * FROM custom_list_medicinal_connection_table JOIN medicinal_table ON custom_list_medicinal_connection_table.medicinal_id = medicinal_table.id WHERE custom_list_medicinal_connection_table.custom_list_id = :key")
    fun getMedicinalsByCustomList(key: Long): LiveData<List<Medicinal>>

}