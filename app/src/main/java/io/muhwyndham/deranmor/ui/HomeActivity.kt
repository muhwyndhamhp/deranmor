package io.muhwyndham.deranmor.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.muhwyndham.deranmor.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bt_cari.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }

        bt_input.setOnClickListener { startActivity(Intent(this, InputActivity::class.java)) }
    }
}
