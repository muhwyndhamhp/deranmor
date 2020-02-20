package io.muhwyndham.deranmor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "report_table")
data class Report(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_nopol") var idNopol: String = "",
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "tipe_kendaraan") var tipeKendaraan: String? = "",
    @ColumnInfo(name = "nomor_rangka") var nomorRangka: String? = "-",
    @ColumnInfo(name = "nomor_mesin") var nomorMesin: String? = "-",
    @ColumnInfo(name = "ovd") var ovd: String? = "-",
    @ColumnInfo(name = "nomor_aduan") var nomorAduan: String? = "-",
    @ColumnInfo(name = "status_aduan") var statusAduan: String? = "Dalam Pencarian"
)