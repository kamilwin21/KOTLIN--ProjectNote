package com.example.note_project_1

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edycja_notatki.view.*
import kotlinx.android.synthetic.main.pozycja_listy.view.*


class MyAdapter(val context: Context, val db:SQLiteDatabase, var notes: ArrayList<Notatka>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.pozycja_listy,parent, false)
        return MyViewHolder(positionList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val note = holder.view.id_layout_pozycji_listy
        val pozycjaListy = holder.view.textView_pozycji_listy
        val pozycjaListy1 = holder.view.textView_pozycji_listy_1


        //Po kliknięciu w notatkę dodawanie do edit tekst aktualnego tytułu
        pozycjaListy.setText(notes[holder.adapterPosition].title)
        pozycjaListy1.setText(notes[holder.adapterPosition].message)


        /*
        val cursor = db.query(FeedEntry.TABLE_NAME, null,
            BaseColumns._ID + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null, null, null)

        //Umieszczenie kursora na pierwszym elemencie
        if (cursor.moveToFirst())
        {
            //Sprawdzenie czy rekord pierwszy nie jest pusty
            if (!cursor.getString(1).isNullOrEmpty())
            {
                pozycjaListy.setText(cursor.getString(1))

            }

        }

         */
        //Przycisk obsługuje kliknięcie w daną notatkę z RecyclerView
        note.setOnClickListener{
            val intent_edit = Intent(context.applicationContext, EdycjaNotatki::class.java)
            val title_edit = notes[holder.adapterPosition].title
            val message_edit = notes[holder.adapterPosition].message
            val id_edit = notes[holder.adapterPosition].id.toString()
            // val title_edit = pozycjaListy.text
           // val id_edit = holder.adapterPosition.plus(1).toString()


            intent_edit.putExtra("title_edit", title_edit)
            intent_edit.putExtra("message_edit", message_edit)
            intent_edit.putExtra("ID_Notatki", id_edit)

            holder.view.context.startActivity(intent_edit)
        }
        //Przytrzymanie notatki spowoduje usunięcie jej
        note.setOnLongClickListener(object  : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                //Usuwa rekord z bazy danych
                db.delete(FeedEntry.TABLE_NAME, BaseColumns._ID + "=?",
                arrayOf(notes[holder.adapterPosition].id.toString()))
                //Usuwanie notatki z listy
                notes.removeAt(holder.adapterPosition)
                //Usuwanie notatki z RecyclerView
                notifyItemRemoved(holder.adapterPosition)

                return true
            }


        })


    }

    override fun getItemCount(): Int {
       // return DataBase.listaNotatek.size
        val cursor = db.query(FeedEntry.TABLE_NAME, null,
            null, null,null,null,
            null)
        val liczbaWierszy = cursor.count
        return liczbaWierszy
    }


}


class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

/*
    init {
        view.id_layout_pozycji_listy.setOnClickListener {
            val position:Int = adapterPosition
            val intent = Intent(view.context, EdycjaNotatki::class.java)
            intent.putExtra("tytułNotatki", DataBase.listaNotatek[position])
            intent.putExtra("ID_Notatki", position )
            view.context.startActivity(intent)
        }


    }


 */



}
