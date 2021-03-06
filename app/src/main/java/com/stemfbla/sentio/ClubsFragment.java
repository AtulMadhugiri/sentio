package com.stemfbla.sentio;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ClubsFragment extends android.support.v4.app.ListFragment {

    public ClubsFragment() { }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[0];
        try {
            values = new String[com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("clubs").length()];
            for(int a = 0; a < values.length; a++) {
                values[a] = com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("clubs")
                        .getJSONObject(a).getString("club_name");
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }

        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(getActivity(),
                    R.layout.list_clubs, values);
            setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(android.widget.ListView l, View v, int position, long id) {
        android.content.Intent x = new android.content.Intent(getActivity(), ClubPageActivity.class);
        int clubId = -1;
        try {
            for(int a = 0; a < SchoolActivity.schoolData.getJSONArray("clubs").length(); a++) {
                if(SchoolActivity.schoolData.getJSONArray("clubs").getJSONObject(a).getString
                    ("club_name").equals(((String)l.getItemAtPosition(position))))
                    clubId = com.stemfbla.sentio.SchoolActivity.schoolData.getJSONArray("clubs")
                            .getJSONObject(a).getInt("club_id");
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
        x.putExtra("page", clubId);
        startActivity(x);
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