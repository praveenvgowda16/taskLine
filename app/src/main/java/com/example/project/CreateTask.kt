package com.example.project

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_create_task.*
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONObject

class CreateTask : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        println("@@@@@@@@@@@@@@@@@@@@")

        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        dueDate.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener {view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat)
                dueDate.text = sdf.format(cal.time)

            }

        dueDate.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        var data = intent.extras
        var id = data!!.getString("id").toString()
        var token = data.getString("token").toString()
        println("$id,$token")

        submitButton.setOnClickListener() {

            if (name.text.toString().trim().isNotEmpty() && description.text.toString().trim().isNotEmpty() && assignee.text.toString().trim().isNotEmpty() && reportTo.text.toString().trim().isNotEmpty()) {
                this.createTask(id,token)
                var intent = Intent(this, TaskType::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Fill All the Details", Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun createTask(id:String,token:String) {
        var url = "https://task-line.herokuapp.com/User/$id/tasks/new?key=$token"
        val jsonObject = JSONObject()
        jsonObject.put("name", "${name.text}")
        jsonObject.put("priority", "${priority()}")
        jsonObject.put("description", "${description.text}")
        jsonObject.put("due_date", "${dueDate.text.toString()}")
        jsonObject.put("assignee", "${assignee.text}")
        jsonObject.put("report_to", "${reportTo.text}")

        // Volley post request with parameters
        val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                // Process the json
                try {
                    if (response != null){
                        var intent = Intent(this, TaskType::class.java)
                        intent.putExtra("id", id)
                        intent.putExtra("token" , token)
                        Toast.makeText(this,"Task created succesfully $response",Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    }

                } catch (e: Exception) {
                    Log.d("process unsuccessful", e.toString())
                }

            }, Response.ErrorListener {
                // Error in request
//                textView.text = "Volley error: $it"
                Toast.makeText(this,"process unsuccessful",Toast.LENGTH_LONG).show()
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


    private fun priority(): String {
        return when {
            highPriority.isChecked -> "high"
            mediumPriority.isChecked -> "medium"
            else -> {
                "low"
            }
        }
        }
    }

