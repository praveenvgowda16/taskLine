package com.example.project

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class TaskListAdapter(private val list: ArrayList<Task>, private val context: Context) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {





    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var u_id: String? = null
        var token: String? = null

        fun bindView(task: Task) {
            var name: TextView = itemView.findViewById(R.id.task_name) as TextView
            var description: TextView = itemView.findViewById(R.id.description) as TextView
            var priority: TextView = itemView.findViewById(R.id.priority) as TextView
            var due_date: TextView = itemView.findViewById(R.id.due_date) as TextView
            var assignee: TextView = itemView.findViewById(R.id.assignee) as TextView
            var reportto: TextView = itemView.findViewById(R.id.reportTo) as TextView
            var progress: TextView = itemView.findViewById(R.id.progress) as TextView

            var pri = task.priority
            println(pri)
            name.text = task.name
            description.text = task.description
            priority.text = pri.toString()
            due_date.text = task.due_date
            assignee.text = task.assignee
            reportto.text = task.report_to
            progress.text = task.progress


        }
    }

        override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.list_row, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bindView(list[position])
        }

    }
