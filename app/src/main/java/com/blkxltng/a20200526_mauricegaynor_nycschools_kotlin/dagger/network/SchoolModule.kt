package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.dagger.network

import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.network.SchoolApi
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.utils.NYC_API
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object SchoolModule {
    @Provides
    @JvmStatic
    fun schoolApi() = Retrofit.Builder().baseUrl(NYC_API)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(SchoolApi::class.java)
}