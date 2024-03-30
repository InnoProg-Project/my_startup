package com.innoprog.android.di

abstract class ComponentHolder<T : DIComponent> {

    protected open val mode = ComponentHolderMode.GLOBAL_SINGLETON

    private var component: T? = null

    protected abstract fun buildComponent(): T

    open fun getComponent(): T = when (mode) {
        ComponentHolderMode.GLOBAL_SINGLETON -> {
            if (component == null) {
                component = buildComponent()
            }
            component!!
        }

        ComponentHolderMode.ALWAYS_CREATE_NEW -> buildComponent()
    }
}
