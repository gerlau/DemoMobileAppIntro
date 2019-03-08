package com.example.android.documentlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentA extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String TITLE   =  "title";
    private static final String DESCRIPTION = "description";
    private static final String DRAWABLE_ID = "drawableId";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public static FragmentA newInstance(int drawableId, String title, String description){
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        args.putInt(DRAWABLE_ID, drawableId);
        args.putString(TITLE, title);
        args.putString(DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }


    public FragmentA() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_fragment_main, container, false);

        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(getArguments().getString(DESCRIPTION));

        TextView title = rootView.findViewById(R.id.title_one);
        title.setText(getArguments().getString(TITLE));


        ViewStub viewStub = rootView.findViewById(R.id.view_stub);

        viewStub.setLayoutResource(R.layout.drawable_layout);
        viewStub.inflate();

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageview);
        imageView.setImageResource(getArguments().getInt(DRAWABLE_ID));



        return rootView;
    }
}
