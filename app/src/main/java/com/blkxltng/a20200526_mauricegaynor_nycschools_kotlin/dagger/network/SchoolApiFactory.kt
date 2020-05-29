package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.dagger.network

import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.network.SchoolApi
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.network.SchoolService
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.ui.main.MainViewModel
import dagger.Component

@Component(modules = [SchoolModule::class])
interface SchoolApiFactory {
    fun provideSchoolApi(): SchoolApi
    fun providesSchoolService(): SchoolService
    fun inject(mainViewModel: MainViewModel)
}