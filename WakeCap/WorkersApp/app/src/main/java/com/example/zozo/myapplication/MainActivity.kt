package com.example.zozo.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.support.design.widget.Snackbar
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException



class MainActivity : AppCompatActivity() {
    //Views
    private lateinit var recyclerView: RecyclerView
    //Data
    private lateinit var data:ArrayList<Workers>
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    //WS
    val url = "https://pilot.wakecap.com/api/sites/10010001/workers"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getData()
        }
    fun init()
    {
        var myDataset: Array<String> = arrayOf("dan", "anca", "fff", "sdsdsd", "sdsd" , "sdsd" , "sdsd" , "sdsd" , "sdsd" , "sdsd" , "sdsd" )
        data= arrayListOf()
        viewManager = LinearLayoutManager(this)
       /* viewAdapter = WorkersAdapter(myDataset,this)

        recyclerView = findViewById<RecyclerView>(R.id.workerList).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }*/
    }
    fun getData()
    {


        val queue = Volley.newRequestQueue(this)
        val jsonObjReq = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                Log.v("result", "Response: %s".format(response.toString()))
                // Process the JSON
                try {
                    val jsonArray:JSONArray = response.getJSONObject("data").getJSONArray("items")
                    // Loop through the array elements
                    for (i in 0 until jsonArray.length()) {
                         var jObject:JSONObject=jsonArray.getJSONObject(i).getJSONObject("attributes");
                        data.add(Workers(jObject.getString("full_name"),jObject.getString("role")
                            ,jObject.getString("contractor"),jObject.getString("updated_at")))
                    }
                    viewAdapter = WorkersAdapter(data,this)

                    recyclerView = findViewById<RecyclerView>(R.id.workerList).apply {
                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        setHasFixedSize(true)

                        // use a linear layout manager
                        layoutManager = viewManager

                        // specify an viewAdapter (see also next example)
                        adapter = viewAdapter
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            },
            Response.ErrorListener { error ->
                //textView.text="error"
                Log.v("result", "error")
               /* Snackbar.make(recyclerView,
                    getString(R.string.server_error),
                    Snackbar.LENGTH_LONG
                ).show();*/
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization",getString(R.string.auth_token))
                headers.put("Content-Type", "application/json")
                headers.put("X-Requested-With", "XMLHttpRequest")
                return headers
            }
        }
        queue.add(jsonObjReq)
    }
}
