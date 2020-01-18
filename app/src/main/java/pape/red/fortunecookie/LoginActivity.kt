package pape.red.fortunecookie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import pape.red.fortunecookie.util.saveId
import pape.red.fortunecookie.util.saveImage
import pape.red.fortunecookie.util.saveName
import pape.red.fortunecookie.util.saveToken
import java.util.*


class LoginActivity : AppCompatActivity() {


    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        callbackManager = CallbackManager.Factory.create()

        login_button.setOnClickListener {
            val loginManager = LoginManager.getInstance()
            loginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    requestMe(loginResult)
                    saveToken(applicationContext, loginResult.accessToken.token)
                }

                override fun onCancel() {
                    Log.d("test", "앱 꺼짐")
                }

                override fun onError(exception: FacebookException) {
                    Log.d("test_error", exception.message.toString())
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun requestMe(loginResult: LoginResult) {
        val graphRequest = GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { loginObject, response ->
            saveName(applicationContext, loginObject.getString("name"))
            saveId(applicationContext, loginObject.getString("id"))
            saveImage(applicationContext,loginObject.getJSONObject("picture").getJSONObject("data").getString("url"))
            startActivity(Intent(this, MainActivity::class.java))
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,picture.type(large)")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()
    }
}
