package io.muhwyndham.deranmor.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.repository.AppRepository
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var dialog : Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bt_cari.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }

        bt_input.setOnClickListener { startActivity(Intent(this, InputActivity::class.java)) }

        bt_update.setOnClickListener {updateReportDatabase()}
    }

    private fun updateReportDatabase() {
        val repository = AppRepository(application)
        val db = FirebaseFirestore.getInstance()

        showLoadingDialog()
        db.collection("report").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val report = document.toObject(Report::class.java)
                repository.setReportForUpdate(report)
            }

            dialog!!.dismiss()
        }
    }

    private fun showLoadingDialog() {
        dialog = Dialog(this)
        dialog!!.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.progress_dialog)
        }

        dialog!!.show()

    }
}
