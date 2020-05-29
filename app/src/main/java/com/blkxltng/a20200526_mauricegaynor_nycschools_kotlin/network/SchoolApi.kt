package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.network

import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SatScores
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SchoolInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit interface we'll use to get the info and scores for the schools
interface SchoolApi {
    @GET("s3k6-pzi2.json")
    fun getSchools(): Observable<List<SchoolInfo>>

    @GET("f9bf-2cp4.json")
    fun getScores(@Query("dbn") dbn: String?): Observable<List<SatScores>>
}