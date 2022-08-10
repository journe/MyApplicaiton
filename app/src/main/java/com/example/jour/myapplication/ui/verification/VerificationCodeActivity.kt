package com.example.jour.myapplication.ui.verification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.KeyboardUtils
import com.example.jour.myapplication.databinding.ActivityVerificationCodeBinding
import com.example.jour.myapplication.view.VerificationCodeEditText
import org.jetbrains.anko.toast

class VerificationCodeActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerificationCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etVerificationCode.setOnInputTextListener(object :
            VerificationCodeEditText.OnInputTextListener {
            override fun onInputTextComplete(text: CharSequence) {
                toast(text)
            }
        })

        binding.etVerificationCode2.setOnInputTextListener(object :
            VerificationCodeEditText.OnInputTextListener {
            override fun onInputTextComplete(text: CharSequence) {
                toast(text)
            }
        })
        binding.etVerificationCode.setOnClickListener {
            KeyboardUtils.showSoftInput(binding.etVerificationCode)
            binding.etVerificationCode.requestFocus()
        }

        binding.etVerificationCode2.setOnClickListener {
            KeyboardUtils.showSoftInput(binding.etVerificationCode2)
            binding.etVerificationCode2.requestFocus()
        }
    }
}