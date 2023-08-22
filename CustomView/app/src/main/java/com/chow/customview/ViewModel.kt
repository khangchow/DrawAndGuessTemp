package com.chow.customview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.socket.client.IO

class ViewModel: ViewModel() {
    private val socket = IO.socket("https://5186-171-235-33-18.ngrok-free.app")
    private val _line = MutableLiveData<Line?>()
    val line = _line

    init {
        socket.connect()
        socket.on("NEW_LINE") {
            _line.postValue(Gson().fromJson(it[0].toString(), Line::class.java))
        }
    }

    fun drawNewLine(newLine: Line?) {
        socket.emit("NEW_LINE", Gson().toJson(newLine))
    }
}