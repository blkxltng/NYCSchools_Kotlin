package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.network

import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SatScores
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SchoolInfo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class SchoolService @Inject constructor(private val schoolApi: SchoolApi) {

    fun getSchools(): Observable<List<SchoolInfo>> {
        return schoolApi.getSchools()
    }

    fun getScores(dbn: String?): Observable<List<SatScores>> {
        return schoolApi.getScores(dbn)
    }
}