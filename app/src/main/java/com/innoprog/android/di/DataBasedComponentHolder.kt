package com.innoprog.android.di

abstract class DataBasedComponentHolder<T : DIComponent, R : Any> : ComponentHolder<T>() {

    private lateinit var dataForBuild: R

    override fun buildComponent(): T = error("Data for build not found")
    override fun createComponent(): T = buildComponent(dataForBuild)
    protected abstract fun buildComponent(data: R): T

    /**
     * Создает компонент, который в дальнейшем можно получать, не располагая входными данными
     */
    fun createComponent(data: R): T {
        dataForBuild = data
        return super.getComponent()
    }
}