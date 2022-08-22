package com.example.register_app.UI.login

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
import com.example.register_app.databinding.LoginBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {


    private val viewModel: LoginVM by viewModels()

    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etEmail.setText("eve.holt@reqres.in")
        binding.etPassword.setText("cityslicka")

        setOnclicklisteners()
        observer()

    }

    private fun setOnclicklisteners(){
        binding.btnLogin.setOnClickListener {
            viewModel.logIn(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collectLatest {
                    when (it){
                        is Resource.Success ->{
                            Toast.makeText(requireContext(),it.data.token, Toast.LENGTH_SHORT).show()
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