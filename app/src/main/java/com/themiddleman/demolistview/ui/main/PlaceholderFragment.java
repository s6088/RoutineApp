package com.themiddleman.demolistview.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.themiddleman.DBHelper;
import com.themiddleman.MyClass;
import com.themiddleman.demolistview.MainActivity;
import com.themiddleman.demolistview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {


    private ListView classList;
    private TextView cntv;
    private int [] timgs = {R.drawable.nasirsir, R.drawable.uzzalsir, R.drawable.tanvirsir, R.drawable.fatemamaam};
    private String [] cnames = {"Dr Nasir", "Dr. Uzzal", "Tanvir Sir", "Shashi Maam"};
    private String [] rooms = {"Software Lab 1", "Lab 2", "Lab 3", "Lab4"};
    private String [] times = {"10 am - 12 pm", "10 am - 12 pm", "10 am - 12 pm", "10 am - 12 pm"};
    private List<MyClass> classes;
    private DBHelper dbHelper;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getContext());
        classes = new ArrayList<>();

        try{
            dbHelper.openDataBase();
            classes = dbHelper.getListClasses(4, 5, 1);
            dbHelper.closeDatabase();
        }
        catch (Exception e){

        }

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        classList = root.findViewById(R.id.classList);
        cntv = root.findViewById(R.id.courseName);
        CustomAdater customAdater = new CustomAdater();
        classList.setAdapter(customAdater);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //
            }
        });
        return root;
    }


    public class CustomAdater extends BaseAdapter {

        @Override
        public int getCount() {
            return classes.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item, null);

            ImageView teacherImage =  view.findViewById(R.id.teacherImg);
            TextView courseName = view.findViewById(R.id.courseName);
            TextView room = view.findViewById(R.id.room);
            TextView time = view.findViewById(R.id.time);

            MyClass myClass = classes.get(i);

            teacherImage.setImageResource(timgs[i]);
            courseName.setText( myClass.getCourse_name() );
            room.setText( myClass.getRoom() );
            time.setText( myClass.getHr() + ":" + myClass.getMin());




            return view;
        }

    }
}