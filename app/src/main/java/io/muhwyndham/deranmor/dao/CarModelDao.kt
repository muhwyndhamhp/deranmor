package io.muhwyndham.deranmor.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.muhwyndham.deranmor.model.CarModel

@Dao
interface CarModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCarModel(carModel: CarModel)

    @Query("SELECT * FROM car_model_table ORDER BY id ASC")
    fun getAllCarModel(): LiveData<List<CarModel>>

    @Query("SELECT * FROM car_model_table WHERE id = :id")
    fun getCarModel(id: Int): LiveData<CarModel>

    @Query("SELECT * FROM car_model_table WHERE car_model LIKE :string ORDER BY id ASC")
    fun getCarModelThatContains(string: String): LiveData<List<CarModel>>

    @Query("DELETE FROM car_model_table")
    fun deleteAll()

    @Delete
    fun deleteCarModel(carModel: CarModel)
}