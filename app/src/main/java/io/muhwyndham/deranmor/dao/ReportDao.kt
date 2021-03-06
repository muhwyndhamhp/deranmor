package io.muhwyndham.deranmor.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.muhwyndham.deranmor.model.Report

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setReport(report: Report)

    @Update
    fun updateReport(report: Report)

    @Query("SELECT * FROM report_table ORDER BY id_nopol ASC")
    fun getAllReport(): LiveData<List<Report>>

    @Query("SELECT * FROM report_table WHERE id_nopol = :id")
    fun getReport(id: String): LiveData<Report>

    @Query("SELECT * FROM report_table WHERE (name LIKE :string OR id_nopol LIKE :string OR tipe_kendaraan LIKE :string OR nomor_rangka LIKE :string OR nomor_mesin LIKE :string OR ovd LIKE :string OR nomor_aduan LIKE :string) ORDER BY name ASC")
    fun getReportThatContains(string: String): LiveData<List<Report>>

    @Query("DELETE FROM report_table")
    fun deleteAll()

    @Delete
    fun delete(report: Report)
}