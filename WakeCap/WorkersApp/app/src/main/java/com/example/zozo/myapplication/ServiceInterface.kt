package com.example.zozo.myapplication

import org.json.JSONObject

interface ServiceInterface {
    fun wsCall(path: String, completionHandler: (response: JSONObject?) -> Unit)

}