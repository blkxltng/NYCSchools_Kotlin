package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.ui.main

import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SchoolInfo

class SchoolViewModel(val info: SchoolInfo, private val mainViewModel: MainViewModel) {

    fun schoolClicked() {
        mainViewModel.clickedSchool.postValue(info)
    }
}