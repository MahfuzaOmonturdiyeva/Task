package uz.gita.task.data.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.task.utils.SharedPreference
import javax.inject.Inject

class LocalStorage @Inject constructor(@ApplicationContext context: Context) : SharedPreference(context){

    var token: String by StringPreference("")

}