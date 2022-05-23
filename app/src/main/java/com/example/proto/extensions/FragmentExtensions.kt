package com.example.proto.extensions

import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proto.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// FIXME: make a number of menu items flexible
fun Fragment.initToolbar(toolbar: Toolbar, @MenuRes menu: Int, actionId: Int, action: () -> Unit) {
    with(toolbar) {
        setupWithNavController(findNavController())
        setNavigationIcon(R.drawable.ic_back)
        inflateMenu(menu)
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                actionId -> {
                    action()
                    true
                }
                else -> false
            }
        }
    }
}

fun Fragment.initToolbar(toolbar: Toolbar) {
    with(toolbar) {
        setupWithNavController(findNavController())
        setNavigationIcon(R.drawable.ic_back)
    }
}

fun <T> Fragment.bind(to: Flow<T>, observer: (T) -> Unit) =
    viewLifecycleOwner.safeObserve(to, observer)

private fun <T> LifecycleOwner.safeObserve(flow: Flow<T>, observer: (T) -> Unit) {
    this.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { state ->
                observer(state)
            }
        }
    }
}

