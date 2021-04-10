package com.example.medicinalbox.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group_medicinal_connection_table")
data class GroupMedicinalConnection(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "id_group")
    var id_group: Long = 0L,

    @ColumnInfo(name = "id_medicinal")
    var id_medicinal: Long = 0L
)
