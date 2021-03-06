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
    fun insertCustomList(customList: CustomList)

    @Update
    fun updateCustomList(customList: CustomList)

    @Delete
    fun deleteCustomList(customList: CustomList)

    @Insert
    fun insertConnection(connection: CustomListMedicinalConnection)

    @Query("SELECT * FROM medicinal_table WHERE id = :key")
    fun getMedicinalById(key: Long): Medicinal?

    @Query("SELECT * FROM medicinal_table WHERE id = :key")
    fun getMedicinalByIdLD(key: Long): LiveData<Medicinal>

    @Query("SELECT * FROM medicinal_table ORDER BY id DESC")
    fun getMedicinals(): LiveData<List<Medicinal>>

    @Query("SELECT * FROM custom_list_table ORDER BY id DESC")
    fun getCustomLists(): LiveData<List<CustomList>>

    @Query("SELECT * FROM custom_list_table WHERE id = :key LIMIT 1")
    fun getCustomListByIdLD(key: Long): LiveData<CustomList>

    @Query("SELECT * FROM custom_list_table WHERE id = :key LIMIT 1")
    fun getCustomListById(key: Long): CustomList

    @Query("SELECT id FROM custom_list_table ORDER BY id DESC LIMIT 1")
    fun getLastCustomList(): Long

    @Query("DELETE FROM custom_list_table WHERE id = :key")
    fun deleteCustomListById(key: Long)

    @Query("DELETE FROM custom_list_medicinal_connection_table WHERE custom_list_id = :key")
    fun deleteConnectionsByCustomListId(key: Long)

    @Query("SELECT * FROM medicinal_table WHERE name LIKE '%' + :search + '%'")
    fun filter(search: String): LiveData<List<Medicinal>>

    @Query("SELECT * FROM custom_list_medicinal_connection_table JOIN medicinal_table ON custom_list_medicinal_connection_table.medicinal_id = medicinal_table.id WHERE custom_list_medicinal_connection_table.custom_list_id = :key")
    fun getMedicinalsByCustomListIdLD(key: Long): LiveData<List<Medicinal>>

    @Query("SELECT * FROM custom_list_medicinal_connection_table JOIN medicinal_table ON custom_list_medicinal_connection_table.medicinal_id = medicinal_table.id WHERE custom_list_medicinal_connection_table.custom_list_id = :key")
    fun getMedicinalsByCustomListId(key: Long) : List<Medicinal>

    @Query("DELETE FROM custom_list_medicinal_connection_table WHERE custom_list_id = :customListId and medicinal_id = :medicinalId")
    fun deleteConnectionByCustomListAndMedicinal(customListId: Long, medicinalId: Long)

}