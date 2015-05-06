package com.stemfbla.sentio;

public class CalendarArrayAdapter extends android.widget.ArrayAdapter<com.stemfbla.sentio.CalendarData> {
    private final android.content.Context context;
    private final java.util.ArrayList<com.stemfbla.sentio.CalendarData> values;

    public CalendarArrayAdapter(android.content.Context context, java.util.ArrayList<CalendarData> values) {
        super(context, com.stemfbla.sentio.R.layout.adapter_listview, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        android.view.LayoutInflater inflater = (android.view.LayoutInflater) context
                .getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        android.view.View rowView = inflater.inflate(com.stemfbla.sentio.R.layout.adapter_listview, parent, false);
        android.widget.TextView clubName = (android.widget.TextView) rowView.findViewById(com
                .stemfbla.sentio.R.id.club_name);
        android.widget.TextView eventName = (android.widget.TextView) rowView.findViewById(com
                .stemfbla.sentio.R.id.event_name);
        android.widget.TextView date = (android.widget.TextView) rowView.findViewById(com
                .stemfbla.sentio.R.id.date);
        android.widget.TextView time = (android.widget.TextView) rowView.findViewById(com
                .stemfbla.sentio.R.id.time);
        clubName.setText(values.get(position).clubName);
        eventName.setText(values.get(position).eventName);
        try {
            date.setText(new java.text.SimpleDateFormat("MM-dd-yyyy").format(new java.text
                    .SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(values.get(position).dateTime.toString())).substring(new java.text.SimpleDateFormat("MM-dd-yyyy").format(new java.text
                    .SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(values.get(position).dateTime.toString())).charAt(0) == '0' ? 1 : 0));
            time.setText(new java.text.SimpleDateFormat("KK:mm a").format(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values.get(position).dateTime.toString())));
        } catch(java.text.ParseException e) {
            e.printStackTrace();
        }
        return rowView;
    }
}