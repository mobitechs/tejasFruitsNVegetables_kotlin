package com.mobitechs.tejasfruitsnvegetables.callbacks

interface ApiResponse {

    fun onSuccess(data: Any, tag: String)
    fun onFailure(message:String)
//    fun onSuccess(response: JSONObject, tag: String)

}