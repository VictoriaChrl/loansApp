package com.example.empty_project.shared.loan.core.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LoanEntity::class], version = LoansDatabase.DB_VERSION)
abstract class LoansDatabase : RoomDatabase() {

    abstract fun loanDao(): LoanDao

    companion object {
        const val DB_NAME = "database-loans"
        const val DB_VERSION = 1

        private var INSTANCE: LoansDatabase? = null
        fun getInstance(application: Application): LoansDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    application,
                    LoansDatabase::class.java,
                    DB_NAME
                ).build().also { INSTANCE = it }
            }
    }
}