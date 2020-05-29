package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.utils

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.R
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SatScores

// Used to remove the latitude and longitude from the location string
@BindingAdapter("schoolLocation")
fun TextView.schoolLocation(location: String) {
    val startIndex = location.indexOf("(")
    val endIndex = location.indexOf(")")
    val toReplace = location.substring(startIndex - 1, endIndex + 1)
    text = location.replace(toReplace, "")
}

// If no email is detected, say so
@BindingAdapter("schoolEmail")
fun TextView.schoolEmail(email: String?) {
    if (email == null || email.isEmpty()) {
        text = context.getString(R.string.no_email_found)
        typeface = Typeface.create(typeface, Typeface.ITALIC)
    } else {
        text = email
    }
}

// If no phone number is detected, say so
@BindingAdapter("schoolPhone")
fun TextView.schoolPhone(phone: String?) {
    if (phone == null || phone.isEmpty()) {
        text = context.getString(R.string.no_phone_number_found)
        typeface = Typeface.create(typeface, Typeface.ITALIC)
    } else {
        text = phone
    }
}

// If no website is detected, say so
@BindingAdapter("schoolWebsite")
fun TextView.schoolWebsite(url: String?) {
    if (url == null || url.isEmpty()) {
        text = context.getString(R.string.no_website_found)
        typeface = Typeface.create(typeface, Typeface.ITALIC)
    } else {
        text = url
    }
}

// Hide the school overview section if one does not exist
@BindingAdapter("schoolOverviewVisibility")
fun TextView.schoolOverviewVisibility(overview: String?) {
    if (overview == null || overview.isEmpty()) {
        visibility = View.GONE
    }
}

// Hide the SAT score section if no scores are found
@BindingAdapter("scoreVisibility")
fun View.scoreVisibility(scores: SatScores?) {
    if (scores == null) {
        visibility = View.GONE
    }
}