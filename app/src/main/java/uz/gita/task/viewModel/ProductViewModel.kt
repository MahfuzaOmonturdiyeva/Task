package uz.gita.task.viewModel

import androidx.lifecycle.LiveData
import uz.gita.task.data.model.response.ProductResponse

interface ProductViewModel : SuperViewModel {

    val joinProductLiveData: LiveData<ProductResponse>

    val backLiveData: LiveData<Unit>

    fun setProduct(id: Int)

    fun onBack()
}