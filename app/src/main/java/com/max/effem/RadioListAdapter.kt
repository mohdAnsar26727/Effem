package com.max.effem

import android.text.Selection.setSelection
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.max.effem.data.RadioData
import com.max.effem.databinding.RadioItemBinding

class RadioListAdapter(private val onClick: (data: RadioData) -> Unit) :
    RecyclerView.Adapter<RadioListAdapter.RadioVH>() {
    val list = radioList.toMutableList()
    var selectedPos = 0

    inner class RadioVH(val binding: RadioItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RadioData) {
            binding.root.apply {
                text = data.name.lowercase()
                setOnClickListener {
                    notifyItemChanged(selectedPos)
                    selectedPos = absoluteAdapterPosition
                    setSelection(absoluteAdapterPosition)
                    onClick(data)
                }
                setSelection(absoluteAdapterPosition)
            }
        }

        private fun TextView.setSelection(pos: Int) {
            setTextColor(
                ContextCompat.getColor(
                    context,
                    if (selectedPos == pos) R.color.green else R.color.white
                )
            )
            background = if (selectedPos == pos) ContextCompat.getDrawable(
                context,
                R.drawable.black_bg
            ) else null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioVH =
        RadioVH(RadioItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RadioVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    companion object {
        val radioList = listOf(
            RadioData(
                "Air Trivandrum",
                "https://air.pc.cdn.bitgravity.com/air/live/pbaudio230/chunklist.m3u8"
            ),
            RadioData(
                "Air Kannada",
                "https://air.pc.cdn.bitgravity.com/air/live/pbaudio029/chunklist.m3u8"
            ),
            RadioData(
                "Radio Vadakara",
                "https://stream-38.zeno.fm/9k5ku7bhb8zuv?zs=n0_QopF1TXeTFJsjko2BaA"
            ),
            RadioData("Mutthunabi FM", "http://radio.zionmediait.com:5280/;"),
            RadioData(
                "MappilaPPattu",
                "https://stream-30.zeno.fm/2sgvktnrbc9uv?zs=Sk8B8mymSvqDqp9_eI_qjw"
            ),
            RadioData(
                "Radio Mango 91.9",
                "https://bcovlive-a.akamaihd.net/19b535b7499a4719a5c19e043063f5d9/ap-southeast-1/6034685947001/profile_0/chunklist.m3u8"
            ),
            RadioData(
                "Air Kochi FM 102.3",
                "https://air.pc.cdn.bitgravity.com/air/live/pbaudio045/chunklist.m3u8"
            ),
            RadioData(
                "Air Ananthapuri FM 101.9",
                "https://air.pc.cdn.bitgravity.com/air/live/pbaudio229/chunklist.m3u8"
            ),
            RadioData(
                "Kottarakkara FM",
                "https://stream-37.zeno.fm/gqdb1rev5s8uv?zs=QQm10PP5SiOhHS80fRIZMA"
            ),
            RadioData(
                "Dubai Evergreen",
                "https://stream-12.zeno.fm/pw1ecm2sywzuv?zs=EEw9KxcHTEGFAnx-JZNBwQ"
            ),
            RadioData(
                "HipHop Malayalam",
                "https://stream-43.zeno.fm/3tgfdfa7f18uv?zs=wurD30PpTe6djHcrmIO4Og"
            )
        )
    }
}

