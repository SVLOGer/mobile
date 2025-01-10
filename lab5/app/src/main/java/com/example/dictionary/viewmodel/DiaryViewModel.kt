package com.example.dictionary.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.DiaryRecord
import com.example.dictionary.StorageApp
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.UUID

class DiaryViewModel : ViewModel() {
    val records: Flow<List<DiaryRecord>> = StorageApp.db.diaryRecordDao().getAllAsFlow()
    private val selectedDateMillis = MutableStateFlow<Long?>(null)

    val filteredRecords: Flow<List<DiaryRecord>> = combine(records, selectedDateMillis) { diaryRecords, dateMillis ->
        if (dateMillis != null) {
            val startOfDayMillis = Calendar.getInstance().apply {
                timeInMillis = dateMillis
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val endOfDayMillis = Calendar.getInstance().apply {
                timeInMillis = dateMillis
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.timeInMillis

            diaryRecords.filter { it.createdAt in startOfDayMillis..endOfDayMillis }
        } else {
            diaryRecords
        }
    }

    fun setSelectedDate(dateMillis: Long?) {
        selectedDateMillis.value = dateMillis
    }

    suspend fun getRecords(): List<DiaryRecord> {
        val deferred = viewModelScope.async {
            StorageApp.db.diaryRecordDao().getAll()
        }

        return deferred.await()
    }

    fun createDiaryRecord(title: String, content: String) {
        viewModelScope.launch {

            val diaryRecordDao = StorageApp.db.diaryRecordDao()
            if (diaryRecordDao.findByTitle(title) != null) {
                return@launch
            }

            val randomUid = UUID.randomUUID().toString()
            val newDiaryRecord = DiaryRecord(
                randomUid,
                title,
                content,
                System.currentTimeMillis(),
            )

            diaryRecordDao.insertAll(newDiaryRecord)
        }
    }

    fun updateDiaryRecord(uid: String, title: String, content: String, date: Long) {
        viewModelScope.launch {

            val diaryRecordDao = StorageApp.db.diaryRecordDao()

            val diaryRecord = DiaryRecord(
                uid,
                title,
                content,
                date,
            )

            diaryRecordDao.updateAll(diaryRecord)
        }
    }

    fun deleteDiaryRecord(diaryRecord: DiaryRecord) {
        println("delete")
        viewModelScope.launch {
            val diaryRecordDao = StorageApp.db.diaryRecordDao()
            diaryRecordDao.delete(diaryRecord)
        }
    }
}