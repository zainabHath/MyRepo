package com.example.zozo.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RolesSect extends AppCompatActivity {
    //Views
    ProgressBar PB;
    RecyclerView workersInSectionList;
     RecyclerView.LayoutManager layoutManager;

    //data
    ArrayList<Workers> workersData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles_sect);
        init();
        getData();
    }
    void init()
    {
        PB=findViewById(R.id.roleSecPB);
        workersData=new ArrayList<>();
        workersInSectionList=findViewById(R.id.roleSecList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        workersInSectionList.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        workersInSectionList.setLayoutManager(layoutManager);
    }
    void getData()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, getString(R.string.url), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("volleyR",response.toString());
                        try{

                            JSONArray jsonArray = response.getJSONObject("data").getJSONArray("items");
                            HashMap <String, ArrayList<String>> rolesArray=new HashMap<>();
                            // Loop through the array elements
                            for (int i=0;i<jsonArray.length();i++)
                            {

                                JSONObject jObject=jsonArray.getJSONObject(i).getJSONObject("attributes");
                                workersData.add(new Workers(jObject.getString("full_name"),jObject.getString("role")
                                        ,jObject.getString("contractor"),jObject.getString("updated_at")));
                                if (rolesArray.containsKey(jObject.getString("role")))
                                {
                                    ArrayList<String> names=rolesArray.get(jObject.getString("role"));
                                    names.add(jObject.getString("full_name"));
                                    rolesArray.put(jObject.getString("role"),names);
                                }
                                else
                                {
                                    ArrayList<String> names=new ArrayList<>();
                                    names.add(jObject.getString("full_name"));
                                    rolesArray.put(jObject.getString("role"),names);
                                }
                            }
                            WorkersSectionAdapter adapter=new WorkersSectionAdapter(workersData.size(),rolesArray);
                            workersInSectionList.setAdapter(adapter);
                            Log.v("volleyR1",rolesArray.size()+"");
                            Log.v("volleyR2",rolesArray.get("Engineer").size()+"");
                            Log.v("volleyR3",workersData.get(0).getName());
                            PB.setVisibility(View.GONE);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
            HashMap headers = new HashMap();
                headers.put("Authorization",getString(R.string.auth_token));
                headers.put("Content-Type", "application/json");
                headers.put("X-Requested-With", "XMLHttpRequest");
            return headers;
        }
        };
        queue.add(jsonObjectRequest);

        /*val jsonObjReq = object : JsonObjectRequest(
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
            PB.setVisibility(View.GONE)
        } catch (e: JSONException) {
        e.printStackTrace()
    }

            },
        Response.ErrorListener { error ->
            //textView.text="error"
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
        queue.add(jsonObjReq)*/
    }



}
