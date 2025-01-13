import android.os.Build

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.TodoRecord
import com.example.todo.databinding.ItemTodoBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view)

class TodoAdapter(
    private val gotoEditorFn: (arguments: Bundle) -> Unit,
    private val deleteTodoRecord: (todoRecord: TodoRecord) -> Unit
) : RecyclerView.Adapter<TodoViewHolder>() {
    var todoRecordList = listOf<TodoRecord>()

    override fun getItemCount() = todoRecordList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)

        return TodoViewHolder(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val itemBinding = ItemTodoBinding.bind(holder.itemView)
        val todoRecord = todoRecordList[position]

        itemBinding.recordTitle.text = todoRecord.title
        itemBinding.recordContent.text = todoRecord.content
        itemBinding.recordState.text = todoRecord.status

        val deadline = todoRecord.deadline
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.systemDefault())
        val formattedDate = formatter.format(Instant.ofEpochMilli(deadline))
        itemBinding.recordDate.text = formattedDate


        holder.itemView.setOnClickListener {
            val arguments = Bundle().apply {
                putString("TITLE", todoRecord.title)
                putString("CONTENT", todoRecord.content)
                putString("ID", todoRecord.uid)
                putLong("DEADLINE", todoRecord.deadline)
                putString("STATUS", todoRecord.status)
            }

            gotoEditorFn(arguments)
        }

        itemBinding.recordDeleteButton.setOnClickListener {
            deleteTodoRecord(todoRecord)
        }
    }
}