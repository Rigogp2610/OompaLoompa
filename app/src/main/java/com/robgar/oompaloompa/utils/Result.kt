package com.robgar.oompaloompa.utils

import com.robgar.oompaloompa.utils.Status.SUCCESS;
import com.robgar.oompaloompa.utils.Status.ERROR;
import com.robgar.oompaloompa.utils.Status.LOADING;

sealed class Result<out T: Any>(val status: Status, val data: T?, val message: String? = "") {

    data class Success<out T: Any>(val _data: T) : Result<T>(SUCCESS, _data)
    data class Error<out T: Any>(val _data: T?, val _message: String) : Result<T>(ERROR, _data, message = _message)
    data class Loading<out T: Any>(val _data: T?) : Result<T>(LOADING, _data)
}