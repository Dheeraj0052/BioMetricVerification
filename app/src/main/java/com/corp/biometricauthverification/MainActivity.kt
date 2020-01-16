package com.corp.biometricauthverification

import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt.AuthenticationCallback
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    val executors = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val activity= this
        val biometricPrompt : androidx.biometric.BiometricPrompt = androidx.biometric.BiometricPrompt(activity,executors,
            object : AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode ==androidx.biometric.BiometricPrompt.ERROR_CANCELED) {
                        runOnUiThread{
                            Toast.makeText(activity,"Authentication failed! Please try again.", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    } else {
                        TODO("Called when an unrecoverable error has been encountered and the operation is complete.")
                    }
                }

                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    runOnUiThread{
                        this@MainActivity.startActivity(Intent(activity,HomeActivity::class.java))
                        finish()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                }
            })


        val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication prompt!")
            /*Subtitle and description are optional parameters, so, you can skip those parameters.
            .setSubtitle("Set the subtitle to display.")
            .setDescription("Verification required")*/
            .setNegativeButtonText("Cancel")
            .build()


        btnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
        btnAuth.performClick();
    }
}
