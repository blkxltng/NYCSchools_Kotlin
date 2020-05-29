package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.ui.main

import android.util.Pair
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.dagger.network.DaggerSchoolApiFactory
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SatScores
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SchoolInfo
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.network.SchoolService
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.utils.LiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class MainViewModel : ViewModel() {

    var progressVisibility = MutableLiveData(View.GONE) // Used to set progressBar visibility
    var schoolInfoList: MutableLiveData<List<SchoolViewModel>> = MutableLiveData<List<SchoolViewModel>>() // Used to hold the list of schools
    var clickedSchool: LiveEvent<SchoolInfo> = LiveEvent() // Used when a user selects a school
    var schoolScores: LiveEvent<Pair<SchoolInfo, SatScores?>> = LiveEvent() // Holds the selected school and SAT scores
    var errorCode: LiveEvent<SchoolErrorCode> = LiveEvent() // Used when there is an error to send the user a message in the fragment

    @Inject
    @Singleton
    lateinit var schoolService: SchoolService

    init {
        // Used Dagger to inject the SchoolService into the MainViewModel
        DaggerSchoolApiFactory.create().inject(this)
    }

    fun loadSchools() {
        val schools: Observable<List<SchoolInfo>> = schoolService.getSchools()
        progressVisibility.postValue(View.VISIBLE) // Show the progressBar

        // Use RxJava3 to get the list of schools on the IO thread and the the UI thread
        schools
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({schoolInfos ->
                progressVisibility.postValue(View.GONE)
                val vmList = mutableListOf<SchoolViewModel>()
                    // "Convert" the list of schools to a list of school viewModels so we an use
                    // them with the Epoxy controller
                    schoolInfos.forEach {info ->
                        vmList.add(SchoolViewModel(info, this))
                    }
                    // Update the list
                    schoolInfoList.postValue(vmList)
            }, {error ->
                progressVisibility.postValue(View.GONE)
                    Timber.d(error)
                    errorCode.postValue(SchoolErrorCode.ERROR_SCHOOL)
            })
    }

    fun getScores(dbn: String?) {
        val scores: Observable<List<SatScores>> = schoolService.getScores(dbn)
        progressVisibility.postValue(View.VISIBLE)

        // Use RxJava3 to get the selected school's scores on the IO thread and the the UI thread
        scores
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({score ->
                progressVisibility.postValue(View.GONE)
                if (score.isNotEmpty()) {
                    // Return the school info and SAT scores
                    schoolScores.postValue(Pair(clickedSchool.value!!, score[0]))
                } else {
                    // No SAT scores could be found for this school. Just return the school info
                    schoolScores.postValue(Pair(clickedSchool.value!!, null))
                }
            }, {error ->
                // Display an error of something goes wrong when obtaining SAT scores
                Timber.d(error)
                progressVisibility.postValue(View.GONE)
                errorCode.postValue(SchoolErrorCode.ERROR_SCORES)
            })
    }
}

enum class SchoolErrorCode {
    NOT_FOUND, NO_CONNECTION, ERROR_SCHOOL, ERROR_SCORES
}