package com.jour.myapplication.ui.verification

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.KeyboardUtils
import com.jour.myapplication.R
import com.jour.myapplication.base.ktx.clickDelay
import com.jour.myapplication.databinding.ActivityVerificationCodeBinding
import com.jour.myapplication.view.TelNumCheckerView
import com.jour.myapplication.view.VerificationCodeEditText
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
        binding.verificationCodeBtn.clickDelay {
            val dialog = AlertDialog.Builder(this, R.style.inputDialog).apply {
                setView(TelNumCheckerView(context))
                setPositiveButton(
                    "确定"
                ) { dialog, which ->
                }
                setNegativeButton(
                    "关闭"
                ) { dialog, which ->
                }
            }
            dialog.create().show()
        }
    }
}