package io.muhwyndham.deranmor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.muhwyndham.deranmor.R
import io.muhwyndham.deranmor.model.Report
import io.muhwyndham.deranmor.ui.SearchActivity
import kotlinx.android.synthetic.main.report_item.view.*

class SearchReportAdapter(val context: Context, val reportList: List<Report>) :
    RecyclerView.Adapter<SearchReportAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.report_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(reportList[position], context)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(report: Report, context: Context) {
            itemView.tv_name.text = report.name
            itemView.tv_nopol.text = report.idNopol
            itemView.tv_tipe_kendaraan.text = "Tipe kendaraan: ${report.tipeKendaraan}"
            itemView.tv_nomor_rangka.text = "Nomor rangka: ${report.nomorRangka}"
            itemView.tv_nomor_mesin.text = "Nomor mesin: ${report.nomorMesin}"
            itemView.tv_name_nomor_aduan.text = "Nomor aduan: ${report.nomorAduan}"
            itemView.tv_name_status_aduan.text = "Status Aduan: ${report.statusAduan}"
            itemView.bt_edit.setOnClickListener {
                (context as SearchActivity).navigateToEditActivity(report.idNopol)
            }
        }
    }

}