package io.muhwyndham.deranmor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.muhwyndham.deranmor.dao.CarModelDao
import io.muhwyndham.deranmor.dao.ReportDao
import io.muhwyndham.deranmor.model.CarModel
import io.muhwyndham.deranmor.model.Report

@Database(entities = [Report::class, CarModel::class], version = 1, exportSchema = false)
abstract class ReportDatabase : RoomDatabase() {

    abstract fun reportDao(): ReportDao
    abstract fun carModelDao(): CarModelDao

    companion object {

        @Volatile
        private var INSTANCE: ReportDatabase? = null

        fun getDatabase(context: Context): ReportDatabase? {
            synchronized(ReportDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ReportDatabase::class.java, "report_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}