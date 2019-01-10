package com.example.zozo.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    //data
    ArrayList<Workers> workersData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles_sect);
        init();
        getData();
        invitedTask invitedtask = new invitedTask();
        invitedtask.execute();

    }
    void init()
    {
        PB=findViewById(R.id.roleSecPB);
        workersData=new ArrayList<>();
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
                            Map <String, ArrayList<String>> rolesArray=new HashMap<>();
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
                            Log.v("volleyR1",rolesArray.size()+"");
                            Log.v("volleyR2",rolesArray.get("Engineer").size()+"");
                          //  Log.v("volleyR3",rolesArray.size()+"");

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

    class invitedTask extends AsyncTask<String, Void, String> {
        String result = "";
        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(getString(R.string.url));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Authorization", getString(R.string.auth_token));
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String line;
                StringBuilder total = new StringBuilder();
                while ((line = in.readLine()) != null)
                    total.append(line).append('\n');

                result = total.toString();
                // Log.i("ouput", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // selector query


            // return
            return result;
        }

        protected void onPostExecute(String data) {
              Log.v("nativeR", result);
              PB.setVisibility(View.GONE);
            try{

                    JSONArray jsonArray = new JSONObject(data).getJSONObject("data").getJSONArray("items");
                    // Loop through the array elements
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jObject=jsonArray.getJSONObject(i).getJSONObject("attributes");
                    workersData.add(new Workers(jObject.getString("full_name"),jObject.getString("role")
                            ,jObject.getString("contractor"),jObject.getString("updated_at")));
                }

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    }


}
