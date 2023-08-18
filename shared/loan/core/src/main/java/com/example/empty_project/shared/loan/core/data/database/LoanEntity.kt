package com.example.empty_project.shared.loan.core.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.empty_project.shared.loan.core.domain.entity.LoanStatus

@Entity(TableDatabase.TABLE_NAME)
data class LoanEntity(
    @PrimaryKey
    @ColumnInfo(name = TableDatabase.Columns.ID)
    val id: Long,
    @ColumnInfo(name = TableDatabase.Columns.AMOUNT)
    val amount: Long,
    @ColumnInfo(name = TableDatabase.Columns.DATE)
    val date: String,
    @ColumnInfo(name = TableDatabase.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = TableDatabase.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = TableDatabase.Columns.PERCENT)
    val percent: Double,
    @ColumnInfo(name = TableDatabase.Columns.PERIOD)
    val period: Int,
    @ColumnInfo(name = TableDatabase.Columns.PHONE_NUMBER)
    val phoneNumber: String,
    @ColumnInfo(name = TableDatabase.Columns.STATUS)
    val status: LoanStatus
)
