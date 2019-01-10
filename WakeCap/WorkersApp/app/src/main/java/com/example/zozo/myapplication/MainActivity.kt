package com.example.zozo.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var myDataset: Array<String> = arrayOf("dan", "anca", "fff", "sdsdsd", "sdsd" , "sdsd" , "sdsd" , "sdsd" , "sdsd" , "sdsd" , "sdsd" )

        viewManager = LinearLayoutManager(this)
        viewAdapter = WorkersAdapter(myDataset,this)

        recyclerView = findViewById<RecyclerView>(R.id.workerList).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
            //val textView = findViewById<TextView>(R.id.test)

            val url = "https://pilot.wakecap.com/api/sites/10010001/workers\n"

            /*  val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, null,
            Response.Listener { response ->
                textView.text = "Response: %s".format(response.toString())
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                textView.text="error"
            }
        )
*/
            val jsonObjReq = object : JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    Log.v("result", "Response: %s".format(response.toString()))
                    //  textView.text = "Response: %s".format(response.toString())
                },
                Response.ErrorListener { error ->
                    //textView.text="error"
                    Log.v("result", "error")


                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put(
                        "Authorization",
                        "Bearer eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiUlNBLU9BRVAiLCJraWQiOiIzaGltWWEyYmJHS18zSVdZMUxUUHdyTUl2ZkF3ZVhZQmRNLTU5XzN6c0MwIn0.DIwk0Q215_M7rt3iGfyRVrImhKCXvEB5NBoLQj6iqBnN9yCWWBhGW9fmX6beOajtWgC8gpupe1hJ-iurFRbo2Eg52Nh9ljkkO5Mz8ulSpH7YlfP8Yi-TBrNoPIf_IvHrr1Rt8bG0w2Ie1Jo3BBOguMQ6Q2WI4fQDY-oFIluxOQ5t9E0_XvDhna7maiQnWevLbu80Mj7g643v5nvNWOdpSAXW91rZXIXdal_A_ffY-IUyvYhEJuOkyHWwNonuGjiGHWKPTjTR16w3seoSs4srkacOIN1cmMdXKlkvlWSYCv5Nd8xJ_rTqA4GIyKLRSALYcuiTFMOIxeMIpPB5DjpMTOmOD72QnD8MHMx-SNHk2Yva5iPs__h_WXIKzjME6wRQxTBayfWcXuA6loTdSXnV5Tfl63y62nYZUesh2ElcF5ZK_if3R70jTh-_dxAc8-EcxagAgeCAYNGx-i0fokA4Crfz0A9MKJv5XKn8hY-_4_AT-tsIwBQ6YWYISPBGqyUFoBX0LGDEojIywQBXcLSqHRoah45IDW87TTjVTtPjKsxrIr9rl661dYKRE4ViTLGON0hRBmmNAOokcTNv4tEPBWDS5OPIVtnqkCruAV1YXGKMWpbGOlH6pi2sLY5FtNP9h7nNKnK59gX_FMwXyLjzdPU7HkEEv2svTHN_vBFDUeE.UmwA_rn0FGSRXl4kVPrF3Q.lpZ6JQSke2wLCi3X18mNMAYs5x2nCk7LGrs65nOo-cv8FQ1zfYz9qj7TdH26BxmIrtJiBlLkLhJJnWmSjceNsQnCaOFt36cStJKTHbRkydezuBJyIgvlQH-8Y3nTXLpIbo3JQjxTJ-lUwmY8bLnyxS91DaKEjmip3DoXJLSa4uOivuC68npCCtPVFeHbdHrzS_wQTRsUEh1o0XtWI1TtW868mV3njXwtjHdKxp7Uk8wfY5i5R4qJHxXWgf8PuXJA99Hh1Jwzee_tuA67wnPxvKf95mFkZVYZdDqNKrFkDE9B7-r5FjLi3pQZdTMXjFBGAMv20jrM5BuvCZLP9p6ugbG5pOBRUjBT5RtQpw48_th6pTa4W36MkBkh5TbXS27yuOXvi-b3blcSCEiizEDcJvqm4DBwqCNlSR8iG_QgktFDzkG99--LHV4R956K6cAozVAeyv0Jo9mKfaTsRLlMNhfTFpPrZmR5hVb11CEmmLi9C84aK1eivlYOZenzdZKpYE2mBKjZXOl96PJ3jb-OwEO2MDw9fyGzez8NaVhWAGGw9rm1iXnQyGljOw_ufOr5GLPHXXMhjSGVpXbN65sCTv8p6XGZnJiW7otVUzzmxG_kYQ3tkFLTyG9p6SG3GGBDVYG_pkDpPWavWlQdplIDTsOrjoXu-8sAYTx6GO_rGL13haW7KY9pRi9v3324zkNYOYL8CWWZzjcm26607lTEhpVWvkP4QwisGjvrAMoyj-GnlKvyK1u-1sPcuRMUg-8AKRYzBdtlChoUOtdVwYsOHxiLAkRMBH4PRIqWyUNILmlugXGNA7umZxj8uWy2mKjpZ7M8D_YlR66tj4duARp0lJYfeuvDqbXABVuSoLs8qaEFuYXLfiTNH4zItNeMQjfzT_eh9S_ligNALdOb82FgZzYv5ZKbFMo2s9kwzAb4PdwyaZjIP4_UJiJBqOazslWJYpdMLY2ZHGwfg0ZFP3adV06hbR1i9bJ7lyew2Dalqh9Sq-cFPg32-6rbZXYJnFFzfMks4FOJCgPOhCz2QODdbZU8_nQp_EQ4mIXnD2BR6BcCa3odD8rliM3IYOWnq3AhCHen55FwygCw6-u61Q03m4httHFozE-x4YiQivTQ70YUmdYNMyU_chQ3WfiJzaOgEwjq8vIP0hasxiaE1fBG7PncjGCUNB9sFiyVYjmSbP68iIQFqQ10oo9hnMYVxs_nSdem23fDNOutoikzjSHPo4_qxg-hNV0GDIrzBPLGsGw_W9agnUQOpwtpYycZ9v472FWhI5z0c8-8U7D9cV9s0ELyO9U7_vHhSoZJCq1edIFVVKTHsVfR2-vOlCHuqlbH4TzqGtIzf-5nUuY7HQ4iIxdjOrfU2GtMaU_tB8v3ZsC2Nr7IMYeyeBhItOXccNnqHUhMyZ-LdHkoafendmWTnnMOlkK2l91Jil4tPrJyVn1YFlsbzobQOurrsz2MAW3Ew1ibTCsRvlKWRs8dOSAxoUBcQL-r9g5_BcF0wsV7xzWvIHFm7fOuqLGsjsrCMAuXKImA2tFYECpr6vqCq8ORdqapUOlMb0_K4Iae-AhEkbMVERgLS3O05tRhQATMl2jnqA71DImzDoQMC_c45ELXToqX7x0oiINsOLQKBFQjiQ4-G9C1DJlZ67ZVtSN2PyKVNqMNegEtSz1uieOmkoXa1Vq62CrDp7KORToTHMZEV9uWe7KAOzqYm7jwhEvFs2jgvSga9Au2YgzqxbBjpC8RDSQqhUZTQeg2gecFLRpIObQ._YXzEw0gT4HbLqcFfdWoFQ"
                    )
                    headers.put("Content-Type", "application/json")
                    headers.put("X-Requested-With", "XMLHttpRequest")

                    return headers
                }
            }

// Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjReq)
        }

}
