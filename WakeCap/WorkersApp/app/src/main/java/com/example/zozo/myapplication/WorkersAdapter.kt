package com.example.zozo.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.custom_item.view.*

class WorkersAdapter(val items : ArrayList<Workers> ,
val context: Context): RecyclerView.Adapter<ViewHolder>() {


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_item,null, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.nameText?.text = items.get(position).name
        holder?.roleText?.text = items.get(position).role
        holder?.contractorText?.text = items.get(position).contractor

    }

}
class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val nameText = view.nameTV
    val roleText = view.roleTV
    val contractorText = view.contractorTV

}