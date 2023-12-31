package com.example.noteapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.noteapp.R
import com.example.noteapp.databse.Database
import com.example.noteapp.model.Importance
import com.example.noteapp.model.Note_model
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class AddFragment:Fragment() {

    private var param1: Note_model? = null
    private lateinit var name1:EditText
    private lateinit var name2:EditText
    private lateinit var date:TextView
    private lateinit var back:ImageView
    private lateinit var more:ImageView
    private lateinit var add_message:Button

    private lateinit var list: ArrayList<Note_model>
    private lateinit var add_time:Button
    private  var contact=Note_model(note_name = "", note_text = "", importance = 0, dead_line = "", date_item = "")
    private lateinit var high:Button
    private lateinit var midle:Button
    private lateinit var low:Button
    private lateinit var importance:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.note_add, container, false)
         more = view.findViewById(R.id.more)
        more.setOnClickListener {
            showPopupMenu()
        }
        return view
    }
    private val database by lazy { Database.getDatabase(requireContext()) }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        list= ArrayList(database.contactDao().getContact())

        importance=view.findViewById(R.id.muhimligi)
        high=view.findViewById(R.id.high)
        midle=view.findViewById(R.id.middle)
        low=view.findViewById(R.id.low)
        name1=view.findViewById(R.id.app_name)
        name2=view.findViewById(R.id.edit_text)
        date=view.findViewById(R.id.date)
        back=view.findViewById(R.id.back)
        add_time=view.findViewById(R.id.add_time)
        high.setOnClickListener {
            contact.importance=Importance.HIGH_IMPORTANCE.level
            importance.text="Ota Muhim"
        }
        midle.setOnClickListener {
            contact.importance=Importance.MID_IMPORTANCE.level
            importance.text="Ortacha Muhim"
        }
        low.setOnClickListener {
            contact.importance=Importance.LOW_IMPORTANCE.level
            importance.text="Uncha Muhim emas"
        }
        back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container,MainFragment())
                .commit()
        }
        add_time.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container,AddTimeFragment.newInstance(contact,false))
                .commit()
        }

        add_message=view.findViewById(R.id.add_message)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container,MainFragment())
                .commit()
        }

        val currentTime = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val currentDate = dateFormat.format(currentTime)
        val currentTime1 = timeFormat.format(currentTime)

        date.text="$currentDate  $currentTime1"

        name1.setText(arguments?.getString("name1"))
        name2.setText(arguments?.getString("name2"))

        add_message.setOnClickListener {

            Toast.makeText(requireContext(),"Saved",Toast.LENGTH_SHORT).show()
            contact.note_name=name1.text.toString()
            contact.note_text=name2.text.toString()
            contact.date_item=date.text.toString()
            contact.dead_line=arguments?.getString("date").toString()

            val name=arguments?.getString("name1")
            if (name==null && !name1.text.toString().isNullOrEmpty()){
                database.contactDao().insert(contact)
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.container,MainFragment())
                    .commit()
            }
            else {
                database.contactDao().nukeTable()
                val index=arguments?.getInt("index")
                if (index!=null){
                    when(list[index].importance){
                        1->{
                            importance.text="Uncha Muhim Emas"
                        }
                        2->{
                            importance.text="Ortacha Muhim"
                        }
                        3->{
                            importance.text="Ota Muhim"
                        }

                    }
                    list[index].note_name=name1.text.toString()
                    list[index].note_text=name2.text.toString()
                    list[index].date_item=date.text.toString()
                }
                database.contactDao().insertAll(list)
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.container,MainFragment())
                    .commit()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container,MainFragment())
                .commit()
        }

    }
    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), more)
        popupMenu.menuInflater.inflate(R.menu.menu_more, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete -> {
                    // Handle menu item 1 click
                    true
                }
                R.id.notification -> {
                    // Handle menu item 2 click
                    true
                }
                // Add more cases for other menu items if needed
                else -> false
            }
        }
        popupMenu.show()
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
        fun newInstance(param1: Note_model) =
            AddTimeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }

}
