package io.muhwyndham.deranmor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "report_table")
data class Report(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,

    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "nopol") var nopol: String,
    @ColumnInfo(name = "tipe_kendaraan") var tipeKendaraan: String = "minibus",
    @ColumnInfo(name = "nomor_rangka") var nomorRangka: String = "12345678",
    @ColumnInfo(name = "nomor_mesin") var nomorMesin: String = "12345678",
    @ColumnInfo(name = "ovd") var ovd: String = "12345678",
    @ColumnInfo(name = "nomor_aduan") var nomorAduan: String = "12345678"
)