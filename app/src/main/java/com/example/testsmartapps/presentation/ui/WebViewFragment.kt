package com.example.testsmartapps.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testsmartapps.BaseApplication
import com.example.testsmartapps.R
import com.example.testsmartapps.data.database.SettingsDataStore
import com.example.testsmartapps.databinding.FragmentWebViewBinding
import com.example.testsmartapps.presentation.viewmodels.WebViewViewModel
import com.example.testsmartapps.presentation.viewmodels.WebViewViewModel.WebViewViewModelFactory
import kotlinx.coroutines.launch


class WebViewFragment : Fragment() {

    private val viewModel: WebViewViewModel by activityViewModels{
        WebViewViewModelFactory(
            (activity?.application as BaseApplication).webdatadatabase.webDataDao()
        )
    }

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsDataStore = SettingsDataStore(requireContext())
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWebData()
        viewModel.result.observe(viewLifecycleOwner) {
            if (it == "false") {
                findNavController().navigate(R.id.action_webViewFragment_to_gameFragment)
            }
        }
        settingsDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.webview.loadUrl(value)
        }
        binding.backButton.setOnClickListener {
            if(binding.webview.canGoBack()) binding.webview.goBack()
        }
        binding.homeButton.setOnClickListener {
            binding.webview.loadUrl("https://www.google.com/")
        }
        binding.webview.apply {
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            webViewClient = object : WebViewClient() {
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageStarted(view, url, favicon)
                    lifecycleScope.launch{
                        settingsDataStore.saveUrlToPreferencesStore(binding.webview.url!!, requireContext())
                    }
                }
            }
        }
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webview.canGoBack()){
                        binding.webview.goBack()
                    } else {
                        requireActivity().finishAndRemoveTask()
                    }
                }
            }
            )
        CookieManager.getInstance().setAcceptThirdPartyCookies(binding.webview, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}