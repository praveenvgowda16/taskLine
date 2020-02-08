package com.example.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*

class TaskListAdapter(private val list: ArrayList<Task>, private val context: Context) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {





    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(task:Task){
            var name :TextView = itemView.findViewById(R.id.task_name) as TextView
            var description : TextView = itemView.findViewById(R.id.description) as TextView

            name.text = task.name
            description.text = task.description

            itemView.setOnClickListener{
                Toast.makeText(context,name.text,Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(list[position])
    }


}