package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.ui.main

import com.airbnb.epoxy.TypedEpoxyController
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.itemSchoolCard

class MainEpoxyController : TypedEpoxyController<List<SchoolViewModel>>() {
    override fun buildModels(data: List<SchoolViewModel>) {
        for (item in data) {
            // Assign each school to a card view and add it to the recyclerView
            itemSchoolCard {
                id(item.info.dbn)
                schoolViewModel(item)
            }
        }
    }
}