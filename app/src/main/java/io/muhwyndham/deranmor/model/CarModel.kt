package io.muhwyndham.deranmor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "car_model_table")
data class CarModel(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "car_model") var carModel: String
)