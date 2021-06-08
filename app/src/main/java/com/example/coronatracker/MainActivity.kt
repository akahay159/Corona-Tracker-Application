package com.example.coronatracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    lateinit var WorldCasesTV:TextView
    lateinit var WorldRecoveredTV:TextView
    lateinit var WorldDeathTV:TextView
    lateinit var CountryCasesTV:TextView
    lateinit var CountryRecoveredTV:TextView
    lateinit var CountryDeathTV:TextView
    lateinit var stateRV : RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList :List<StateModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WorldCasesTV = findViewById(R.id.WorldCases)
        WorldRecoveredTV = findViewById(R.id.WorldRecoveredCases)
        WorldDeathTV = findViewById(R.id.WorldDeathCases)
        CountryCasesTV = findViewById(R.id.IndianCases)
        CountryRecoveredTV = findViewById(R.id.IndianRecoveredCases)
        CountryDeathTV = findViewById(R.id.IndianDeathCases)
        stateRV = findViewById(R.id.RVStates)
        stateList = ArrayList<StateModel>()
        getStateInfo()
        getWorldInfo()
    }

    private fun getStateInfo(){
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{
            response ->
            try {
                val dataObj = response.getJSONObject("data")
                val summaryObj = dataObj.getJSONObject("summary")
                val cases : Int = summaryObj.getInt("total")
                val recovers : Int = summaryObj.getInt("discharged")
                val deaths : Int = summaryObj.getInt("deaths")

                CountryCasesTV.text = cases.toString()
                CountryRecoveredTV.text = recovers.toString()
                CountryDeathTV.text = deaths.toString()

                val regionArray = dataObj.getJSONArray("regional")
                for(i in 0 until regionArray.length()){
                    val regionalObj = regionArray.getJSONObject(i)
                    val stateName : String = regionalObj.getString("loc")
                    val cases : Int = regionalObj.getInt("totalConfirmed")
                    val recovered : Int = regionalObj.getInt("discharged")
                    val deaths  : Int = regionalObj.getInt("deaths")

                    val stateModel = StateModel(stateName, recovered, deaths, cases)
                    stateList = stateList+stateModel
                }

                stateRVAdapter = StateRVAdapter(stateList)
                stateRV.layoutManager = LinearLayoutManager(this)
                stateRV.adapter = stateRVAdapter

            } catch (e: JSONException){
            e.printStackTrace()
            }
        },{error ->
            Toast.makeText(this,"Failed to Get Data", Toast.LENGTH_LONG).show()
        })
        queue.add(request)

    }

    private fun getWorldInfo(){
        println("Akshay")
        val url = "https://corona.lmao.ninja/v2/all"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{
            response ->
            try {
                val worldcases: Int = response.getInt("cases")
                val worldrecovered : Int = response.getInt("recovered")
                val worlddeaths : Int = response.getInt("deaths")
                println(worldcases)
                WorldCasesTV.text = worldcases.toString()
                WorldRecoveredTV.text = worldrecovered.toString()
                WorldDeathTV.text = worlddeaths.toString()
            } catch (e: JSONException){
                e.printStackTrace()
            }

        },{ error ->
        Toast.makeText(this,"Failed to Get Data", Toast.LENGTH_LONG).show()
    })
        queue.add(request)
    }
}