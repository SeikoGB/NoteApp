package com.example.noteapp.Fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.adapter.ContactAdapter
import com.example.noteapp.databse.Database
import com.example.noteapp.model.Note_model
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.*
import kotlin.collections.ArrayList

class MainFragment:Fragment(R.layout.fragment_main) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    private val database by lazy { Database.getDatabase(requireContext()) }
    private lateinit var list: ArrayList<Note_model>

    private lateinit var add_store: ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = ArrayList(database.contactDao().getContact())


        recyclerView = view.findViewById(R.id.recyclerView)
        add_store = view.findViewById(R.id.add_store)

        add_store.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("MainFragment")
                .replace(R.id.container, AddFragment())
                .commit()
        }

            adapter = ContactAdapter(list, { contact, i ->
                adapter.notifyItemRemoved(i)
                list.remove(contact)
                database.contactDao().deletecontact(contact)
                adapter.notifyDataSetChanged()

            }, { contact, i ->
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack("MainFragment")
                    .replace(
                        R.id.container,
                        AddFragment::class.java,
                        bundleOf("name1" to contact.note_name, "name2" to contact.note_text, "index" to i)
                    )
                    .commit()
            })
        adapter.notifyDataSetChanged()
            recyclerView.adapter = adapter
        }


    }
