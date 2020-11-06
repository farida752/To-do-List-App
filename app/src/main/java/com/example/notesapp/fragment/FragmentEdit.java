package com.example.notesapp.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.db.NoteDao;
import com.example.notesapp.model.Note;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEdit extends Fragment {
NumberPicker np;
TextView textViewPriority;
EditText editTextViewDescription;
EditText editTextViewTitle;
TextView textViewCalender;
ImageView imageViewCalender;
Button buttonSave;

onFragmentInteractListener listener;

DatePickerDialog.OnDateSetListener setListener;

public static final String BUNDLE_TAG ="com.example.notesapp.fragment.BUNDLE_TAG";
Note oldNote =null;

//fields to format the constuctor of the Note object which would be send to the database method
    String title;
    String description;
    String date;
    int priority = 1;

    String state ;
    public FragmentEdit(String state) {
        // Required empty public constructor
        this.state=state;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_fragment_edit, container, false );
    }
//_____________________________________________________________________________________________________________________________________________

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle oldDataBundle =getArguments();
        if(state.equals("update")){
        oldNote = oldDataBundle.getParcelable(BUNDLE_TAG);}
    }
//______________________________________________________________________________________________________________________________________________
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //all the findViewById lines
        findViews(view);
        //_________________________________________________________
        //if the state is update and the oldNote != null put the old data in the views
        if(state.equals("update")&& oldNote!= null){
            priority=oldNote.getPriority();
            date=oldNote.getDate();
            textViewPriority.setText(String.valueOf(priority));
            editTextViewTitle.setText(oldNote.getTitle());
            editTextViewDescription.setText(oldNote.getDescription());
            textViewCalender.setText(date);
        }
         //____________________________________________________________
        //set the number picker attributes
        np.setMinValue(1);
        np.setMaxValue(3);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                textViewPriority.setText(String.valueOf(newValue));
                priority = newValue;
            }
        });
        //____________________________________________________________
        //manage the calender and get the date from it on click at the image
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        imageViewCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext()
                        ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year , int month , int dayOfMonth) {
                       /* month =month+1;
                        date = day+"/"+month+"/"+year;*/
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, month);
                   c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                  date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                        textViewCalender.setText(date);
                    }},year,month,day);

                datePickerDialog.show();
            }
        });

        //____________________________________________________________
        //on button save click collect the new data in a note object and
        // send it to the suitable data base method and
        // refresh the recycler in the mainActivity
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = editTextViewTitle.getText().toString();
                description = editTextViewDescription.getText().toString();

                Log.i("priority","priority from the fragment edit num picker "+ priority);

                //check if some views is empty before calling the data base methods
                if(title.trim().equals("")||description.trim().equals("")||date.trim().equals("")||priority==0){
                    Toast.makeText(getActivity(), "some fields are empty please fill them first", Toast.LENGTH_SHORT).show();
                }else{
                //calling the data base update function
                NoteDao dao =new NoteDao(getActivity());
                if(state.equals("update")){
                    Note editedNote = new Note(priority,title,description,date,oldNote.getId());
                    dao.updateNote(editedNote);
                   // listener.refresh();
                }else if (state.equals("add")){
                    Note addedNote = new Note(priority,title,description,date);
                    dao.addNote(addedNote);
                   // listener.refresh();
                }

               getActivity().getSupportFragmentManager().popBackStack();
                listener.refreshDataBase();

            }}
        });

    }
//_______________________________________________________________________________________________________________________________________________
    public static FragmentEdit newInstance(String state){
        FragmentEdit fragmentEdit= new FragmentEdit (state);
        return fragmentEdit;
    }
    public static FragmentEdit newInstance(String state,Note oldNote){
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_TAG,oldNote);
        FragmentEdit fragmentEdit= new FragmentEdit (state);
        fragmentEdit.setArguments(bundle);
        return fragmentEdit;
    }

    public interface onFragmentInteractListener{
        void refreshDataBase();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentInteractListener){
            listener=(onFragmentInteractListener)context;
        }
    }

    public void findViews(View view){
        np = view.findViewById(R.id.number_picker);
        textViewPriority = view.findViewById(R.id.editting_text_view_priority);
        editTextViewTitle = view.findViewById(R.id.edit_text_view_title);
        editTextViewDescription = view.findViewById(R.id.edit_text_view_discription);
        textViewCalender = view.findViewById(R.id.editting_text_view_date);
        imageViewCalender = view.findViewById(R.id.image_view_calender);
        buttonSave = view.findViewById(R.id.button_save);
    }
}
