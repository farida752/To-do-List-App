package com.example.notesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.adapter.RecyclerNoteAdapter;
import com.example.notesapp.db.NoteDao;
import com.example.notesapp.fragment.FragmentEdit;
import com.example.notesapp.model.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements FragmentEdit.onFragmentInteractListener {

    RecyclerView recyclerViewNote ;
    ImageView buttonAddNote;
    NoteDao dao;
    ArrayList<Note> notes ;
    RelativeLayout backgroundLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

         dao =new NoteDao(this);
        buttonAddNote = findViewById(R.id.button_add_note);
       /* ArrayList<Note> notes=new ArrayList<> ();
        notes.add(new Note ( 1,"home work","science lab ","20/12/2020" ));
        notes.add(new Note ( 1,"home work","science lab ","20/12/2020" ));
        notes.add(new Note ( 1,"home work","science lab ","20/12/2020" ));*/
        notes = getDataFromDB();
       setRecyclerViewNote ( notes );
       setAddNotesButton();


    }
    public ArrayList<Note> getDataFromDB(){
        //select all from dp
        return dao.getAllNotes();
    }


    public void setRecyclerViewNote(final ArrayList<Note> notes){
        backgroundLayout = findViewById(R.id.background_layout);
        if(notes.size()==0){
           backgroundLayout.setBackgroundResource(R.drawable.emptybackground);
        }else{
            backgroundLayout.setBackgroundColor(getResources().getColor(R.color.background_color));
        }
        recyclerViewNote = findViewById ( R.id.recycle_view );
        final RecyclerNoteAdapter adapter = new RecyclerNoteAdapter (notes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( this );
        recyclerViewNote.setLayoutManager ( layoutManager );
        recyclerViewNote.setAdapter ( adapter );

        adapter.setListener ( new RecyclerNoteAdapter.onItemClickListener () {
            @Override
            public void onArrowClick(boolean isExpandable,int position) {
                if(isExpandable){
                    adapter.notes.get (position).setExpandable ( false );
                    adapter.notifyDataSetChanged ();
                }else{
                    adapter.notes.get (position).setExpandable ( true );
                    adapter.notifyDataSetChanged ();
                }
            }

            @Override
            public void onButtonEditClick(int position , Note oldNote) {

                getSupportFragmentManager ().beginTransaction ()
                        .replace (android.R.id.content, FragmentEdit.newInstance ("update",oldNote) )
                        .addToBackStack("EDIT FRAGMENT")
                        .commit ();

                //refreshDataBase();
            }


        } );

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
             int position =viewHolder.getAdapterPosition();

             Note currentNote = notes.get(position);
                dao.deleteNote(currentNote);
                notes.remove(position);
                adapter.notifyDataSetChanged();

                if(notes.size()==0){
                    backgroundLayout.setBackgroundResource(R.drawable.emptybackground);
                }else{
                    backgroundLayout.setBackgroundColor(getResources().getColor(R.color.background_color));
                }
            }
        }).attachToRecyclerView(recyclerViewNote);


    }

    public void setAddNotesButton(){
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add to the data base anew Note
                // we can use the same edit fragment but we will have to make a variable
                // that refears to the state of the fragment wheter it is add to the database or update an existing one

               // refreshDataBase();

                getSupportFragmentManager ().beginTransaction ()
                        .replace (android.R.id.content, FragmentEdit.newInstance ("add") )
                        .addToBackStack("EDIT FRAGMENT")
                        .commit ();

               // refreshDataBase();
            }
        });
    }

    public void refreshDataBase(){
        notes = getDataFromDB();
        setRecyclerViewNote ( notes );
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        notes = getDataFromDB();
        setRecyclerViewNote ( notes );
    }*/

    /*@Override
    protected void onRestart() {
        super.onRestart();
        notes = getDataFromDB();
        setRecyclerViewNote ( notes );
    }*/
}
