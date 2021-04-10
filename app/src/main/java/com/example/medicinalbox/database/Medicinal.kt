package com.example.medicinalbox.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicinal_table")
data class Medicinal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "form_of_issue")
    var formOfIssue: String = "",

    @ColumnInfo(name = "amount")
    var amount: Int = -1,

    @ColumnInfo(name = "dosage")
    var dosage: String = "",

    @ColumnInfo(name = "comment")
    var comment: String = ""
)