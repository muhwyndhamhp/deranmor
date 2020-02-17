package io.muhwyndham.deranmor.repository

import android.app.Application
import io.muhwyndham.deranmor.dao.CarModelDao
import io.muhwyndham.deranmor.dao.ReportDao
import io.muhwyndham.deranmor.database.ReportDatabase
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

    init {
        val db = ReportDatabase.getDatabase(application)
        reportDao = db?.reportDao()
        carModelDao = db?.carModelDao()
    }

    fun getAllReport() = reportDao?.getAllReport()

    fun getReportThatContainString(string: String) = reportDao?.getReportThatContains(string)

    fun setReport(report: Report) {
        launch { setReportBG(report) }
    }

    private suspend fun setReportBG(report: Report) {
        withContext(Dispatchers.IO) {
            reportDao?.setReport(report)
        }

    }
}