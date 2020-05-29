package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.R
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.databinding.FragmentMainBinding
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.utils.isNetworkConnected
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.mainViewModel = viewModel
        binding.executePendingBindings()
        setupObservers()

        // Get the list of schools
        if (viewModel.schoolInfoList.value == null) {
            // Check if the device has an internet connection
            checkConnected()
        }
    }

    fun setupObservers() {
        viewModel.schoolInfoList.observe(viewLifecycleOwner, Observer { schoolInfos ->
            // Use Epoxy to show the list of schools that were found
            val mainEpoxyController = MainEpoxyController()
            mainEpoxyController.setData(schoolInfos)
            binding.recyclerView.setController(mainEpoxyController)
        })
        viewModel.clickedSchool.observe(viewLifecycleOwner, Observer { info ->
            // Used to grab the 2012 SAT scores for the selected school
            viewModel.getScores(info.dbn)
        })
        viewModel.schoolScores.observe(viewLifecycleOwner, Observer { schoolScores ->
            // Once the scores have been retrieved, show all the info in the Details Fragment
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(schoolScores.first, schoolScores.second))
        })

        // Used to handle errors that come up when running things in the ViewModel
        viewModel.errorCode.observe(viewLifecycleOwner, Observer { error ->
            val message = when (error) {
                SchoolErrorCode.ERROR_SCHOOL -> getString(R.string.error_schools)
                SchoolErrorCode.ERROR_SCORES -> getString(R.string.error_scores)
                else -> getString(R.string.error_generic)
            }
            // Show the error
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        })
    }

    // If the device is connected, continue with getting the list of schools. If not, then show a
    // snackbar asking the user to connect to the internet and initiate the download again.
    private fun checkConnected() {
        if (isNetworkConnected()) {
            viewModel.loadSchools()
        } else {
            showSnackbar()
        }
    }

    private fun showSnackbar() {
        val connSnackbar: Snackbar = Snackbar.make(
            binding.getRoot(),
            R.string.error_no_connection,
            Snackbar.LENGTH_INDEFINITE
        )
        connSnackbar.setAction(R.string.retry) {
            connSnackbar.dismiss()
            checkConnected()
        }
        connSnackbar.show()
    }

}