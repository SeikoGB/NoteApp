package com.example.noteapp.Fragments

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentAddTimeBinding
import java.time.LocalTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTimeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTimeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var dueTime: LocalTime? = null
    var time:String? = null
    var date:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var binding=FragmentAddTimeBinding.inflate(inflater,container,false)
        binding.calendarView!!.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(requireContext(), dayOfMonth.toString() + "/" + month.toString() + "/" + year.toString(), Toast.LENGTH_LONG).show();
            date = dayOfMonth.toString() + "/" + month.toString() + "/" + year.toString()
        }
        binding.reschedule!!.setOnClickListener {
            openTimePicker()
        }
        binding.save.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container,AddFragment())
        }

        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            dueTime = LocalTime.of(hourOfDay, minute)
            time = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
        }

        val dialog = TimePickerDialog(requireContext(),listener,dueTime!!.hour, dueTime!!.minute,true)
        dialog.setTitle("Enter youtr task time")

        dialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTimeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddTimeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}