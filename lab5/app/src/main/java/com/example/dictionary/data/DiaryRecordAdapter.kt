package com.example.dictionary.data

import android.os.Build

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemDiaryBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DiaryViewHolder(view: View) : RecyclerView.ViewHolder(view)

class DiaryAdapter(
    private val gotoEditorFn: (arguments: Bundle) -> Unit,
    private val deleteDiaryRecord: (diaryRecord: DiaryRecord) -> Unit
) : RecyclerView.Adapter<DiaryViewHolder>() {
    var diaryRecordList = listOf<DiaryRecord>()

    override fun getItemCount() = diaryRecordList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDiaryBinding.inflate(inflater, parent, false)

        return DiaryViewHolder(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val itemBinding = ItemDiaryBinding.bind(holder.itemView)
        val diaryRecord = diaryRecordList[position]

        itemBinding.recordTitle.text = diaryRecord.title
        itemBinding.recordContent.text = diaryRecord.content

        val createdAt = diaryRecord.createdAt
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.systemDefault())
        val formattedDate = formatter.format(Instant.ofEpochMilli(createdAt))
        itemBinding.recordDate.text = formattedDate


        holder.itemView.setOnClickListener {
            val arguments = Bundle().apply {
                putString("TITLE", diaryRecord.title)
                putString("CONTENT", diaryRecord.content)
                putString("ID", diaryRecord.uid)
                putLong("DATE", diaryRecord.createdAt)
            }

            gotoEditorFn(arguments)
        }

        itemBinding.recordDeleteButton.setOnClickListener {
            deleteDiaryRecord(diaryRecord)
        }
    }
}
