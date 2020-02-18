package io.muhwyndham.deranmor.viewmodel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.repository.AppRepository

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AppRepository = AppRepository(application)

    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    fun getReport() = repository.getAllReport()

    fun getSingleReport(id: String) = repository.getSingleReport(id)

    fun getReportThatContainString(string: String) = repository.getReportThatContainString(string)

    fun setReport(isValid: Boolean, report: Report): MutableLiveData<Boolean> {

        if (isValid) {
            repository.setReport(report)
            return MutableLiveData(true)
        } else {
            return MutableLiveData(false)
        }


    }

    fun updatereport(isValid: Boolean, report: Report): MutableLiveData<Boolean> {
        if (isValid) {
            repository.updateReport(report)
            return MutableLiveData(true)
        } else {
            return MutableLiveData(false)
        }

    }

    fun validateReportInput(
        et_nama: EditText,
        et_tipe_kendaraan: EditText,
        et_nomor_aduan: EditText
    ): LiveData<Boolean> {
        return if (
            et_nama.text.toString().trim { it <= ' ' }.isNotEmpty()
            && et_nama.text.toString().trim { it <= ' ' }.isNotEmpty()
            && et_tipe_kendaraan.text.toString().trim { it <= ' ' }.isNotEmpty()
            && et_nomor_aduan.text.toString().trim { it <= ' ' }.isNotEmpty()

        ) {
            toastLiveData.value = "Data telah diinput!"

            MutableLiveData(true)

        } else {
            toastLiveData.value = "Kolom tidak boleh kosong!"
            MutableLiveData(false)
        }
    }


}