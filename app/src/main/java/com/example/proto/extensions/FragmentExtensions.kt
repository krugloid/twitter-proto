package com.example.proto.extensions

import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proto.R

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
