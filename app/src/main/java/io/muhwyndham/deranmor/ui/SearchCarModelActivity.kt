package io.muhwyndham.deranmor.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.adapter.SearchCarModelAdapter
import io.muhwyndham.deranmor.model.CarModel
import io.muhwyndham.deranmor.viewmodel.CarModelViewModel
import io.muhwyndham.deranmor.viewmodel.ReportViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchCarModelActivity : AppCompatActivity() {

    private var carModelViewModel: CarModelViewModel? = null

    private var adapter: SearchCarModelAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_car_model)

        carModelViewModel = ViewModelProviders.of(this).get(CarModelViewModel::class.java)

        carModelViewModel?.getCarModel()
            ?.observe(this, Observer<List<CarModel>> { this.renderReports(it) })

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_search.text.toString().trim { it <= ' ' }.isNotEmpty())
                    carModelViewModel?.getReportThatContainString("%${et_search.text.toString().trim()}%")?.observe(
                        this@SearchCarModelActivity,
                        Observer<List<CarModel>> { this@SearchCarModelActivity.renderReports(it) })
                else if (et_search.text.toString().trim().isEmpty())
                    carModelViewModel?.getCarModel()?.observe(
                        this@SearchCarModelActivity,
                        Observer<List<CarModel>> { this@SearchCarModelActivity.renderReports(it) })
            }

        })

        bt_search.setOnClickListener {
            if (et_search.text.toString().trim { it <= ' ' }.isNotEmpty())
                carModelViewModel?.getReportThatContainString("%${et_search.text.toString().trim()}%")?.observe(
                    this,
                    Observer<List<CarModel>> { this.renderReports(it) })
            else if (et_search.text.toString().trim().isEmpty())
                carModelViewModel?.getCarModel()?.observe(
                    this,
                    Observer<List<CarModel>> { this.renderReports(it) })
        }
    }

    fun setCarModel(string: String) {
        val data = Intent()
        data.data = Uri.parse(string)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun renderReports(carModelList: List<CarModel>) {
        adapter = SearchCarModelAdapter(carModelList, this@SearchCarModelActivity)
        val layoutManager = LinearLayoutManager(this@SearchCarModelActivity)
        rv_report.layoutManager = layoutManager
        rv_report.adapter = adapter
    }
}
