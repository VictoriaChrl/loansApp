package com.example.empty_project.shared.loan.core.data.database

import androidx.room.*
import com.example.empty_project.shared.loan.core.data.model.LoanModel

@Dao
interface LoanDao {

    @Query("SELECT * FROM ${TableDatabase.TABLE_NAME}")
    suspend fun getAllLoans(): List<LoanEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoans(loans: List<LoanEntity>)

}