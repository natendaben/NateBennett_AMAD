package com.example.flame.ui.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.flame.R
import com.example.flame.data.Habit


class HabitRecyclerAdapter(var habitList: List<Habit>, val itemListener: HabitItemListener): RecyclerView.Adapter<HabitRecyclerAdapter.ViewHolder>() {
    //custom view holder
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val streakDaysTextView = itemView.findViewById<TextView>(R.id.streakDaysTextView)
        val streakLabelTextView = itemView.findViewById<TextView>(R.id.streakLabelTextView)
    }

    //inflate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.habit_grid_item, parent, false)
        return ViewHolder(view)
    }

    //count for recyclerView
    override fun getItemCount() = habitList.count()

    //fill data and attach onClickListener
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHabit = habitList[position]

        holder.streakDaysTextView.text = currentHabit.numberOfDaysActive.toString()
        holder.streakLabelTextView.text = currentHabit.name

        holder.itemView.setOnClickListener {
            itemListener.onHabitItemClick(currentHabit)
        }
    }

    //interface to be implemented in StreaksFragment
    interface HabitItemListener{
        fun onHabitItemClick(habit: Habit)
    }
}


