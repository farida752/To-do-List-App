package com.example.notesapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.model.Note;

import java.util.ArrayList;

public class RecyclerNoteAdapter extends RecyclerView.Adapter<RecyclerNoteAdapter.NoteViewHolder> {

    public interface onItemClickListener{
        void onArrowClick(boolean isExpandable,int positon);
        void onButtonEditClick (int position ,Note oldNote);

    }

    public ArrayList<Note> notes;
    onItemClickListener listener;
    ViewGroup parent;

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public RecyclerNoteAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from ( parent.getContext ()).inflate ( R.layout.recycler_layout ,parent,false);
        NoteViewHolder holder= new NoteViewHolder(v);
        this.parent= parent;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
    Note currentNote =notes.get ( position );
    holder.TextViewTitle.setText ( currentNote.getTitle () );
    holder.TextViewDescription.setText ( currentNote.getDescription () );
    holder.TextViewDeadline.setText ( currentNote.getDate () );
    holder.TextViewPriority.setText ( String.valueOf (currentNote.getPriority ()));

    int currentPriority = currentNote.getPriority ();

        Log.i("priority","priority from the recycler adapter on bind method "+ currentPriority+currentNote.getTitle());

    switch (currentPriority){
        case 1:
            holder.priorityLine.setBackgroundColor(parent.getResources().getColor(R.color.color1_red));
            break;
        case 2:
            holder.priorityLine.setBackgroundColor(parent.getResources().getColor(R.color.color2_blue));
            break;
        case 3:
            holder.priorityLine.setBackgroundColor(parent.getResources().getColor(R.color.color3_green));
            break;
        default:
            break;
    }
    if(currentNote.isExpandable ()) {
        holder.arrow.setImageResource ( R.drawable.ic_arrow_up );
        holder.layoutExpandable.setVisibility ( View.VISIBLE );
     }else{
        holder.arrow.setImageResource ( R.drawable.ic_arrow_down );
        holder.layoutExpandable.setVisibility ( View.GONE );
     }
    }

    @Override
    public int getItemCount() {
        return notes.size ();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView TextViewTitle;
        TextView TextViewDescription;
        TextView TextViewPriority;
        TextView TextViewDeadline;
        Button buttonEdit;
        ImageView arrow;
        LinearLayout layoutExpandable;
        View priorityLine;

        public NoteViewHolder(@NonNull View itemView) {
            super ( itemView );

             TextViewTitle=itemView.findViewById ( R.id.text_view_title );
             TextViewDescription=itemView.findViewById ( R.id .text_view_description);
             TextViewPriority=itemView.findViewById ( R.id.text_view_priority );
             TextViewDeadline=itemView.findViewById ( R.id.text_view_deadline );
             buttonEdit=itemView.findViewById ( R.id.button_edit );
             arrow= itemView.findViewById ( R.id.image_arrow );
             layoutExpandable= itemView.findViewById ( R.id.expandable_layout );
             priorityLine= itemView.findViewById ( R.id.priority_line);

             arrow.setOnClickListener ( new View.OnClickListener () {
                 @Override
                 public void onClick(View view) {
                     if(listener!=null){
                         int position = getAdapterPosition ();
                         if(position!=RecyclerView.NO_POSITION){
                             Note currentnote =notes.get ( position );
                             listener.onArrowClick ( currentnote.isExpandable (),position );
                         }
                     }
                 }
             } );

             buttonEdit.setOnClickListener ( new View.OnClickListener () {
                 @Override
                 public void onClick(View view) {
                     if(listener!=null){
                         int position = getAdapterPosition ();
                         if(position!=RecyclerView.NO_POSITION){
                             listener.onButtonEditClick (position,notes.get(position));
                         }
                     }
                 }
             } );


        }
    }
}
