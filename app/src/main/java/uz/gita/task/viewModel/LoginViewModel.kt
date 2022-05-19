package uz.gita.task.viewModel

import androidx.lifecycle.LiveData
import uz.gita.task.data.model.request.LoginRequest

interface LoginViewModel : SuperViewModel {

    val openMainLiveData: LiveData<Unit>

    val isVisiblePasswordLiveData: LiveData<Boolean>

    fun onLogin(data: LoginRequest)

    fun onVisiblePassword(isVisiblePassword:Boolean)
}