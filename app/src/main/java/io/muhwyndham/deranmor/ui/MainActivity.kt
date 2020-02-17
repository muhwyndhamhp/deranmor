package io.muhwyndham.deranmor.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import io.muhwyndham.deranmor.R

class MainActivity : AppCompatActivity() {

    companion object {
        const val RC_SIGN_IN = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareFirebaseUI()
    }

    private fun prepareFirebaseUI() {
        val providers = arrayListOf(AuthUI.IdpConfig.PhoneBuilder().build())

        if (FirebaseAuth.getInstance().currentUser == null)
            startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                    providers
                ).build(), RC_SIGN_IN
            )
        else startActivity(Intent(this@MainActivity, HomeActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
//                val user = FirebaseAuth.getInstance().currentUser

                val toast = Toast.makeText(
                    this@MainActivity,
                    "Login berhasil!",
                    Toast.LENGTH_LONG
                )
                toast.show()

                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            } else {
                val toast = Toast.makeText(
                    this@MainActivity,
                    "Login gagal, silahkan coba lagi!",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
        }
    }
}
