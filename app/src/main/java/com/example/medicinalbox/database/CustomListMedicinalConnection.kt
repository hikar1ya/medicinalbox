package com.example.medicinalbox.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_list_medicinal_connection_table")
class CustomListMedicinalConnection(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "custom_list_id")
    var customListId: Long = 0L,

    @ColumnInfo(name = "medicinal_id")
    var medicinalId: Long = 0L
)