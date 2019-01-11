package com.example.zozo.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ProgressBar
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zozo.myapplication.Adapters.WorkersAdapter
import com.example.zozo.myapplication.DataModel.Workers
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException



class MainActivity : AppCompatActivity() {
    //Views
    private lateinit var recyclerView: RecyclerView
    private lateinit var PB:ProgressBar
    //Data
    private lateinit var data:ArrayList<Workers>
    private lateinit var recycleViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recycleViewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        getData()
        }
    fun init()
    {
        data= arrayListOf()
        recycleViewManager = LinearLayoutManager(this)
        PB=findViewById(R.id.workerListPB)
    }
    public fun openRoleSections(view:View)
    {
        val intent = Intent(this,RolesSect::class.java)
        startActivity(intent)
    }
    fun getData()
    {
        val queue = Volley.newRequestQueue(this)
        val jsonObjReq = object : JsonObjectRequest(
            Method.GET, getString(R.string.url), null,
            Response.Listener<JSONObject> { response ->
                Log.v("result", "Response: %s".format(response.toString()))
                // Process the JSON
                try {
                    val jsonArray:JSONArray = response.getJSONObject("data").getJSONArray("items")
                    // Loop through the array elements
                    for (i in 0 until jsonArray.length()) {
                        var jObject:JSONObject=jsonArray.getJSONObject(i).getJSONObject("attributes");
                        data.add(
                            Workers(
                                jObject.getString("full_name"), jObject.getString("role")
                                , jObject.getString("contractor"), jObject.getString("updated_at")
                            )
                        )
                    }
                    recycleViewAdapter = WorkersAdapter(data, this)

                    recyclerView = findViewById<RecyclerView>(R.id.workerList).apply {
                        layoutManager = recycleViewManager
                        adapter = recycleViewAdapter
                    }
                    PB.setVisibility(View.GONE)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            },
            Response.ErrorListener { error ->
                Log.v("result", "error")
                Snackbar.make(PB,
                    getString(R.string.server_error),
                    Snackbar.LENGTH_LONG
                ).show();
                PB.setVisibility(View.GONE)

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
