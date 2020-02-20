@file:Suppress("DEPRECATION")

package io.muhwyndham.deranmor.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.adapter.SearchReportAdapter
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.utils.Constants.Companion.REPORT_ID
import io.muhwyndham.deranmor.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.activity_search.*

@Suppress("DEPRECATION")
class SearchActivity : AppCompatActivity() {

    private var reportViewModel: ReportViewModel? = null

    private var adapter: SearchReportAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        actionBar?.title = "Cari Laporan"
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)

        reportViewModel?.getReport()
            ?.observe(this, Observer<List<Report>> { this.renderReports(it) })

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_search.text.toString().trim { it <= ' ' }.isNotEmpty())
                    reportViewModel?.getReportThatContainString("%${et_search.text.toString().trim()}%")?.observe(
                        this@SearchActivity,
                        Observer<List<Report>> { this@SearchActivity.renderReports(it) })
                else if (et_search.text.toString().trim().isEmpty())
                    reportViewModel?.getReport()?.observe(
                        this@SearchActivity,
                        Observer<List<Report>> { this@SearchActivity.renderReports(it) })
            }

        })

        bt_search.setOnClickListener {
            if (et_search.text.toString().trim { it <= ' ' }.isNotEmpty())
                reportViewModel?.getReportThatContainString("%${et_search.text.toString().trim()}%")?.observe(
                    this,
                    Observer<List<Report>> { this.renderReports(it) })
            else if (et_search.text.toString().trim().isEmpty())
                reportViewModel?.getReport()?.observe(
                    this,
                    Observer<List<Report>> { this.renderReports(it) })
        }
    }


    fun navigateToEditActivity(id: String) {
        val intent = Intent(this@SearchActivity, EditActivity::class.java)
        intent.putExtra(REPORT_ID, id)
        startActivity(intent)
    }

    private fun renderReports(reportList: List<Report>) {
        adapter = SearchReportAdapter(this@SearchActivity, reportList)
        val layoutManager = LinearLayoutManager(this@SearchActivity)
        rv_report.layoutManager = layoutManager
        rv_report.adapter = adapter
    }
}
