package uz.gita.task.viewModel

import androidx.lifecycle.LiveData

interface SuperViewModel {

    val messageLiveData: LiveData<String>

    val errorLiveData: LiveData<String>

    val progressLiveData: LiveData<Boolean>

}