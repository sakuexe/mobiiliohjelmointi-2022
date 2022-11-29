package com.example.bottom_navigation.ui.blank

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.bottom_navigation.MainActivityViewModel
import com.example.bottom_navigation.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {

    private var _binding : FragmentBlankBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: BlankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BlankViewModel::class.java)
        // TODO: Use the ViewModel

        val appViewModel:MainActivityViewModel by activityViewModels()
        val textView: TextView = binding.textView
        textView.text = appViewModel.selected.value?.text
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

    }

}