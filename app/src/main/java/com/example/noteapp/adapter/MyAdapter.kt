package com.example.noteapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ItemLayoutBinding
import com.example.noteapp.model.Note_model

class ContactAdapter(private val data: ArrayList<Note_model>,

                     val onDeleteClick:(Note_model, Int)->Unit,
                     val onItemClcik:(Note_model, Int)->Unit,

                     ) : RecyclerView.Adapter<ContactAdapter.Vh>() {

    inner class Vh(val itemContactBinding: ItemLayoutBinding)
        :RecyclerView.ViewHolder(itemContactBinding.root){

        @SuppressLint("SetTextI18n")
        fun  onBind(contact: Note_model, position:Int){

            itemContactBinding.apply {

                appName123.text=contact.note_name
                text123.text=contact.note_text
                date123.text=contact.date_item

                if (contact.note_text.length>15){
                    text123.text=contact.note_text.substring(0,15)
                }
                delete.setOnClickListener { onDeleteClick.invoke(contact,position) }
            }
            itemView.setOnClickListener { onItemClcik.invoke(contact,position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: Vh, position: Int) =holder.onBind(data[position],position)

}