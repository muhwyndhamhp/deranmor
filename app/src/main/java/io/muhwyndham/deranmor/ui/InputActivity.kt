@file:Suppress("DEPRECATION")

package io.muhwyndham.deranmor.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.viewmodel.CarModelViewModel
import io.muhwyndham.deranmor.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : AppCompatActivity() {


    private var reportViewModel: ReportViewModel? = null
    private var carModelViewModel: CarModelViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)
        carModelViewModel = ViewModelProviders.of(this).get(CarModelViewModel::class.java)

        prepareDialogObserver()

        et_tipe_kendaraan.setOnClickListener {
            startActivityForResult(
                Intent(this@InputActivity, SearchCarModelActivity::class.java),
                1003
            )
        }


        bt_input.setOnClickListener {
            reportViewModel?.validateReportInput(
                et_nama,
                et_tipe_kendaraan,
                et_nomor_aduan
            )
                ?.observe(this@InputActivity, Observer { isValid ->
                    reportViewModel?.setReport(
                        isValid, Report(
                            0,
                            et_nama.text.toString(),
                            et_nopol.text.toString(),
                            et_tipe_kendaraan.text.toString() + " " + et_tahun_kendaraan.text.toString(),
                            if (et_nomor_rangka.text.toString().trim { it <= ' ' }.isNotEmpty()) et_nomor_rangka.text.toString() else "-",
                            if (et_nomor_mesin.text.toString().trim { it <= ' ' }.isNotEmpty()) et_nomor_mesin.text.toString() else "-",
                            " ",
                            et_nomor_aduan.text.toString()
                        )
                    )?.observe(this@InputActivity, Observer { if (it) finish() })
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1003) {
            if (resultCode == Activity.RESULT_OK) {
                et_tipe_kendaraan.setText(data?.data.toString())
            }
        }
    }

    private fun prepareDialogObserver() {

        //toast observer
        reportViewModel?.toastLiveData?.observe(this@InputActivity, Observer {
            val toast = Toast.makeText(this@InputActivity, it, Toast.LENGTH_LONG)
            toast.show()
        })
    }
}
