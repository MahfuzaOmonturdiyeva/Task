package uz.gita.task.viewModel

import androidx.lifecycle.LiveData
import uz.gita.task.data.model.response.ProductResponse

interface MainViewModel : SuperViewModel {

    val joinProductsLiveData: LiveData<List<ProductResponse>>

    val openProductLiveData: LiveData<Int>

    fun onClickItem(id: Int)

}