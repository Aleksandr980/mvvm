package com.example.m12_mvvm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.m12_mvvm.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    private lateinit var binding : FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            viewModel.onButtonClick()
        }
        return binding.root
    }

    private val editText: String
        get() = binding.search.text.toString()
    private val dataText : String
        get() = "По запросу ${editText} ничего не найдено"
    private val textLengch: Int
        get() = editText.length

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.addTextChangedListener { Editable ->
            val state1 = {
                binding.button.isEnabled = false
            }
            val state2 = {
                binding.button.isEnabled = true
            }
            if (textLengch in 0..2) {
                state1()
            }
            if (textLengch >= 3) {
                state2()
            }
        }
        binding.button.setOnClickListener {
            viewModel.onSingInClick(dataText)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state: State ->
                when (state) {
                    State.Search -> {
                        binding.progress.isVisible = true
                    }
                    State.Data(dataText) -> {
                        binding.progress.isVisible = false
                       binding.result.text = viewModel.state.value.toString()
                    // binding.result.text = State.Success(viewModel.data.toString()).toString()
                    }
                    else -> {}
                }
            }
        }
    }
}

