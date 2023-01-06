package com.example.testsmartapps.presentation.viewmodels

import androidx.lifecycle.*
import com.example.testsmartapps.data.database.WebDataDao
import com.example.testsmartapps.data.database.WebDataModel
import com.example.testsmartapps.data.network.WebViewNetwork
import kotlinx.coroutines.launch

class WebViewViewModel(
    private val webDataDao: WebDataDao
): ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    private lateinit var dataList: List<WebDataModel>

    fun getWebData() {
        viewModelScope.launch {
            dataList = getWebDataFromDatabase()
            try {
                val result = WebViewNetwork.webview.getWebData()
                _result.value = result
                if (dataList.isNotEmpty()) {
                    updateWebData(result)
                } else {
                    insertWebData(result)
                }
            } catch (e: Exception) {
                _result.value = "false"
            }

        }
    }

    private suspend fun getWebDataFromDatabase(): List<WebDataModel> {
        return webDataDao.getWebData()
    }

    private suspend fun updateWebData(webData: String) {
        webDataDao.updateWebdata(
            WebDataModel(
                id = 1,
                webviewDate = webData
            )
        )
    }

    private suspend fun insertWebData(webData: String) {
        if (result.value != "false") {
            webDataDao.insertWebData(
                WebDataModel(
                    id = 1,
                    webviewDate = webData
                )
            )
        }
    }

    class WebViewViewModelFactory(private val webDataDao: WebDataDao): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WebViewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WebViewViewModel(webDataDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}