package uz.gita.task.viewModel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.task.data.common.MyResult
import uz.gita.task.data.model.response.ProductResponse
import uz.gita.task.repository.Repository
import uz.gita.task.viewModel.ProductViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModelImpl @Inject constructor(
    private val repo: Repository
) : ProductViewModel, ViewModel() {

    override val joinProductLiveData = MutableLiveData<ProductResponse>()
    override val messageLiveData = MutableLiveData<String>()
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val backLiveData = MutableLiveData<Unit>()

    override fun setProduct(id: Int) {
        viewModelScope.launch {
            progressLiveData.value = true
            val result = repo.product(id)
            progressLiveData.value = false
            when (result) {
                is MyResult.Success -> {
                    joinProductLiveData.postValue(result.data)
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

    override fun onBack() {
        backLiveData.value = Unit
    }
}