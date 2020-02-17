package io.muhwyndham.deranmor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.repository.AppRepository

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AppRepository = AppRepository(application)

    fun getReport() = repository.getAllReport()

    fun getReportThatContainString(string: String) = repository.getReportThatContainString(string)

    fun setReport(report: Report) {
        repository.setReport(report)
    }
}