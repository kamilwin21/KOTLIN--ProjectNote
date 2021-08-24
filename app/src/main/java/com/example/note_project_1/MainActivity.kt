package com.example.note_project_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // var dbHelper = BazaDanych(applicationContext)
       // var db = dbHelper.writableDatabase

        var editText_pojedyńczej_Notatki = findViewById<EditText>(R.id.EditText_Notatki)
        var pozycja_listy = findViewById<TextView>(R.id.textView_pozycji_listy)
        var recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        //Połączenie z adapterem
        //recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        //recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
       // recyclerView.adapter = MyAdapter(applicationContext, db)



    }

    fun DodajNotatke(view: View)
    {
        val intent = Intent(applicationContext,EdycjaNotatki::class.java)
        startActivity(intent)

    }
    override fun onResume() {
        super.onResume()

        var dbHelper = BazaDanych(applicationContext)
        var db = dbHelper.writableDatabase

        //Kursor pobiera wszystkie rekordy z bazy
        val cursor = db.query(FeedEntry.TABLE_NAME,null, null,
            null,null, null,null)
        //Uzupełnienie arraylisty
        val notes = ArrayList<Notatka>()
        //sprawdzenie czy w bazie danych istnieje przynajmniej jeden element
        if (cursor.count > 0)
        {
            //Umieszczenie kursora na pierwszym elementcie w bazie danych
            cursor.moveToFirst()
            //Sprawdzanie czy kursor nie przeszedł już całej bazy danych
            while(!cursor.isAfterLast)
            {
                //Utworzenie instancji klasy Notatka
                var note = Notatka()
                //Uzupełnienie elementów listy o typie Notatka
                note.id = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.message = cursor.getString(2)
                notes.add(note)
                //Przesunięcie kursora na kolejny element w bazie danych
                cursor.moveToNext()



            }
            //Zamknięcie kursora
            cursor.close()

        }

        var recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        recyclerView.layoutManager = GridLayoutManager(applicationContext,2)
        recyclerView.adapter = MyAdapter(applicationContext, db,notes)


    }
    //Rozdmuchanie przycisku dodającego po mainActivity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)


        return super.onCreateOptionsMenu(menu)
    }
    //Kod wykonywalny po wciśnięciu przycisku z dodanego menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //sprawdzenie, który przycisk został wciśnięty
        if (item?.itemId == R.id.menu_add_button)
        {
          //  DodajNotatke()
            val intent = Intent(applicationContext, EdycjaNotatki::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }

}