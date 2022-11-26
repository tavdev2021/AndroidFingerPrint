package com.example.androidfingerprint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var ivFinger : ImageView
    private lateinit var executor : Executor
    private lateinit var biometricPrompt : BiometricPrompt
    private lateinit var promptinfo : BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivFinger = findViewById(R.id.ivFinger)
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this@MainActivity,executor,object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                MotionToast.Companion.createToast(this@MainActivity,
                    "Autenticacion Fallida ☹️",
                    "Error: $errString",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this@MainActivity,R.font.helvetica_regular))

            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                MotionToast.Companion.createToast(this@MainActivity,
                    "Autenticacion Exitosa 😍",
                    "Autenticacion Completada!",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this@MainActivity,R.font.helvetica_regular))

                val intent = Intent(this@MainActivity,resultActivity::class.java)
                startActivity(intent)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                MotionToast.Companion.createToast(this@MainActivity,
                    "La huella no coincide",
                    "Authentication Fallida",
                    MotionToastStyle.WARNING,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this@MainActivity,R.font.helvetica_regular))
            }
        })

        promptinfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint or face")
            .setNegativeButtonText("Cancelar")
            .build()

        ivFinger.setOnClickListener {
            biometricPrompt.authenticate(promptinfo)
        }
    }
}