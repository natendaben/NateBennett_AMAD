package com.natebennett.flame.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.natebennett.flame.R
import com.natebennett.flame.data.Habit


class HabitRecyclerAdapter(val context: Context, var habitList: List<Habit>, val itemListener: HabitItemListener): RecyclerView.Adapter<HabitRecyclerAdapter.ViewHolder>() {
    //custom view holder
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val streakDaysTextView = itemView.findViewById<TextView>(R.id.streakDaysTextView)
        val streakLabelTextView = itemView.findViewById<TextView>(R.id.streakLabelTextView)
        val container = itemView.findViewById<ConstraintLayout>(R.id.itemContainer)
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

        if(currentHabit.doneForDay){
            holder.streakDaysTextView.text = (currentHabit.numberOfDaysActive+1).toString()
        } else {
            holder.streakDaysTextView.text = currentHabit.numberOfDaysActive.toString()
        }
        holder.streakLabelTextView.text = currentHabit.name

        val resources = context.resources
        var drawableRef: Int
        if(currentHabit.doneForDay){
            drawableRef = when(currentHabit.color){
                "yellow" -> {
                    R.drawable.yellow
                }
                "orange" -> {
                    R.drawable.orange
                }
                "red" -> {
                    R.drawable.red
                }
                "pink" -> {
                    R.drawable.pink
                }
                "purple" -> {
                    R.drawable.purple
                }
                "darkblue" -> {
                    R.drawable.darkblue
                }
                "blue" -> {
                    R.drawable.blue
                }
                "lightblue" -> {
                    R.drawable.lightblue
                }
                "lightgreen" -> {
                    R.drawable.lightgreen
                }
                "green" -> {
                    R.drawable.green
                }
                else -> {
                    R.drawable.rounded_corner
                }
            }
        } else {
            drawableRef = R.drawable.rounded_corner
        }
        holder.container.background = ResourcesCompat.getDrawable(resources, drawableRef, null)

        holder.itemView.setOnClickListener {
            itemListener.onHabitItemClick(currentHabit)
        }
    }

    //interface to be implemented in StreaksFragment
    interface HabitItemListener{
        fun onHabitItemClick(habit: Habit)
    }
}


