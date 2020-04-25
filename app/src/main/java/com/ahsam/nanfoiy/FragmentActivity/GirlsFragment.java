package com.ahsam.nanfoiy.FragmentActivity;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahsam.nanfoiy.Adapter.NameAdapter;
import com.ahsam.nanfoiy.DatabaseHelper;
import com.ahsam.nanfoiy.Item.Item;
import com.ahsam.nanfoiy.OnTapListener;
import com.ahsam.nanfoiy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GirlsFragment extends Fragment {

    private RecyclerView recyclerViewGirl;
    private EditText editText;
    private ImageView searchButton;
    private ImageView cancelButton;
    private DatabaseHelper databaseHelper;
    private ArrayList<Item> arrayList = new ArrayList<Item>();
    private ArrayList<Item> arrayListSelection = new ArrayList<Item>();
    Cursor cursor;
    Cursor cursorSelection;
    private NameAdapter nameAdapter;
    private NameAdapter nameAdapterSelection;
    private int[] nameIndex = new int[]{0, 163, 224, 265, 286, 334, 401, 428, 478, 495, 663, 743, 898, 1033, 1076, 1091, 1129, 1137, 1286, 1319, 1387, 1410, 1456, 1497, 1661, 1816, 1857, 1899};
    private int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view =  (ViewGroup)inflater.inflate(R.layout.list_item, container, false);
        recyclerViewGirl = view.findViewById(R.id.recyclerView);
        editText = view.findViewById(R.id.search_edit_text);
        searchButton = view.findViewById(R.id.search_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        loadDatabase();

        BottomNavigationView navigation = getActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);

        return view;
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final LinearLayout letterLayout = view.findViewById(R.id.letters);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(40,5,50,5);

        String[] letters = getResources().getStringArray(R.array.letter_array);

        for (int i=0; i<28; i++) {
            TextView letterText = new TextView(getContext());
            letterText.setPadding(5,15,5,20);
            letterText.setTextSize(30);
            letterText.setText(letters[i]);
            letterText.setTypeface(getResources().getFont(R.font.arabtype), Typeface.BOLD);
            letterText.setLayoutParams(params);
            letterLayout.addView(letterText);
        }
        for (int i = 0; i < letterLayout.getChildCount(); i++){
            TextView letterTextViewGirl = (TextView)letterLayout.getChildAt(i);

            letterTextViewGirl.setTag(i);

//            System.out.println("Girl "+letterTextViewGirl.getTag());

            letterTextViewGirl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (v.getTag() instanceof Integer) {
                        position = (Integer) v.getTag();
                    }

                    if(position == 0)
                        recyclerViewGirl.scrollToPosition(nameIndex[position]+10);
                    else
                        recyclerViewGirl.scrollToPosition(nameIndex[position]-10);
                    recyclerViewGirl.smoothScrollToPosition(nameIndex[position]);



                }
            });
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence searchCharSequence = editText.getText();
                loadDatabaseCriteria(searchCharSequence);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatabase();
                editText.setText("");
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }


    public void loadDatabase() {
        databaseHelper = new DatabaseHelper(getActivity());
        try {
            databaseHelper.checkAndCopyDatabase();
            databaseHelper.openDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            cursor = databaseHelper.QueryData("select * from babynames where gender = 'އަންހެން' ORDER BY akuru");
            if (cursor!= null) {
                if(cursor.moveToFirst()){
                    do {
                        Item item = new Item();
                        item.setArabic(cursor.getString(1));
                        item.setDivehi(cursor.getString(0));
                        item.setMeaning(cursor.getString(3));
                        arrayList.add(item);

                    }while (cursor.moveToNext());
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        nameAdapter = new NameAdapter(getActivity(), arrayList);
        nameAdapter.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position) {
//                Toast.makeText(getContext(), "Click to " +position,Toast.LENGTH_SHORT).show();

            }
        });

        recyclerViewGirl.setHasFixedSize(true);
        recyclerViewGirl.setLayoutManager(linearLayoutManager);
        recyclerViewGirl.setAdapter(nameAdapter);

        databaseHelper.close();

    }

    public void loadDatabaseCriteria(CharSequence editedText) {

        databaseHelper = new DatabaseHelper(getActivity());
        try {
            databaseHelper.checkAndCopyDatabase();
            databaseHelper.openDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cursorSelection = null;
            arrayListSelection.clear();
            cursorSelection = databaseHelper.QueryData("select * from babynames where gender = 'އަންހެން' and nan like '%" +editedText+ "%' or arabic like '%" +editedText+ "%' ORDER BY akuru");
            if (cursorSelection != null) {
                if(cursorSelection.moveToFirst()){
                    do {
                        Item itemSelection = new Item();
                        itemSelection.setArabic(cursorSelection.getString(1));
                        itemSelection.setDivehi(cursorSelection.getString(0));
                        itemSelection.setMeaning(cursorSelection.getString(3));
                        arrayListSelection.add(itemSelection);

                    }while (cursorSelection.moveToNext());
                }
            }

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (arrayListSelection.size() == 0) {
            Toast.makeText(getContext(), "އެއްވެސް ނަމަކާ މެޗެއް ނުވޭ", Toast.LENGTH_SHORT).show();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        nameAdapterSelection = new NameAdapter(getActivity(), arrayListSelection);
        nameAdapterSelection.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position) {
                Toast.makeText(getContext(), "Click to " +position,Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewGirl.setHasFixedSize(true);
        recyclerViewGirl.setLayoutManager(linearLayoutManager);
        recyclerViewGirl.setAdapter(nameAdapterSelection);

        databaseHelper.close();
    }
}
