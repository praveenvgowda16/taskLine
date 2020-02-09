package com.example.project

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.project.TaskList as TaskList1

class TeamAdapter(private val list: ArrayList<Team>,
                  private val context: Context) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    var u_id:String? = null
    var token_id:String?= null
    lateinit var array:Array<String>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(team:Team){
            var name :TextView = itemView.findViewById(R.id.task_name) as TextView
            var description : TextView = itemView.findViewById(R.id.description) as TextView
            var t_id = team.id

            name.text = team.name
            description.text = team.description

            itemView.setOnClickListener{
                retrieveData()
                var url = "https://task-line.herokuapp.com/User/$u_id/tasks/team/$t_id?key=$token_id"
                var intent= Intent(context, com.example.project.TaskList::class.java)
                intent.putExtra("url",url)
                context.startActivity(intent)
//                println("huhuhuhuhuhuhuhuh")
//                var taskList = TaskList1()
//               // println("lalalalalalalalalalal")
//
//                println("$u_id @@@@@@@@@@@@@@, $t_id  $token_id")
//                taskList.getallTask(url)
                Toast.makeText(context,name.text,Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.team_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(list[position])
    }

    fun retrieveData() {
        val mypref = context.getSharedPreferences("mypref", Context.MODE_PRIVATE)
        u_id = mypref.getString("user_id","").toString()
        token_id = mypref.getString("token_id","").toString()
        println(u_id)
        println(token_id)

    }



}