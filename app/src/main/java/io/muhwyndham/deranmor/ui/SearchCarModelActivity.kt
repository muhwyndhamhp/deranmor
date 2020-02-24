package io.muhwyndham.deranmor.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.adapter.SearchCarModelAdapter
import io.muhwyndham.deranmor.model.CarModel
import io.muhwyndham.deranmor.viewmodel.CarModelViewModel
import kotlinx.android.synthetic.main.activity_search.bt_search
import kotlinx.android.synthetic.main.activity_search.et_search
import kotlinx.android.synthetic.main.activity_search.rv_report
import kotlinx.android.synthetic.main.activity_search_car_model.*

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

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_search.text.toString().trim { it <= ' ' }.isNotEmpty()) {
                    carModelViewModel?.getReportThatContainString("%${et_search.text.toString().trim()}%")
                        ?.observe(
                            this@SearchCarModelActivity,
                            Observer<List<CarModel>> { this@SearchCarModelActivity.renderReports(it) })
                    ll_add_custom_car_model.visibility = View.VISIBLE
                    tv_custom_car_model.text = "Set sebagai '${et_search.text}'"
                } else if (et_search.text.toString().trim().isEmpty()) {
                    carModelViewModel?.getCarModel()?.observe(
                        this@SearchCarModelActivity,
                        Observer<List<CarModel>> { this@SearchCarModelActivity.renderReports(it) })
                    ll_add_custom_car_model.visibility = View.GONE
                }
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

        ll_add_custom_car_model.setOnClickListener {
            setCarModel(et_search.text.toString())
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
