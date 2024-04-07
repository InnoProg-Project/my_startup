package com.innoprog.android.di

abstract class ComponentHolder<T : DIComponent> {

    protected open val mode = ComponentHolderMode.GLOBAL_SINGLETON

    private var component: T? = null

    protected abstract fun buildComponent(): T
    protected open fun createComponent(): T = buildComponent()

    fun getComponent(): T = when (mode) {
        ComponentHolderMode.GLOBAL_SINGLETON -> {
            if (component == null) {
                component = createComponent()
            }
            component!!
        }

        ComponentHolderMode.ALWAYS_CREATE_NEW -> createComponent()
    }
}
