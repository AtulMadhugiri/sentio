package com.stemfbla.sentio;

public class CalendarFragment extends android.support.v4.app.Fragment {
    private OnFragmentInteractionListener mListener;
    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }
    public CalendarFragment() { }

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
                             android.os.Bundle savedInstanceState) {
        return inflater.inflate(com.stemfbla.sentio.R.layout.fragment_calendar, container, false);
    }

    public void onButtonPressed(android.net.Uri uri) {
        if(mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(android.app.Activity activity) {
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(android.net.Uri uri);
    }
}