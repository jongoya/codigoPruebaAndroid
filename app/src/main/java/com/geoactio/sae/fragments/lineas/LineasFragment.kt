package com.geoactio.sae.fragments.lineas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.geoactio.domain.Linea
import com.geoactio.sae.Activities.MainActivity
import com.geoactio.sae.R
import com.geoactio.sae.common.showAlertMessage
import com.geoactio.sae.databinding.LineasFragmentBinding
import com.geoactio.sae.fragments.lineas.LineasViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LineasFragment @Inject constructor(): Fragment() {
    lateinit var binding: LineasFragmentBinding
    private val viewModel: LineasViewModel by viewModels()
    private lateinit var lineasAdapter: LineasListAdapter
    private lateinit var lineas: List<Linea>
    private lateinit var lineasFiltradas: ArrayList<Linea>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LineasFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))
        (requireActivity() as MainActivity).hideInfoButton()

        binding.buscarField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (binding.buscarField.text.isEmpty()) {
                    lineasFiltradas.clear()
                    lineasFiltradas.addAll(lineas)
                    lineasAdapter.notifyDataSetChanged()
                }
            }
        })

        binding.buscarButton.setOnClickListener {
            filtrarLineas()
        }
    }

    private fun updateUI(model: UiModel) {
        when (model) {
            is UiModel.Content -> {
                this.lineas = model.lineas
                this.lineasFiltradas = arrayListOf()
                this.lineasFiltradas.addAll(model.lineas)
                loadList(lineasFiltradas)
            }
            UiModel.ErrorLineas -> {
                requireActivity().showAlertMessage(getString(R.string.app_name))
            }
        }
    }

    private fun loadList(lineas: ArrayList<Linea>) {
        lineasAdapter = LineasListAdapter(lineas, requireContext())
        binding.lineasListView.apply {
            adapter = lineasAdapter
            divider = null
        }

        binding.lineasListView.setOnItemClickListener { _, _, position, _ ->
            val linea = lineasAdapter.getItem(position)
            findNavController().navigate(R.id.trayectosFragment, bundleOf("linea" to linea))
        }
    }

    private fun filtrarLineas() {
        lineasFiltradas.clear()
        lineas.forEach {
            if (it.nombre.lowercase().contains(binding.buscarField.text.toString().lowercase())) {
                lineasFiltradas.add(it)
            }
        }

        lineasAdapter.notifyDataSetChanged()
    }
}
