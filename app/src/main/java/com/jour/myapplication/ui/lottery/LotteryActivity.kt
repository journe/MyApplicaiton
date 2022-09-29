package com.jour.myapplication.ui.lottery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.jour.myapplication.databinding.ActivityLotteryBinding
import com.jour.myapplication.databinding.ActivityLotteryItemBinding

class LotteryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLotteryBinding

    private val lotterNumbers = listOf(
        1,
        2,
        3,
        14,
        15,
        16,
        27,
        28,
        29,
        31,
        32,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLotteryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.numberRv.adapter = NumberAdapter(lotterNumbers)

        val time1 = 5L * 60 * 60 * 1000
        val time2 = 11L * 60 * 60 * 1000
        binding.countdownViewTest1.start(time1)
        binding.countdownView2.start(time2)

        binding.segmentTabNext.setTabData(arrayOf("dest1","codues2","codues3"))

    }

    inner class NumberAdapter(private val data: List<Int>) :
        RecyclerView.Adapter<NumberAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return ViewHolder(
                ActivityLotteryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

        inner class ViewHolder(private val binding: ActivityLotteryItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(bean: Int) {
                binding.lotteryNumberTv.text = bean.toString()
            }
        }
    }

}