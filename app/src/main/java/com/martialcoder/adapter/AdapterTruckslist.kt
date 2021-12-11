package com.martialcoder.adapter

import androidx.annotation.RequiresApi
import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.martialcoder.model.Data
import com.martialcoder.remotestate.databinding.ItemTruckViewBinding
import java.util.*
import java.util.concurrent.TimeUnit

class AdapterTruckslist(
    private var getList: ArrayList<Data>,
) : RecyclerView.Adapter<AdapterTruckslist.Holder>() {

    class Holder(val binding: ItemTruckViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =

        Holder(ItemTruckViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    @Suppress("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = getList[position]
        holder.binding.model = model
        val date1 = Date(model.lastRunningState.stopStartTime)
        val date2 = Date()
        val diff = date2.time - date1.time
        val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        val hours: Long = days / 3600
        val minutes: Long = days % 3600 / 60
        val seconds: Long = days % 3600 % 60

        val speed = String.format("%.0f", model.lastWaypoint.speed)
        holder.binding.tructkmNo.text = speed
        holder.binding.tructRunningStatus.text = "$days days ago"
        if (model.lastRunningState.truckRunningState == 1) {
            holder.binding.tructkmNo.visibility = View.VISIBLE
            holder.binding.truckkm.visibility = View.VISIBLE
            holder.binding.tructSatus.text = "Running since last $days ago"
        } else {
            holder.binding.tructSatus.text = "Stopped since last $days ago"
            holder.binding.tructkmNo.visibility = View.GONE
            holder.binding.truckkm.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = getList.size

    fun addMoreLinkedList(newLinkedList: ArrayList<Data>) {
        val oldLinkedListSize = getList.size
        if (getList != newLinkedList) {
            getList.addAll(newLinkedList)
            notifyItemRangeInserted(oldLinkedListSize, newLinkedList.size)
        }
    }

    fun initList(initialLinkedList: ArrayList<Data>) {
        getList = initialLinkedList
        notifyDataSetChanged()
    }
}
