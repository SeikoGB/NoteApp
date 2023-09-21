package com.example.noteapp.Fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.adapter.ContactAdapter
import com.example.noteapp.databse.Database
import com.example.noteapp.model.Note_model
import java.util.*

class MainFragment:Fragment(R.layout.fragment_main) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    private val database by lazy { Database.getDatabase(requireContext()) }
    private lateinit var list: ArrayList<Note_model>

    private lateinit var add_store: ImageView

    private lateinit var data: List<Note_model>

    private lateinit var search:EditText
    private lateinit var settings:ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = ArrayList(database.contactDao().getContact())


        recyclerView = view.findViewById(R.id.recyclerView)
        add_store = view.findViewById(R.id.add_store)
        search=view.findViewById(R.id.search)
        settings=view.findViewById(R.id.settings)

        settings.setOnClickListener {
            showPopupMenu()
        }

        search.addTextChangedListener {
            val query = it?.toString()
            if (query.isNullOrEmpty()) {
                list.clear()
                list.addAll(database.contactDao().getContact())
                adapter.notifyDataSetChanged()
                return@addTextChangedListener
            }
            data = database.contactDao().searchUzWords(query) as ArrayList<Note_model>

            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
        }

        add_store.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
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



    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), settings)
        popupMenu.menuInflater.inflate(R.menu.menu_settings, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.clear_all -> {
                    // Handle menu item 1 click
                    true
                }
                R.id.notifications -> {
                    // Handle menu item 2 click
                    true
                }

                R.id.colors -> {
                    // Handle menu item 2 click
                    val items = arrayOf("Element 1", "Element 2", "Element 3")
                    val checkedItem = 0 // Tanlangan element indexi

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Tanlangan element")
                    builder.setSingleChoiceItems(items, checkedItem) { dialog, which ->
                        // Element tanlandikda chaqiriladi
                        // which - tanlangan element indeksi
                    }

// Elementlarga mos ranglarni o'rnatish
                    builder.setPositiveButton("OK", null) // OK tugmasi
                    val dialog = builder.create()

// Elementlarga mos ranglarni o'rnatish
                    dialog.setOnShowListener {
                        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    }

                    dialog.show()
                    true
                }
                // Add more cases for other menu items if needed
                else -> false
            }
        }
        popupMenu.show()
    }


    }
