package com.example.weather.network.api

import okhttp3.ResponseBody

class ResponseError{

    var throwable:Throwable? = null
    var message : String? = null
    var timestamp:Long = 0L
    var code = 0
    var errorBody: ResponseBody? = null
    var errorType: ErrorType = ErrorType.NONE


    constructor(msg:String?, error:ErrorType){
        this.message = msg
        this.errorType = error

    }

    constructor(code:Int, timestamp:Long, message: String?){
        this.code = code
        this.message = message
        this.timestamp = timestamp
    }


    constructor(code:Int, timestamp:Long, errorBody: ResponseBody?, throwable: Throwable?){
        this.code = code
        this.errorBody = errorBody
        this.timestamp = timestamp
        this.throwable = throwable
    }
}