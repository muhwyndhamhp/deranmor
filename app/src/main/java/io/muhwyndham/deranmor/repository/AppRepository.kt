package io.muhwyndham.deranmor.repository

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import io.muhwyndham.deranmor.dao.CarModelDao
import io.muhwyndham.deranmor.dao.ReportDao
import io.muhwyndham.deranmor.database.ReportDatabase
import io.muhwyndham.deranmor.model.CarModel
import io.muhwyndham.deranmor.model.Report
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class AppRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var reportDao: ReportDao?
    private var carModelDao: CarModelDao?
    private val db = FirebaseFirestore.getInstance()

    init {
        val db = ReportDatabase.getDatabase(application)
        reportDao = db?.reportDao()
        carModelDao = db?.carModelDao()
    }

    fun getAllReport() = reportDao?.getAllReport()

    fun getSingleReport(id: String) = reportDao?.getReport(id)

    fun getReportThatContainString(string: String) = reportDao?.getReportThatContains(string)

    fun setReport(report: Report) {
        launch { setReportBG(report) }
    }


    fun updateReport(report: Report) {
        launch { updateReportBG(report) }
    }

    fun getAllCarModel() = carModelDao?.getAllCarModel()

    fun getCarModelThatContainString(string: String) = carModelDao?.getCarModelThatContains(string)

    fun setCarModel(carModel: CarModel) {
        launch { setCarModelBG(carModel) }
    }

    private suspend fun updateReportBG(report: Report) {
        withContext(Dispatchers.IO) {
            reportDao?.updateReport(report)
            db.collection("report").document(report.idNopol).set(report)
        }
    }

    private suspend fun setCarModelBG(carModel: CarModel) {
        withContext(Dispatchers.IO) {
            carModelDao?.insertCarModel(carModel)
        }
    }

    private suspend fun setReportBG(report: Report) {
        withContext(Dispatchers.IO) {
            reportDao?.setReport(report)
            db.collection("report").document(report.idNopol).set(report)
        }

    }
}