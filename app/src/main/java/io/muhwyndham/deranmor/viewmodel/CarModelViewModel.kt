package io.muhwyndham.deranmor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.muhwyndham.deranmor.model.CarModel
import io.muhwyndham.deranmor.repository.AppRepository

class CarModelViewModel(application: Application) : AndroidViewModel(application) {


    private var repository: AppRepository = AppRepository(application)

    fun getCarModel() = repository.getAllCarModel()

    fun getReportThatContainString(string: String) = repository.getCarModelThatContainString(string)

    fun setReport(carModel: CarModel) {
        repository.setCarModel(carModel)
    }
}