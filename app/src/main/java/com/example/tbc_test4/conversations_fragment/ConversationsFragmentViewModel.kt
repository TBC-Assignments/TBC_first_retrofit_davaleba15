package com.example.tbc_test4.conversations_fragment

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_test4.repository.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ConversationsFragmentViewModel : ViewModel() {

    private val _itemFlow = MutableStateFlow<List<Conversation>>(emptyList())
    val itemFlow: SharedFlow<List<Conversation>> = _itemFlow.asStateFlow()

    init {
        getAllData()
    }

    fun search(text: String) {
        viewModelScope.launch {
            RetrofitInstance.apiService.getData()
                .enqueue(object : retrofit2.Callback<List<Conversation>> {
                    override fun onResponse(
                        call: Call<List<Conversation>>,
                        response: Response<List<Conversation>>
                    ) {
                        _itemFlow.value = response.body()?.toMutableList()?.apply {
                            for (i in response.body()!!) {
                                if (!i.owner.contains(text)) {
                                    remove(i)
                                }
                            }
                        }!!
                    }

                    override fun onFailure(call: Call<List<Conversation>>, t: Throwable) {
                        d("response body", "${t.message}")
                    }
                })
        }
    }

    private fun getAllData() {
        viewModelScope.launch {
            RetrofitInstance.apiService.getData()
                .enqueue(object : retrofit2.Callback<List<Conversation>> {
                    override fun onResponse(
                        call: Call<List<Conversation>>,
                        response: Response<List<Conversation>>
                    ) {
                        d("response body", "first time all data")
                        _itemFlow.value = response.body()!!.toMutableList()
                    }

                    override fun onFailure(call: Call<List<Conversation>>, t: Throwable) {
                        d("Retrofit error", "$t")
                    }
                })
        }
    }
}