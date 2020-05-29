package com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.R
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.databinding.FragmentDetailsBinding
import com.blkxltng.a20200526_mauricegaynor_nycschools_kotlin.models.SchoolInfo

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Grab the school info and scores that were sent from the previous fragment and assign
        // them to this fragment
        val info = args.info
        binding.schoolInfo = info
        binding.satScores = args.scores
        binding.executePendingBindings()

        // Shows a dialog asking if hte user would like to call
        binding.phoneNumberValue.setOnClickListener { createDialog(true, info) }

        // Shows a dialog asking if the user would like to email
        binding.emailValue.setOnClickListener { createDialog(false, info) }

        // Creates a web intent when the user clicks on the website
        binding.websiteValue.setOnClickListener {
            //Load website
            val intent = Intent(Intent.ACTION_VIEW)
            if (info.website.startsWith("http://") || info.website.startsWith("https://")) {
                intent.data = Uri.parse(info.website)
            } else {
                intent.data = Uri.parse("http://" + info.website)
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(context, R.string.error_no_app_found, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Creates a dialog that asks the user if they would like to call or email the school depending
    // on which they click.
    private fun createDialog(isPhone: Boolean, info: SchoolInfo) {

        val builder = AlertDialog.Builder(requireActivity())
        val message: String
        val intent: Intent
        if (isPhone) {
            // Create a dial intent
            message = getString(R.string.make_a_call, info.school_name)
            intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + info.phone_number))
        } else {
            // Create an email intent
            message = getString(R.string.send_an_email, info.school_name)
            intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, info.school_email)
        }
        builder.setMessage(message)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            // User clicked OK button
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(context, R.string.error_no_app_found, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            // Do nothing
            dialog.dismiss()
        }
        builder.create().show()
    }
}