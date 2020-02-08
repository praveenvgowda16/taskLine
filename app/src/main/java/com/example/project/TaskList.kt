package com.example.project

import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.textclassifier.TextLinks
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_task_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class TaskList : AppCompatActivity() {

    private var taskAdapter : TaskListAdapter ?= null
    private var taskList : ArrayList<Task> ?= null
    private var layoutManager : RecyclerView.LayoutManager ?= null

    var volleyRequest : RequestQueue ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        taskList = ArrayList<Task>()
        layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskListAdapter(taskList!!,this)

        // set up list (RecyclerView
        recylerView.layoutManager = layoutManager
        recylerView.adapter = taskAdapter

        volleyRequest = Volley.newRequestQueue(this)

        var data = intent.extras
        if(data != null){
            var url = data.get("url").toString()
            getallTask(url)
        }

    }
//    fun createUrl(id:String,token:String):String{
//        return "https://task-line.herokuapp.com/User/$id/tasks/all?key=$token"
//    }
    fun getallTask(url : String) {
        val taskRequest = JsonArrayRequest(Request.Method.GET
            ,url,null,
            Response.Listener {
                response : JSONArray ->
                try {
                    Log.d("response: ",response.toString())
                    //loooping for card data
                    var resparray = response
                    println("***************  $resparray")
                    for(i in 0 until resparray.length()){

                        var taskobj = resparray.getJSONObject(i)
                        //println(taskobj)


                        var name = taskobj.getString("name")
                        var description = taskobj.getString("description")

                        var task = Task()
                        task.name = name
                        task.description = description

                        taskList!!.add(task)

                        taskAdapter = TaskListAdapter(taskList!!,this)
                        layoutManager  = LinearLayoutManager(this)

                        //set up Recycler view
                        recylerView.layoutManager = layoutManager
                        recylerView.adapter = taskAdapter


                    }
                    taskAdapter!!.notifyDataSetChanged()

                } catch (e : JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { 
                error : VolleyError? ->
                try {
                    Log.d("Error:",error.toString())
                } catch (e:JSONException) {
                    e.printStackTrace()
                }
            })
        VolleySingleton.getInstance(this).addToRequestQueue(taskRequest)
        volleyRequest!!.add(taskRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.sort -> {
                Toast.makeText(this,"sort is selected", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }

            R.id.item1 -> {
                Toast.makeText(this,"log out is selected", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
