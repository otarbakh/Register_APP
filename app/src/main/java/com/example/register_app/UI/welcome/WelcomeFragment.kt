package com.example.register_app.UI.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.register_app.R
import com.example.register_app.databinding.WelcomeBinding
import kotlinx.coroutines.flow.first
import java.util.prefs.Preferences

class WelcomeFragment: Fragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "info")



    private var _binding: WelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnCliclisteners()
        save(KEY_AUTH.toString(),)
    }

    // OTO KAI BICHIA
    //oto kai bichia
    private fun setOnCliclisteners(){
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun save(key: String, name: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context?.dataStore?.edit {
            it[dataStoreKey] = name
        }
    }

    suspend fun getInfo(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context?.dataStore?.data?.first()
        return preferences?.get(dataStoreKey)
    }

    companion object {
        private val KEY_AUTH = stringPreferencesKey("KEY_AUTH")
    }
}

