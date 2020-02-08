package com.example.project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_task_type.*

class TaskType : AppCompatActivity() {

        var url: String? = null
//        var id: String? = null
//        var token: String? = null
        lateinit var u_id : String
        lateinit var token_id : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_type)

        retrieveData()

        var data = intent.extras
        print("13132246 $data")


//        if (data != null) {
//            id = data.getString("user_id").toString()
//            token = data.getString("token").toString()
//        }

        var intent= Intent(this, TaskList::class.java)




        myTaskButton.setOnClickListener {

                url = "https://task-line.herokuapp.com/User/$u_id/tasks?key=$token_id"
                //print("3333333333 $url")
                intent.putExtra("url",url)
                startActivity(intent)
            }


        teamTaskButton.setOnClickListener{

            url = "https://task-line.herokuapp.com/User/$u_id/tasks/team?key=$token_id"
            //print("3333333333 $url")
            startActivity(intent)
        }

        allTaskButton.setOnClickListener {

            url = "https://task-line.herokuapp.com/User/$u_id/tasks/all?key=$token_id"
            intent.putExtra("url",url)
            startActivity(intent)

        }

        createTaskButton.setOnClickListener{
            var i = Intent(this,CreateTask::class.java)
            i.putExtra("id",u_id)
            i.putExtra("token", token_id)
//            println("###############$id,$token")
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_type_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.item1 -> {
                Toast.makeText(this,"item1 is selected",Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
    fun retrieveData() {
        val mypref = getSharedPreferences("mypref",Context.MODE_PRIVATE)
        u_id = mypref.getString("user_id","").toString()
        token_id = mypref.getString("token_id","").toString()
        println(u_id)
        println(token_id)
    }
}
