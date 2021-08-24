package com.example.note_project_1

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_edycja_notatki.*

class EdycjaNotatki : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edycja_notatki)
        //Odczytywanie tytułu notatki po wybraniu notatki
        if(intent.hasExtra("title_edit") || intent.hasExtra("message_edit"))
        {
            editText_EdycjaNotatki.setText(intent.getStringExtra("title_edit"))
            editText_EdycjaNotatki_message.setText(intent.getStringExtra("message_edit"))
        }

        val dbHelper = BazaDanych(applicationContext)
        val db = dbHelper.writableDatabase
        //Kod do wykonania po kliknięciu przycisku Tworzenia/Edytowania notatki
       /*
        Button_EdycjaNotatki.setOnClickListener {


            val title = editText_EdycjaNotatki.text.toString()

            if(intent.hasExtra("ID_Notatki"))
                {
                    //Edycja notatki gdy notatka została wciśnięta
                    val value =  ContentValues()
                    value.put(FeedEntry.COLUMN_NAME_TITLE, title)
                    db.update(FeedEntry.TABLE_NAME,value, BaseColumns._ID +"=?",
                                arrayOf(intent.getStringExtra("ID_Notatki")))
                    Toast.makeText(applicationContext,"Zaktualizowano notatkę", Toast.LENGTH_SHORT).show()
                }else{
                    //Dodanie notatki
                    if(!title.isNullOrEmpty())
                    {
                        //Komunikacja z bazą danych
                        val value =  ContentValues()
                        value.put(FeedEntry.COLUMN_NAME_TITLE, title)
                        db.insertOrThrow(FeedEntry.TABLE_NAME, null, value)
                    }else{
                        Toast.makeText(applicationContext,"Nie zapisano notatki", Toast.LENGTH_SHORT).show()
                    }
                }

                //Powrót do listy notatek
                val intent1 = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent1)



        }


        */
    }
    //MenuInflater rozdmuchuje menu po aplikacji
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edycja_notatki,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Sprawdzenie który przycisk w ActionBar został kliknięty
        if (item?.itemId == R.id.save_button)
        {
            //Edytowanie lub dodawanie notatek
            val dbHelper = BazaDanych(applicationContext)
            val db = dbHelper.writableDatabase

            val title = editText_EdycjaNotatki.text.toString()
            val message = editText_EdycjaNotatki_message.text.toString()

            if(intent.hasExtra("ID_Notatki"))
            {
                //Edycja notatki gdy notatka została wciśnięta
                val value =  ContentValues()
                value.put(FeedEntry.COLUMN_NAME_TITLE, title)
                value.put(FeedEntry.COLUMN_NAME_MESSAGE, message)

                db.update(FeedEntry.TABLE_NAME,value, BaseColumns._ID +"=?",
                    arrayOf(intent.getStringExtra("ID_Notatki")))
                Toast.makeText(applicationContext,"Zaktualizowano notatkę", Toast.LENGTH_SHORT).show()
            }else{
                //Dodanie notatki
                if(!title.isNullOrEmpty() || !message.isNullOrEmpty())
                {
                    //Komunikacja z bazą danych
                    val value =  ContentValues()
                    value.put(FeedEntry.COLUMN_NAME_TITLE, title)
                    value.put("message", message)

                    //Dodanie nowej notatki do bazy danych
                    db.insertOrThrow(FeedEntry.TABLE_NAME, null, value)
                    Toast.makeText(applicationContext,"Dodano notatkę",Toast.LENGTH_SHORT).show()

                    //val intent = Intent(applicationContext, MainActivity::class.java)
                    //intent.putExtra("nowatresc",message)
                   // startActivity(intent)
                }else{
                    Toast.makeText(applicationContext,"Nie zapisano notatki", Toast.LENGTH_SHORT).show()
                }
            }
            //zamykanie bazy danych po dodaniu lub zapisaniu notatki
            db.close()
            //powrót do poprzedniego activity
            onBackPressed()

        }

        return super.onOptionsItemSelected(item)
    }

}