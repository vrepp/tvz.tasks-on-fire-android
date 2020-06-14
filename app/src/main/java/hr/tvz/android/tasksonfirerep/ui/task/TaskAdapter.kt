package hr.tvz.android.tasksonfirerep.ui.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import hr.tvz.android.tasksonfirerep.R
import hr.tvz.android.tasksonfirerep.model.Task
import kotlinx.android.synthetic.main.item_task.view.*
import java.text.SimpleDateFormat

class TaskAdapter(private var items: MutableList<Task>,
                  private val context: Context,
                  private val listener: TaskActionsListener
):
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var dateFormat = SimpleDateFormat("E dd MMMM yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder =
        TaskAdapter.TaskViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        val date = dateFormat.format(items[position].CreatedAt)

        holder.tvTitle.text = items[position].title
        holder.tvDescription.text = items[position].description
        holder.tvCreatedAt.text = date

        holder.cvItemTask.setOnClickListener {
            listener.showUpdateDialog(items[position])
        }

        holder.btnDelete.setOnClickListener {
            listener.showDeleteDialog(items[position])
        }
    }

    fun updateAllTask(list: MutableList<Task>) {
        items = list
        notifyDataSetChanged()
    }

    fun updateTask(task: Task) {
        val item = items.single { t -> t.id == task.id }
        val index = items.indexOf(item)

        items[index] = task
        notifyItemChanged(index)
    }

    fun deleteTask(task: Task) {
        val item = items.single { t -> t.id == task.id }
        val index = items.indexOf(item)

        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, itemCount)
    }

    fun addTask(task: Task) {
        items.add(0, task)

        if (itemCount <= 1) {
            notifyDataSetChanged()
        } else {
            notifyItemInserted(0)
            notifyItemRangeChanged(0, itemCount)
        }
    }

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.tvTitle
        val tvDescription: TextView = view.tvDescription
        val btnDelete: MaterialButton = view.btnOk
        val cvItemTask: MaterialCardView = view.cvItemTask
        val tvCreatedAt: TextView = view.tvCreatedAt
    }
}