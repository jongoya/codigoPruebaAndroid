package com.geoactio.sae.fragments.launchScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.geoactio.sae.Activities.MainActivity
import com.geoactio.sae.databinding.LaunchScreenFragmentBinding
import com.geoactio.sae.fragments.launchScreen.LaunchScreenViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchScreenFragment @Inject constructor(): Fragment() {
    lateinit var binding: LaunchScreenFragmentBinding
    private val viewModel: LaunchScreenViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchScreenFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    private fun updateUI(model: UiModel) {
        when (model) {
            UiModel.Content -> {
                (activity as MainActivity).hideNavBar()
                changeFragment()
            }
        }
    }

    private fun changeFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(LaunchScreenFragmentDirections.actionLaunchScreenFragmentToMainTabFragment())
            }, 2000)
    }
}
