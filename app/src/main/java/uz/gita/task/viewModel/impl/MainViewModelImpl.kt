package uz.gita.task.viewModel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.task.data.common.MyResult
import uz.gita.task.data.model.response.ProductResponse
import uz.gita.task.repository.Repository
import uz.gita.task.viewModel.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repo: Repository
) : MainViewModel, ViewModel() {

    override val joinProductsLiveData = MutableLiveData<List<ProductResponse>>()
    override val messageLiveData = MutableLiveData<String>()
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val openProductLiveData = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            progressLiveData.value = true
            val result = repo.getAllProducts()
            progressLiveData.value = false
            when (result) {
                is MyResult.Success -> {
                    joinProductsLiveData.postValue(result.data)
                }
                is MyResult.Error -> {
                    errorLiveData.postValue(result.data)
                }
                is MyResult.Message -> {
                    messageLiveData.postValue(result.data)
                }
            }
        }
    }

    override fun onClickItem(id: Int) {
        openProductLiveData.value = id
    }
}