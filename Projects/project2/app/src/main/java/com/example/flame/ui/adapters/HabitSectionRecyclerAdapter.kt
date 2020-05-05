package com.example.flame.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flame.R
import com.example.flame.TAG
import com.example.flame.data.Habit
import com.example.flame.data.HabitCategory
import com.example.flame.ui.viewModels.MainViewModel


class HabitSectionRecyclerAdapter(var context: Context, var habitCategoryList: List<HabitCategory>, val itemListener: HabitRecyclerAdapter.HabitItemListener): RecyclerView.Adapter<HabitSectionRecyclerAdapter.ViewHolder>(){

    //custom view holder
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val categoryRecyclerViewLabel = itemView.findViewById<TextView>(R.id.categoryTextLabel)
        val categoryRecyclerView = itemView.findViewById<RecyclerView>(R.id.categoryRecyclerView)
    }

    //inflate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.habit_category_group, parent, false)
        return ViewHolder(view)
    }

    //count for recyclerView
    override fun getItemCount() = habitCategoryList.count()

    //fill data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory = habitCategoryList[position]

        holder.categoryRecyclerViewLabel.text = currentCategory.categoryLabel
        val adapter = HabitRecyclerAdapter(context, currentCategory.habitList, itemListener)
        val manager = GridLayoutManager(context, 2)
        holder.categoryRecyclerView.adapter = adapter
        holder.categoryRecyclerView.layoutManager = manager

//        holder.itemView.setOnClickListener {
//            itemListener.onHabitItemClick(currentHabit)
//        }
    }
}


