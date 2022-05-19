package uz.gita.task.data.common

sealed class MyResult<T> {
    class Success<T>(val data: T) : MyResult<T>()
    class Message<T>(val data: String) : MyResult<T>()
    class Error<T>(val data: String) : MyResult<T>()
}