package io.muhwyndham.deranmor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.adapter.HomeRvAdapter
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private var reportViewModel : ReportViewModel? = null

    private var adapter: HomeRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)

        reportViewModel?.getReport()
            ?.observe(this, Observer<List<Report>> { this.renderReports(it) })

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_search.getText().toString().trim { it <= ' ' }.isNotEmpty())
                    reportViewModel?.getReportThatContainString("%${et_search.getText().toString().trim()}%")?.observe(
                        this@SearchActivity,
                        Observer<List<Report>> { this@SearchActivity.renderReports(it) })
                else if (et_search.getText().toString().trim().isEmpty())
                    reportViewModel?.getReport()?.observe(
                        this@SearchActivity,
                        Observer<List<Report>> { this@SearchActivity.renderReports(it) })
            }

        })

        bt_search.setOnClickListener {
            if (et_search.getText().toString().trim { it <= ' ' }.isNotEmpty())
                reportViewModel?.getReportThatContainString("%${et_search.getText().toString().trim()}%")?.observe(
                    this,
                    Observer<List<Report>> { this.renderReports(it) })
            else if (et_search.getText().toString().trim().isEmpty())
                reportViewModel?.getReport()?.observe(
                    this,
                    Observer<List<Report>> { this.renderReports(it) })
        }
    }

    private fun renderReports(reportList: List<Report>) {
        adapter = HomeRvAdapter(reportList)
        val layoutManager = LinearLayoutManager(this@SearchActivity)
        rv_report.layoutManager = layoutManager
        rv_report.adapter = adapter
    }
}
