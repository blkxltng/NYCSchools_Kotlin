package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SchoolInfo (
    val dbn: String,
    val school_name: String,
    val overview_paragraph: String,
    val location: String,
    val phone_number: String,
    val school_email: String,
    val website: String,
    val total_students: String,
    val neighborhood: String,
    val borough: String
): Parcelable