package com.ahsam.nanfoiy.FragmentActivity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
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
public class BoysFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText searchText;
    private ImageView searchButton;
    private ImageView cancelButton;
    private DatabaseHelper databaseHelper;
    private ArrayList<Item> arrayListAll = new ArrayList<Item>();
    private ArrayList<Item> arrayListSelection = new ArrayList<Item>();
    Cursor cursor;
    Cursor cursorSelection;
    private NameAdapter nameAdapterAll;
    private NameAdapter nameAdapterSelection;
    private int[] nameIndex = new int[]{0, 362, 423,449, 468, 532, 628, 650, 687, 706, 913, 981, 1076, 1220, 1255, 1257, 1276, 1285, 1549, 1586, 1661, 1691, 1726, 1773, 2023, 2198, 2247, 2305};
    private int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.list_item, container, false);
        recyclerView = viewGroup.findViewById(R.id.recyclerView);
        searchText = viewGroup.findViewById(R.id.search_edit_text);
        searchButton = viewGroup.findViewById(R.id.search_button);
        cancelButton = viewGroup.findViewById(R.id.cancel_button);

        loadDatabase();

        BottomNavigationView navigation = getActivity().findViewById(R.id.navigation);
        navigation.setVisibility(View.VISIBLE);


        return viewGroup;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        LinearLayout letterLayout = view.findViewById(R.id.letters);
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
            TextView letterTextViewBoy = (TextView)letterLayout.getChildAt(i);

            letterTextViewBoy.setTag(i);

            letterTextViewBoy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (v.getTag() instanceof Integer) {
                        position = (Integer) v.getTag();
                    }

                    if(position == 0)
                        recyclerView.scrollToPosition(nameIndex[position]+10);
                    else
                        recyclerView.scrollToPosition(nameIndex[position]-10);
                    recyclerView.smoothScrollToPosition(nameIndex[position]);

                }
            });
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence searchCharSequence = searchText.getText();
                loadDatabaseCriteria(searchCharSequence);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatabase();
                searchText.setText("");
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

        cursor = databaseHelper.QueryData("select * from babynames where gender = 'ފިރިހެން' ORDER BY akuru");

        try {
            if (cursor != null) {
                if(cursor.moveToFirst()){
                    do {
                        Item itemAll = new Item();
                        itemAll.setArabic(cursor.getString(1));
                        itemAll.setDivehi(cursor.getString(0));
                        itemAll.setMeaning(cursor.getString(3));
                        arrayListAll.add(itemAll);

                    }while (cursor.moveToNext());
                }
            }

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        nameAdapterAll = new NameAdapter(getActivity(), arrayListAll);
        nameAdapterAll.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position) {
//                Toast.makeText(getContext(), "Click to " +position,Toast.LENGTH_SHORT).show();
//
//                .animate()
//                        .rotationX(360).rotationY(360)
//                        .setDuration(1000)
//                        .setInterpolator(new LinearInterpolator())
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animator) {
//                                recyclerView.setRotationX(0);
//                                recyclerView.setRotationY(0);
//                            }
//                        });
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nameAdapterAll);

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
            cursorSelection = databaseHelper.QueryData("select * from babynames where gender = 'ފިރިހެން' and nan like '%" +editedText+ "%' or arabic like '%" +editedText+ "%' ORDER BY akuru");
            System.out.println(editedText);
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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nameAdapterSelection);

        databaseHelper.close();

    }
}
