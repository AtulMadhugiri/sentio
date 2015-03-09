package com.stemfbla.sentio;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    private com.daimajia.slider.library.SliderLayout sliderShow;
    private OnFragmentInteractionListener mListener;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderShow = (com.daimajia.slider.library.SliderLayout) view.findViewById(R.id.slider);
        int file_maps[] = new int[6];
            file_maps[0] = R.drawable.stem3;
            file_maps[1] = R.drawable.stem1;
            file_maps[2] = R.drawable.stem0;
            file_maps[3] = R.drawable.stem2;
            file_maps[4] = R.drawable.stem4;
            file_maps[5] = R.drawable.stem5;
        sliderShow.setDuration(5000);
        for(int a = 0; a < file_maps.length; a++){
            com.daimajia.slider.library.SliderTypes.TextSliderView textSliderView = new com
                    .daimajia.slider.library.SliderTypes.TextSliderView(getActivity());
            textSliderView
                    .image(file_maps[a])
                    .setScaleType(com.daimajia.slider.library.SliderTypes.BaseSliderView.ScaleType.Fit);
            sliderShow.addSlider(textSliderView);
        }
        return view;
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}