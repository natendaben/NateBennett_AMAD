package com.example.lab6.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.R
import com.example.lab6.data.Route

class MainRecyclerAdapter(val context: Context, val routeList: List<Route>, val itemListener: RouteItemListener): RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val routeNameTextView = itemView.findViewById<TextView>(R.id.routeNameTextView)
        val routeGradeTextView = itemView.findViewById<TextView>(R.id.routeGradeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        //attach to xml file
        val view = inflater.inflate(R.layout.route_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = routeList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRoute = routeList[position]

        holder.routeNameTextView.text = currentRoute.name
        holder.routeGradeTextView.text = "${currentRoute.type}, ${currentRoute.rating}"

        holder.itemView.setOnClickListener{
            itemListener.onRouteItemClick(currentRoute)
        }
    }

    interface RouteItemListener{
        fun onRouteItemClick(route: Route)
    }
}