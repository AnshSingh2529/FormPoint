package coading.champ.online_form_india.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coading.champ.online_form_india.R
import coading.champ.online_form_india.databinding.ActivityWebviewBinding
import coading.champ.online_form_india.helper.PRIVACY_POLICY
import coading.champ.online_form_india.helper.TERM_AND_CONDITION_URL
import android.webkit.WebSettings
import android.webkit.WebView

class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val flag = intent.getIntExtra("flag",0)

        when(flag){
            1->{
                binding.awToolbar.title = getString(R.string.privacy_policy)
                loadWebView(PRIVACY_POLICY)
            }
            2->{
                binding.awToolbar.title = getString(R.string.terms_amp_condition)
                loadWebView(TERM_AND_CONDITION_URL)
            }
        }

        binding.awToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadWebView(url: String) {
        // Enable JavaScript
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true


        // Enable support for the viewport meta tag
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true

        // Enable mixed content (if needed)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        // Enable caching
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        // Enable hardware acceleration
        binding.webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null)

        // Load the specified URL
        binding.webView.loadUrl(url)
    }
}