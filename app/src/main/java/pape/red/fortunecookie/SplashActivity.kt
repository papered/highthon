package pape.red.fortunecookie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import pape.red.fortunecookie.util.getToken


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startLoading()
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed({
//            if(getToken(this).isNotBlank())
                startActivity(Intent(this,MainActivity::class.java))
//            else
//                startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onBackPressed() {

    }

}