package com.example.empty_project.shared.loan.core.util

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import java.io.Serializable

fun Fragment.navigate(actionId: Int, hostId: Int? = null, data: Long? = null) {
	val navController = if (hostId == null) {
		findNavController()
	} else {
		Navigation.findNavController(requireActivity(), hostId)
	}

	Log.v("data", data.toString())

	val bundle = Bundle().apply {
		data?.let {
			putLong("navigation_data", it)
		}
	}

	navController.navigate(actionId, bundle)
}

val Fragment.navigationData: Long?
	get() = arguments?.getLong("navigation_data", -1L).let { if (it == -1L) null else it }
