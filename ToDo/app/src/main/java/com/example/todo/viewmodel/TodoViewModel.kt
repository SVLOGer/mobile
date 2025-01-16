    package com.example.todo.viewmodel

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.todo.data.TodoRecord
    import com.example.todo.StorageApp
    import kotlinx.coroutines.async
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.combine
    import kotlinx.coroutines.launch
    import java.util.UUID

    class TodoViewModel : ViewModel() {
        val records: Flow<List<TodoRecord>> = StorageApp.db.todoRecordDao().getAllAsFlow()
        private val selectedStatus = MutableStateFlow<String>("All tasks")
        private val selectedCategory = MutableStateFlow("All category")

        val filteredRecords: Flow<List<TodoRecord>> = combine(records, selectedStatus, selectedCategory) { todoRecords, status, category ->
            if (status == "All tasks" && (category == "All category" || category == "Все категории")) {
                todoRecords
            }
            else {
                if (category != "All category") {
                    if (status != "All tasks") {
                        todoRecords.filter { it.category == category  && it.status == status}
                    } else {
                        todoRecords.filter { it.category == category }
                    }
                } else {
                    todoRecords.filter { it.status == status }
                }
            }
        }

        fun setSelectedStatus(status: String) {
            when(status) {
                "Все задачи" -> selectedStatus.value = "All tasks"
                "Не начато" -> selectedStatus.value = "Not started"
                "В процессе" -> selectedStatus.value = "In progress"
                "Готово" -> selectedStatus.value = "Done"
                else -> selectedStatus.value = status
            }
        }

        fun setSelectedCategory(category: String) {
            selectedCategory.value = category
        }

        suspend fun getRecords(): List<TodoRecord> {
            val deferred = viewModelScope.async {
                StorageApp.db.todoRecordDao().getAll()
            }

            return deferred.await()
        }

        fun createTodoRecord(title: String, content: String, deadline: Long, status: String, category: String) {
            viewModelScope.launch {

                val todoRecordDao = StorageApp.db.todoRecordDao()
                if (todoRecordDao.findByTitle(title) != null) {
                    return@launch
                }

                val randomUid = UUID.randomUUID().toString()
                val newTodoRecord = TodoRecord(
                    randomUid,
                    title,
                    content,
                    deadline,
                    status,
                    category,
                )

                todoRecordDao.insertAll(newTodoRecord)
            }
        }

        fun updateTodoRecord(uid: String, title: String, content: String, deadline: Long, status: String, category: String) {
            viewModelScope.launch {

                val todoRecordDao = StorageApp.db.todoRecordDao()

                val todoRecord = TodoRecord(
                    uid,
                    title,
                    content,
                    deadline,
                    status,
                    category,
                )

                todoRecordDao.updateAll(todoRecord)
            }
        }

        fun deleteTodoRecord(todoRecord: TodoRecord) {
            viewModelScope.launch {
                val todoRecordDao = StorageApp.db.todoRecordDao()
                todoRecordDao.delete(todoRecord)
            }
        }
    }