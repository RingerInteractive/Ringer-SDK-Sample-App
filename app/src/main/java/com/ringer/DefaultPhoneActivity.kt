package com.ringer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ringer.interactive.askSDK.offerReplacingDefaultDialer

class DefaultPhoneActivity : AppCompatActivity() {

    lateinit var btn_default: Button
    lateinit var txt_privacy1: TextView
    lateinit var txt_terms_condition: TextView
    val PERMISSIONS_REQUEST_CALL_LOG = 101


    companion object {
        val REQUEST_CODE_SDK = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_phone)

        initialize()

        onClick()
    }

    private fun onClick() {
        btn_default.setOnClickListener {

            offerReplacingDefaultDialer(
                this, applicationContext.packageName,
                REQUEST_CODE_SDK
            )

        }


        txt_terms_condition.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.terms_url)))
            startActivity(browserIntent)
        }

        txt_privacy1.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.privacy_url)))
            startActivity(browserIntent)
        }
    }

    private fun initialize() {
        btn_default = findViewById(R.id.btn_default)
        txt_privacy1 = findViewById(R.id.txt_privacy1)
        txt_terms_condition = findViewById(R.id.txt_terms_condition)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("resultCode",resultCode.toString())
        if (requestCode == REQUEST_CODE_SDK) {
            if (resultCode == RESULT_OK) {
                PreferencesApp().setScreenNumber(this, 2)
//                PreferencesApp().setAppearOnTop(this,false)
//                PreferencesApp().setContact(this,false)
                /*InitializeToken(
                    this,
                    resources.getString(R.string.ringer_user_name),
                    resources.getString(R.string.ringer_password),
                    resources.getString(R.string.app_name)
                )*/
                startActivity(Intent(this@DefaultPhoneActivity, AccessContactActivity::class.java))
                finish()
            } else if (requestCode == PERMISSIONS_REQUEST_CALL_LOG) {
                PreferencesApp().setScreenNumber(this, 2)
                startActivity(Intent(this@DefaultPhoneActivity, AccessContactActivity::class.java))
                finish()
            } else {
                Log.e("defaultApp", "no");
//                askCallLogPermission();
                startActivity(Intent(this@DefaultPhoneActivity, AccessContactActivity::class.java))
                finish()
                askBluetoothPermission()
            }

        }
        if (requestCode == 5){
            if (resultCode == RESULT_OK){
                PreferencesApp().setScreenNumber(this, 2)
                startActivity(Intent(this@DefaultPhoneActivity, AccessContactActivity::class.java))
                finish()
            }
        }
    }

    private fun askBluetoothPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT),
                5)
        }else{
            startActivity(Intent(this@DefaultPhoneActivity, AccessContactActivity::class.java))
            finish()
        }
    }

   /* private fun askCallLogPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            //Request Permission to Continue

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS
                ),
                PERMISSIONS_REQUEST_CALL_LOG
            )
        } else {
            askBluetoothPermission()
            *//*startActivity(Intent(this@DefaultPhoneActivity, AppearOnTopActivity::class.java))
            finish()*//*
        }
    }*/
}