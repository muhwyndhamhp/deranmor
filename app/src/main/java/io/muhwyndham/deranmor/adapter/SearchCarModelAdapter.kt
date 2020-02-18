package io.muhwyndham.deranmor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.model.CarModel
import io.muhwyndham.deranmor.ui.SearchCarModelActivity
import kotlinx.android.synthetic.main.car_model_item.view.*

class SearchCarModelAdapter(val carModelList: List<CarModel>, val context: Context) :
    RecyclerView.Adapter<SearchCarModelAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_model_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return carModelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(carModelList[position], context)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(carModel: CarModel, context: Context) {
            itemView.tv_car_model.text = carModel.carModel

            itemView.setOnClickListener {
                (context as SearchCarModelActivity).setCarModel(carModel.carModel)
            }
        }
    }
}