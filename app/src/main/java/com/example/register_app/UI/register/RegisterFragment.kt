package com.example.register_app.UI.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.register_app.Resource
import com.example.register_app.databinding.RegisterBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {


    private val viewModel: RegisterVM by viewModels()

    private var _binding: RegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etEmail.setText("eve.holt@reqres.in")
        binding.etPassword.setText("cityslicka")

        setOnClicklisteners()
        observer()

    }

    private fun setOnClicklisteners(){
        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registeredState.collectLatest {
                    when (it){
                        is Resource.Success ->{
                            Toast.makeText(requireContext(),it.data.id.toString(), Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Error ->{
                            Toast.makeText(requireContext(),it.error, Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Loading ->{

                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}