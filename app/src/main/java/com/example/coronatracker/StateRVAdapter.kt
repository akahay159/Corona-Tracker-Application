package com.example.coronatracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateRVAdapter (private val StateList:List<StateModel>) :
    RecyclerView.Adapter<StateRVAdapter.StateRVViewHolder>() {

    class StateRVViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        // creating  the views

        val stateNameTV : TextView = itemView.findViewById(R.id.idTVState)
        val casesTV : TextView = itemView.findViewById(R.id.idTVCases)
        val recoveredTV : TextView = itemView.findViewById(R.id.idTVRecoveredCases)
        val deathTV : TextView = itemView.findViewById(R.id.idTVDeathCases)

        // ending of creating of the views

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRVViewHolder {
        // create the Layout Inflator
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateRVViewHolder(itemView)
        // end of create the LayoutInflator
    }

    override fun getItemCount(): Int {
        return StateList.size
    }

    override fun onBindViewHolder(holder: StateRVViewHolder, position: Int) {
        // setting data to each view
        val stateData = StateList[position]
        holder.stateNameTV.text = stateData.state.toString()
        holder.casesTV.text = stateData.cases.toString()
        holder.recoveredTV.text = stateData.recovered.toString()
        holder.deathTV.text = stateData.recovered.toString()
        // end of setting data to each view
    }

}