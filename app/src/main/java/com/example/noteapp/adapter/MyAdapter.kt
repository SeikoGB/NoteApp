package com.example.noteapp.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.ItemLayoutBinding
import com.example.noteapp.model.Note_model
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class ContactAdapter(private val data: ArrayList<Note_model>,

                     val onDeleteClick:(Note_model, Int)->Unit,
                     val onItemClcik:(Note_model, Int)->Unit,

                     ) : RecyclerView.Adapter<ContactAdapter.Vh>() {

    inner class Vh(val itemContactBinding: ItemLayoutBinding)
        :RecyclerView.ViewHolder(itemContactBinding.root){

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun  onBind(contact: Note_model, position:Int){

            itemContactBinding.apply {

                appName123.text=contact.note_name
                text123.text=contact.note_text
                date123.text=contact.date_item



                if (contact.note_text.length>15){
                    text123.text=contact.note_text.substring(0,15)
                }
                when(contact.importance){
                    1->{
                        muhim.text="Uncha Muhim Emas"
                    }
                    2->{
                        muhim.text="Ortacha Muhim"
                    }
                    3->{
                        muhim.text="Ota Muhim"
                    }

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