package io.muhwyndham.deranmor.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.utils.Constants.Companion.REPORT_ID
import io.muhwyndham.deranmor.viewmodel.CarModelViewModel
import io.muhwyndham.deranmor.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private var reportViewModel: ReportViewModel? = null
    private var carModelViewModel: CarModelViewModel? = null

    private var reportId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        actionBar?.title = "Edit Laporan"

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)
        carModelViewModel = ViewModelProviders.of(this).get(CarModelViewModel::class.java)

        prepareDialogObserver()
        prepareInputCaps()
        prepareSpinner()
        prepareInitialData()

        et_tipe_kendaraan.setOnClickListener {
            startActivityForResult(
                Intent(this@EditActivity, SearchCarModelActivity::class.java),
                1003
            )
        }


        bt_input.setOnClickListener {
            reportViewModel?.validateReportInput(
                et_nama,
                et_tipe_kendaraan,
                et_nomor_aduan
            )
                ?.observe(this@EditActivity, Observer { isValid ->
                    reportViewModel?.updatereport(
                        isValid, Report(
                            reportId!!,
                            et_nama.text.toString(),
                            et_tipe_kendaraan.text.toString() + " " + et_tahun_kendaraan.text.toString(),
                            if (et_nomor_rangka.text.toString().trim { it <= ' ' }.isNotEmpty()) et_nomor_rangka.text.toString().toUpperCase().trim() else "-",
                            if (et_nomor_mesin.text.toString().trim { it <= ' ' }.isNotEmpty()) et_nomor_mesin.text.toString().toUpperCase().trim() else "-",
                            " ",
                            et_nomor_aduan.text.toString().toUpperCase().trim(),
                            spinner_status_aduan.selectedItem.toString()
                        )
                    )?.observe(this@EditActivity, Observer { if (it) finish() })
                })
        }
    }

    private fun prepareInputCaps() {
        val inputFilter = Array<InputFilter>(1) { InputFilter.AllCaps() }
        et_nopol.filters = inputFilter
        et_nomor_rangka.filters = inputFilter
        et_nomor_mesin.filters = inputFilter
        et_nomor_aduan.filters = inputFilter
    }

    private fun prepareInitialData() {
        reportId = intent.getStringExtra(REPORT_ID)
        if (reportId != null && reportId != "") reportViewModel?.getSingleReport(reportId!!)?.observe(
            this@EditActivity,
            Observer {
                setInitialData(it)
            })
    }

    private fun setInitialData(it: Report?) {
        if (it != null) {
            et_nama.setText(it.name)
            et_nopol.setText(it.idNopol)
            et_tipe_kendaraan.setText(it.tipeKendaraan!!.dropLast(5))
            et_tahun_kendaraan.setText(setVehicleYear(it.tipeKendaraan!!))
            et_nomor_mesin.setText(it.nomorMesin)
            et_nomor_rangka.setText(it.nomorRangka)
            et_nomor_aduan.setText(it.nomorAduan)
            spinner_status_aduan.setSelection(getIndex(it.statusAduan!!))

        }
    }

    private fun getIndex(statusAduan: String): Int {
        val array = resources.getStringArray(R.array.status_aduan)
        for (i in array.indices) {
            if (array[i] == statusAduan) return i
        }
        return 0
    }

    private fun setVehicleYear(tipeKendaraan: String): String {
        return tipeKendaraan.takeLast(4)
    }

    private fun prepareSpinner() {

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.status_aduan,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_status_aduan.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //TODO Still need to move this logic to View Model
        if (requestCode == 1003) {
            if (resultCode == Activity.RESULT_OK) {
                et_tipe_kendaraan.setText(data?.data.toString())
            }
        }
    }

    private fun prepareDialogObserver() {

        //toast observer
        reportViewModel?.toastLiveData?.observe(this@EditActivity, Observer {
            val toast = Toast.makeText(this@EditActivity, it, Toast.LENGTH_LONG)
            toast.show()
        })
    }
}
