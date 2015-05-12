package com.stemfbla.sentio;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONException;

public class CalendarFragment extends Fragment {
    private android.widget.ListView mListView;
    //private FloatingActionButton fab;
    java.util.ArrayList<com.stemfbla.sentio.CalendarData> data;
    com.stemfbla.sentio.CalendarArrayAdapter adapter;

    public CalendarFragment() {}

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, android.view.ViewGroup container,
                                          android.os.Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        mListView = (android.widget.ListView) rootView.findViewById(com.stemfbla.sentio.R.id
                .calendarList);
        //fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        //fab.attachToListView(mListView);
        //fab.hide();
        data = new java.util.ArrayList<CalendarData>();
        lAll();
        adapter = new CalendarArrayAdapter(getActivity(), data);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, final View view,
                                    int position, long id) {
                try {
                    for(int a = 0; a < SchoolActivity.schoolData.getJSONArray
                            ("events").length(); a++) {
                        if(SchoolActivity.schoolData.getJSONArray("events").getJSONObject(a)
                                .getString("event_name").equals(((android.widget.TextView)view.findViewById(com.stemfbla.sentio.R
                                        .id.event_name)).getText().toString())) {
                            final int x = a;
                            View layout = inflater.inflate(R.layout.event_dialog, null);
                            new AlertDialog.Builder(getActivity())
                                    .setView(layout).setTitle(
                                    ((android.widget.TextView) view.findViewById(
                                            com.stemfbla.sentio.R.id.event_name)).getText().toString() + " Description")
                                    .setMessage(SchoolActivity.schoolData.getJSONArray(
                                            "events").getJSONObject(a).getString("description"))
                                            .show();
                            FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent calIntent = new Intent(Intent.ACTION_INSERT);
                                        calIntent.setType("vnd.android.cursor.item/event");
                                        calIntent.putExtra(CalendarContract.Events.TITLE,
                                                ((android.widget.TextView) view.findViewById(
                                                        com.stemfbla.sentio.R.id.event_name)).getText().toString());
                                        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, SchoolActivity.schoolData.getString(
                                                "school_name"));
                                        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, SchoolActivity.schoolData.getJSONArray(
                                                "events").getJSONObject(x).getString("description"));
                                        startActivity(calIntent);
                                    } catch(JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            break;
                        }
                    }
                } catch(org.json.JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        CaldroidFragment caldroidFragment = new com.roomorama.caldroid.CaldroidFragment();
        Bundle args = new android.os.Bundle();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        args.putInt(com.roomorama.caldroid.CaldroidFragment.MONTH, cal.get(java.util.Calendar.MONTH) + 1);
        args.putInt(com.roomorama.caldroid.CaldroidFragment.YEAR, cal.get(java.util.Calendar.YEAR));
        caldroidFragment.setArguments(args);
        caldroidFragment.setMinDateFromString("01/09/2014", "dd/MM/yyyy");
        caldroidFragment.setMaxDateFromString("16/06/2015", "dd/MM/yyyy");
        try {
            for(int a = 0; a < SchoolActivity.schoolData.getJSONArray("events").length(); a++) {
                caldroidFragment.setBackgroundResourceForDate(com.stemfbla.sentio.R.color.colorAccent,
                        new java.text.SimpleDateFormat("yyyy-MM-dd").parse(SchoolActivity.schoolData
                                .getJSONArray("events").getJSONObject(a)
                                .getString("date_time")));
                caldroidFragment.setTextColorForDate(com.stemfbla.sentio.R.color.white,
                        new java.text.SimpleDateFormat("yyyy-MM-dd").parse(SchoolActivity.schoolData
                                .getJSONArray("events").getJSONObject(a)
                                .getString("date_time")));
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        } catch(java.text.ParseException e) {
            e.printStackTrace();
        }
        caldroidFragment.setCaldroidListener(new com.roomorama.caldroid.CaldroidListener() {
            @Override
            public void onSelectDate(java.util.Date date, android.view.View view) {
                data.removeAll(data);
                try {
                    org.json.JSONArray array = SchoolActivity.schoolData.getJSONArray("events");
                    for(int a = 0; a < array.length(); a++) {
                        if(new java.text.SimpleDateFormat("yyyy-MM-dd").format(
                                new java.text.SimpleDateFormat("EEE MMM d k:m:s z yyyy").parse(
                                        date.toString())).equals(
                                array.getJSONObject(a).getString("date_time").substring(0,
                                        array.getJSONObject(a).getString("date_time").indexOf(
                                                " ")))) data.add(new CalendarData(
                                SchoolActivity.schoolData.getJSONArray("events").getJSONObject(
                                        a).getString("event_name"),
                                SchoolActivity.schoolData.getJSONArray("events").getJSONObject(
                                        a).getString("club_name"), new hirondelle.date4j.DateTime(
                                SchoolActivity.schoolData.getJSONArray("events").getJSONObject(
                                        a).getString("date_time"))));
                    }
                } catch(org.json.JSONException e) {
                    e.printStackTrace();
                } catch(java.text.ParseException e) {
                    e.printStackTrace();
                }
                mListView.invalidateViews();
            }
        });
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction t = fragManager.beginTransaction();
        t.replace(R.id.calendar, caldroidFragment);
        t.commit();
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(android.view.Menu.NONE,
                0,
                android.view.Menu.NONE,
                "All Events")
                .setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item.toString().equals("All Events")) lAll();
        return super.onOptionsItemSelected(item);
    }

    public void lAll() {
        data.removeAll(data);
        try {
            for(int a = 0; a < SchoolActivity.schoolData.getJSONArray("events").length(); a++) {
                data.add(new CalendarData(SchoolActivity.schoolData.getJSONArray("events")
                        .getJSONObject(a).getString("event_name"),
                        SchoolActivity.schoolData.getJSONArray("events").getJSONObject(a)
                                .getString("club_name"), new hirondelle.date4j.DateTime
                        (SchoolActivity.schoolData.getJSONArray("events").getJSONObject(a)
                                .getString("date_time"))));
            }
        } catch(org.json.JSONException e) {
            e.printStackTrace();
        }
        mListView.invalidateViews();
    }
    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}