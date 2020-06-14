package hr.tvz.android.tasksonfirerep.ui.task.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hr.tvz.android.tasksonfirerep.R
import hr.tvz.android.tasksonfirerep.model.Task
import hr.tvz.android.tasksonfirerep.util.Validator
import kotlinx.android.synthetic.main.task_dialog.*
import java.util.*

class UpdateTaskDialog: DialogFragment() {
    private var tmpTask: Task = Task("", "", "", Date())
    private lateinit var myListener: UpdateTaskListener
    private val validator = Validator()

    companion object {
        private const val EXTRA_ID = "id"
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_DESCRIPTION = "description"
        const val TAG = "update_dialog"

        fun newInstance(id: String, title: String, description: String): UpdateTaskDialog {
            val fragment =
                UpdateTaskDialog()

            val args = Bundle()
            args.putString(EXTRA_ID, id)
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_DESCRIPTION, description)

            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.task_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validator.add(inputLayoutTitle)
        tvDialogTitle.text = resources.getString(R.string.update_task)

        tmpTask.taskId = arguments?.getString(EXTRA_ID)!!
        tmpTask.title = arguments?.getString(EXTRA_TITLE)!!
        tmpTask.description = arguments?.getString(EXTRA_DESCRIPTION)!!

        inputLayoutTitle.editText?.setText(tmpTask.title)
        etDescription.setText(tmpTask.description)

        btnOk.setOnClickListener {
            tmpTask.title = inputLayoutTitle.editText?.text.toString()
            tmpTask.description = etDescription.text.toString()

            if (validator.result()) {
                myListener.updateTask(tmpTask.taskId, tmpTask.title, tmpTask.description)
                tmpTask.clear()
                dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            myListener = context as UpdateTaskListener
        } catch (error: ClassCastException) {
            throw ClassCastException(error.message)
        }
    }
}

interface UpdateTaskListener {
    fun updateTask(id: String, title: String, description: String)
}