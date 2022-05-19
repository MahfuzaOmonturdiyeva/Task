package uz.gita.task.viewModel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.task.data.common.MyResult
import uz.gita.task.data.model.request.LoginRequest
import uz.gita.task.repository.Repository
import uz.gita.task.viewModel.LoginViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val repo: Repository
) : LoginViewModel, ViewModel() {

    override val openMainLiveData = MutableLiveData<Unit>()
    override val messageLiveData = MutableLiveData<String>()
    override val errorLiveData = MutableLiveData<String>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val isVisiblePasswordLiveData = MutableLiveData<Boolean>()

    override fun onLogin(data: LoginRequest) {
        viewModelScope.launch {
            progressLiveData.value = true
            val result = repo.login(data)
            progressLiveData.value = false
            when (result) {
                is MyResult.Success -> {
                    openMainLiveData.postValue(Unit)
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

    override fun onVisiblePassword(isVisiblePassword: Boolean) {
        isVisiblePasswordLiveData.value=isVisiblePassword
    }
}