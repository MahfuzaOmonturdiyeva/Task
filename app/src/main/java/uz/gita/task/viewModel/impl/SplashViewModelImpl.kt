package uz.gita.task.viewModel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.task.data.common.MyResult
import uz.gita.task.repository.Repository
import uz.gita.task.viewModel.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val repo: Repository
) : SplashViewModel, ViewModel() {

    override val openMainLiveData = MutableLiveData<Unit>()
    override val openLoginLiveData = MutableLiveData<Unit>()

    override fun init() {
        viewModelScope.launch {
            when (repo.getAllProducts()) {
                is MyResult.Success -> {
                    openMainLiveData.postValue(Unit)
                }
                is MyResult.Error -> {
                    openLoginLiveData.postValue(Unit)
                }
                is MyResult.Message -> {
                    openLoginLiveData.postValue(Unit)
                }
            }
        }
    }
}