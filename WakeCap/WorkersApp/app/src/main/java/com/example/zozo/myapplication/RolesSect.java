package com.example.zozo.myapplication;

import android.support.design.widget.Snackbar;
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
import com.example.zozo.myapplication.Adapters.WorkersSectionAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RolesSect extends AppCompatActivity{
    //Views
    ProgressBar PB;
    RecyclerView workersInSectionList;
    RecyclerView.LayoutManager layoutManager;
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
        workersInSectionList=findViewById(R.id.roleSecList);
        workersInSectionList.setHasFixedSize(true);
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
                            WorkersSectionAdapter adapter=new WorkersSectionAdapter(jsonArray.length(),rolesArray);
                            workersInSectionList.setAdapter(adapter);
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
                        Snackbar.make(PB,
                                getString(R.string.server_error),
                                Snackbar.LENGTH_LONG
                        ).show();
                        PB.setVisibility(View.GONE);

                    }
                })
        {
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


    }


}
