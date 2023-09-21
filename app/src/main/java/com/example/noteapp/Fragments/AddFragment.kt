package com.example.noteapp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.noteapp.R
import com.example.noteapp.databse.Database
import com.example.noteapp.model.Note_model
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFragment:Fragment(R.layout.note_add) {

    private lateinit var name1:EditText
    private lateinit var name2:EditText
    private lateinit var date:TextView
    private lateinit var add_message:Button

    private lateinit var list: ArrayList<Note_model>

    private lateinit var contact:Note_model

    private val database by lazy { Database.getDatabase(requireContext()) }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list= ArrayList(database.contactDao().getContact())

        name1=view.findViewById(R.id.app_name)
        name2=view.findViewById(R.id.edit_text)
        date=view.findViewById(R.id.date)

        add_message=view.findViewById(R.id.add_message)

        val currentTime = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val currentDate = dateFormat.format(currentTime)
        val currentTime1 = timeFormat.format(currentTime)

        date.text="$currentDate  $currentTime1"

        name1.setText(arguments?.getString("name1"))
        name2.setText(arguments?.getString("name2"))

        add_message.setOnClickListener {

            contact= Note_model(note_name = name1.text.toString(), note_text = name2.text.toString(), date_item = date.text.toString())

            val name=arguments?.getString("name1")
            if (name==null && !name1.text.toString().isNullOrEmpty()){
                database.contactDao().insert(contact)
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("MainFragment")
                    .replace(R.id.container,MainFragment())
                    .commit()
            }
            else {
                database.contactDao().nukeTable()
                val index=arguments?.getInt("index")
                if (index!=null){
                    list[index].note_name=name1.text.toString()
                    list[index].note_text=name2.text.toString()
                    list[index].date_item=date.text.toString()
                }
                database.contactDao().insertAll(list)
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("MainFragment")
                    .replace(R.id.container,MainFragment())
                    .commit()
            }

        }

    }
}