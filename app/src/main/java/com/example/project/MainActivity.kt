package com.example.project


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(){

    lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        log_in_button.setOnClickListener {

            if (user_id.text.toString().trim().isNotEmpty() && password.text.toString().trim().isNotEmpty()) {
                this.login()
            }else{
                Toast.makeText(this,"fill the fields!! Dude",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun login() {
        var url = "https://task-line.herokuapp.com/login"
        val jsonObject = JSONObject()
        jsonObject.put("username", "${user_id.text}")
        jsonObject.put("password", "${password.text}")


        // Volley post request with parameters
        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                // Process the json
                try {
                     if (response != null){
                         token = response["token"].toString()
                         saveData()
                         var intent = Intent(this, TaskType::class.java)
                         intent.putExtra("user_id",response["id"].toString())
                         intent.putExtra("token",response["token"].toString())
//                         println(response["id"])
                         startActivity(intent)
                         //Toast.makeText(this,"Logged in succesfully",Toast.LENGTH_LONG).show()

                     }


                } catch (e: Exception) {
                    Log.d("Log in Exception:", e.toString())
                }

            }, Response.ErrorListener {
                // Error in request
//                textView.text = "Volley error: $it"
                Toast.makeText(this,"Log in unsuccesfull",Toast.LENGTH_LONG).show()
            })


        // Volley request policy, only one time request to avoid duplicate transaction
        request.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
            // 0 means no retry
            0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
            1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        // Add the volley post request to the request queue
        VolleySingleton.getInstance(this).addToRequestQueue(request)

    }
    fun saveData() {
        val mypref = getSharedPreferences("mypref",Context.MODE_PRIVATE)

        val editor = mypref.edit()

        editor.putString("user_id",user_id.text.toString())
        editor.putString("token_id",token)

        editor.apply()

        Toast.makeText(this,"Data Saved",Toast.LENGTH_LONG).show()
    }
}

