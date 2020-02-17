package io.muhwyndham.deranmor.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : AppCompatActivity() {


    private var reportViewModel: ReportViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)

        bt_input.setOnClickListener {
            if (
                et_nama.getText().toString().trim { it <= ' ' }.isNotEmpty()
                && et_nama.getText().toString().trim { it <= ' ' }.isNotEmpty()
                && et_tipe_kendaraan.getText().toString().trim { it <= ' ' }.isNotEmpty()
                && et_nomor_aduan.getText().toString().trim { it <= ' ' }.isNotEmpty()

            ) {
                val report = Report(
                    0,
                    et_nama.getText().toString(),
                    et_nopol.getText().toString(),
                    et_tipe_kendaraan.getText().toString(),
                    if (et_nomor_rangka.getText().toString().trim { it <= ' ' }.isNotEmpty()) et_nomor_rangka.getText().toString() else "-",
                    if (et_nomor_mesin.getText().toString().trim { it <= ' ' }.isNotEmpty()) et_nomor_mesin.getText().toString() else "-",
                    " ",
                    et_nomor_aduan.getText().toString()
                )

                reportViewModel?.setReport(report)

                et_nama.setText("")
                et_nopol.setText("")
                et_tipe_kendaraan.setText("")
                et_nomor_rangka.setText("")
                et_nomor_mesin.setText("")
                et_nomor_aduan.setText("")

                val toast = Toast.makeText(
                    this@InputActivity,
                    "Data telah diinput!",
                    Toast.LENGTH_LONG
                )
                toast.show()

                finish()
            } else {
                val toast = Toast.makeText(
                    this@InputActivity,
                    "Kolom tidak boleh kosong!",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
        }
    }
}
