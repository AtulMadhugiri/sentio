package com.stemfbla.sentio;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ClubsFragment extends android.support.v4.app.ListFragment {

    private OnFragmentInteractionListener mListener;
    public static ClubsFragment newInstance() {
        ClubsFragment fragment = new ClubsFragment();
        return fragment;
    }

    public ClubsFragment() { }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[] { "Envirostories",
                "Ethics Bowl",
                "Future Business Leaders of America",
                "HOSA Future Health Professionals",
                "Link Crew",
                "Math Club",
                "Model United Nations",
                "National Art Honor Society",
                "National Honor Society",
                "Peer Tutor Lab",
                "PHASER",
                "Quantum Robotics",
                "Relay for Life",
                "Science Bowl",
                "Technology Student Association" };
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
        }

        @Override
        public void onListItemClick(android.widget.ListView l, View v, int position, long id) {
            android.content.Intent x = new android.content.Intent(getActivity(), ClubPageActivity.class);
            x.putExtra("page", String.valueOf(position));
            startActivity(x);
        }

    public void onButtonPressed(Uri uri) {
        if(mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}