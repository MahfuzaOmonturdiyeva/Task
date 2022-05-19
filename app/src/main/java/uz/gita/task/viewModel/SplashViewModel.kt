package uz.gita.task.viewModel

import androidx.lifecycle.LiveData

interface SplashViewModel {

    val openMainLiveData: LiveData<Unit>

    val openLoginLiveData: LiveData<Unit>

    fun init()
}