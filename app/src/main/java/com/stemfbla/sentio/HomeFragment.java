package com.stemfbla.sentio;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        android.widget.TextView schoolName = (android.widget.TextView) view.findViewById(com.stemfbla.sentio.R.id.textView);
        android.widget.TextView schoolDescription = (android.widget.TextView) view.findViewById(R.id.textView9);
        ArrayList<File> imageList = new ArrayList<File>();
        try {
            schoolName.setText(SchoolActivity.schoolData.getString("school_name"));
            schoolDescription.setText(SchoolActivity.schoolData.getString("description"));
            if(!SchoolActivity.schoolData.getString("logo").equals("")) imageList.add
                    (SchoolActivity.saveImage(getActivity(), SchoolActivity.schoolData.getString("logo")));
            for(int a = 0; a < SchoolActivity.schoolData.getJSONArray("images").length(); a++) {
                if(SchoolActivity.schoolData.getJSONArray("images").getJSONObject(a).getInt
                        ("club_id") == 0) imageList.add(SchoolActivity.saveImage(getActivity(),
                        SchoolActivity.schoolData.getJSONArray("images").getJSONObject(a).getString("image_data")));
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        } finally {
            com.daimajia.slider.library.SliderLayout sliderShow;
            sliderShow = (com.daimajia.slider.library.SliderLayout) view.findViewById(R.id.slider);
            for(int a = 0; a < imageList.size(); a++) {
                com.daimajia.slider.library.SliderTypes.TextSliderView textSliderView = new com
                        .daimajia.slider.library.SliderTypes.TextSliderView(getActivity());
                textSliderView
                        .image(imageList.get(a))
                        .setScaleType(com.daimajia.slider.library.SliderTypes.BaseSliderView
                                .ScaleType.Fit);
                sliderShow.addSlider(textSliderView);
            }
        }
        return view;
    }
    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        try {
            if(com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("faculty").length() != 0) {
                menu.add(android.view.Menu.NONE,
                        1,
                        android.view.Menu.NONE,
                        "Faculty Members")
                        .setIcon(R.drawable.ic_action_group)
                        .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
            menu.add(android.view.Menu.NONE,
                    2,
                    android.view.Menu.NONE,
                    "School Information")
                    .setIcon(R.drawable.ic_action_information)
                    .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item.toString().equals("School Information")) {
            try {
                new android.app.AlertDialog.Builder(getActivity())
                        .setTitle("School Information")
                        .setMessage(SchoolActivity.schoolData.getString
                                ("information"))
                        .show();
            } catch(org.json.JSONException e) {
                e.printStackTrace();
            }
        } else if(item.toString().equals("Faculty Members")) {
            startActivity(new android.content.Intent(getActivity(), FacultyActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}