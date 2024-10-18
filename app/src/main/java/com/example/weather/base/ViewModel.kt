package com.example.weather.base

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.route.NavigationAction
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Composable
fun ComposeLayout(
    viewModel: IViewModel,
    sendNavigationAction: (NavigationAction) -> Unit,
    content: @Composable (
        actionEmitter: UIActionEmitter
    ) -> Unit
) {
    val navigationLambda by rememberUpdatedState(sendNavigationAction)

    LaunchedEffect(navigationLambda, viewModel) {
        viewModel.navigationFlow.collect { navigationAction ->
            navigationLambda(navigationAction)
        }
    }

    content(viewModel.actionEmitter)
}

interface IViewModel {
    val navigationFlow: SharedFlow<NavigationAction>
    val actionEmitter: UIActionEmitter
}

abstract class BaseViewModel() : ViewModel(), IViewModel {
    private val _navigationFlow: MutableSharedFlow<NavigationAction> = MutableSharedFlow()
    override val navigationFlow: SharedFlow<NavigationAction> = _navigationFlow.asSharedFlow()

    override val actionEmitter: UIActionEmitter = { uiAction ->
        handleAction(uiAction)
    }

    protected fun performNavigationAction(action: NavigationAction) {
        viewModelScope.launch {
            _navigationFlow.emit(action)
        }
    }

    protected abstract fun handleAction(uiAction: UIAction)

}