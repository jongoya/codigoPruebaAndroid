package com.geoactio.sae.fragments.mainTabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.geoactio.sae.Activities.MainActivity
import com.geoactio.sae.R
import com.geoactio.sae.databinding.MainTabFragmentBinding
import com.geoactio.sae.fragments.comoLlegar.ComoLlegarFragment
import com.geoactio.sae.fragments.favoritos.FavoritosFragment
import com.geoactio.sae.fragments.home.HomeFragment
import com.geoactio.sae.fragments.lineas.LineasFragment
import com.geoactio.sae.fragments.mainTabFragment.MainTabViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainTabFragment @Inject constructor(): Fragment() {
    lateinit var binding: MainTabFragmentBinding
    private val viewModel: MainTabViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainTabFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUI))

        binding.tabBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    it.icon = ContextCompat.getDrawable(requireContext(), R.drawable.home_selected)
                    setCurrentFragment(HomeFragment())
                }
                R.id.lineas -> {
                    setCurrentFragment(LineasFragment())
                }
                R.id.favoritos -> {
                    setCurrentFragment(FavoritosFragment())
                }
                R.id.como_llegar -> {
                    setCurrentFragment(ComoLlegarFragment())
                }
                R.id.mas -> {
                }
            }
            true
        }
        
        setCurrentFragment(HomeFragment())
    }

    private fun updateUI(model: UiModel) {
        when (model) {
            UiModel.Content -> {
                (activity as MainActivity).showNavBar()
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.tabHostFragment, fragment).commit()
        }
    }
}
