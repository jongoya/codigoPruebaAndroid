package com.geoactio.sae.fragments.comoLlegar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.geoactio.sae.databinding.ComoLlegarFragmentBinding
import com.geoactio.sae.fragments.comoLlegar.ComoLlegarViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComoLlegarFragment @Inject constructor(): Fragment() {
    lateinit var binding: ComoLlegarFragmentBinding
    private val viewModel: ComoLlegarViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ComoLlegarFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
    }

    private fun updateUI(model: UiModel) {
        when (model) {
            UiModel.Content -> {

            }
        }
    }
}
