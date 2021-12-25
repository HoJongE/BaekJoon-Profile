package per.hojong.baekjoonprofile.utility

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Coroutine Builder that handle error with CoroutineException Handler
 * User can give Error callback or not
 * @property onError Error Callback
 */
class NetworkCoroutine(val onError: (Throwable) -> Unit = { it.printStackTrace() }) {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private val _ioDispatchers = Dispatchers.IO + coroutineExceptionHandler
    private val _uiDispatchers = Dispatchers.Main + coroutineExceptionHandler

    val ioDispatcher = _ioDispatchers
    val uiDispatcher = _uiDispatchers

    /**
     * return Coroutine with ErrorExceptionHandler
     * @return Coroutine with ErrorExceptionHandler
     */
    fun getCoroutineScope(): CoroutineScope {
        return CoroutineScope(_ioDispatchers)
    }
}