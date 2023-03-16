package com.evansroy.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.evansroy.todolist.databinding.FragmentToDoDialogBinding

import com.google.android.material.textfield.TextInputEditText

// The class extends the DialogFragment class
class ToDoDialogFragment : DialogFragment() {

    // Declare necessary variables
    private lateinit var binding: FragmentToDoDialogBinding
    private var listener : OnDialogNextBtnClickListener? = null
    private var toDoData: ToDoData? = null

    // A function to set the listener for the Next button
    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    // A companion object used to create new instances of this fragment
    companion object {
        const val TAG = "DialogFragment"

        // A function to create a new instance of the fragment with given arguments
        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            ToDoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }

    // Inflate the view and create the binding object
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoDialogBinding.inflate(inflater , container,false)
        return binding.root
    }

    // Initialize the view and set the event listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If there are any arguments passed in, create a new instance of the to-do item with the arguments
        if (arguments != null){
            toDoData = ToDoData(arguments?.getString("taskId").toString() ,arguments?.getString("task").toString())
            binding.todoEt.setText(toDoData?.task)
        }

        // Set the listener for the Close button
        binding.todoClose.setOnClickListener {
            dismiss()
        }

        // Set the listener for the Next button
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()

            // If the to-do item is not empty, save it or update it accordingly
            if (todoTask.isNotEmpty()){
                if (toDoData == null){
                    listener?.saveTask(todoTask , binding.todoEt)
                }else{
                    toDoData!!.task = todoTask
                    listener?.updateTask(toDoData!!, binding.todoEt)
                }
            }
