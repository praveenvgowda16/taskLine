package com.example.project

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_task_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Task {
    var id : Int ?= null
    var name : String ?= null
    var priority : String ?= null
    var description : String ?= null
    var progress : String ?= null
    var due_date : String ?= null
    var assignee : String ?= null
    var report_to : String ?= null
    var safe_delete : Boolean ?= null

    public fun logout(thi : Context , url:String) {
        val logoutRequest = JsonObjectRequest(
            Request.Method.GET
            ,url,null,
            Response.Listener {
                    response : JSONObject ->
                try {
                    Log.d("response: ",response.toString())
                    //loooping for card data
                    Toast.makeText(thi, "Logged out successfully", Toast.LENGTH_LONG).show()
                }
                catch (e : JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                    error : VolleyError? ->
                try {
                    Log.d("Logout Error:",error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })
        var volleyRequest = Volley.newRequestQueue(thi)
        VolleySingleton.getInstance(thi).addToRequestQueue(logoutRequest)
        volleyRequest!!.add(logoutRequest)

    }
}