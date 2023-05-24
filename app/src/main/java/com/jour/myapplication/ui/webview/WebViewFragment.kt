package com.jour.myapplication.ui.webview

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.KeyEvent
import android.webkit.*
import androidx.fragment.app.viewModels
import com.jour.myapplication.base.ktx.d
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseFragment
import com.jour.myapplication.databinding.FragmentFirstBinding
import com.jour.myapplication.databinding.FragmentWebviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebviewBinding, EmptyViewModel>() {
    override val mViewModel: EmptyViewModel by viewModels()

    override fun FragmentWebviewBinding.initView() {
        mBinding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = MyWebViewClient()
            settings.javaScriptEnabled = true

        }

        mBinding.webView.loadUrl("http://m.axoso.me/home")
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        // Check if the key event was the Back button and if there's history
//        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
//            myWebView.goBack()
//            return true
//        }
//        // If it wasn't the Back key or there's no web page history, bubble up to the default
//        // system behavior (probably exit the activity)
//        return super.onKeyDown(keyCode, event)
//    }


    inner class ChromeClient : WebChromeClient() {


    }

    inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
            // Check if the key event was the Back button and if there's history
            if (event?.keyCode == KeyEvent.KEYCODE_BACK && mBinding.webView.canGoBack()) {
                mBinding.webView.goBack()
                return true
            }
            // If it wasn't the Back key or there's no web page history, bubble up to the default
            // system behavior (probably exit the activity)
            return super.shouldOverrideKeyEvent(view, event)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            url.d()
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            mBinding.webView.loadUrl("javascript:callJS()");

            super.onPageFinished(view, url)
        }

        override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
//            url.d()
            return super.shouldInterceptRequest(view, url)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            request?.url.d()
            request.d()
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url.d()
            if (Uri.parse(url).path == "menus") {
                "menus".d()
            }
            if (Uri.parse(url).host == "m.axoso.me") {
                // This is my web site, so do not override; let my WebView load the page
                "m.axoso.me".d()
                return false
            }

            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                startActivity(this)
            }
            return true
        }
    }

}
