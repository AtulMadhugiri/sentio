package com.stemfbla.sentio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;

public class SchoolActivity extends Activity {
    // SharedPreferences denote if logged-in
    public static android.content.SharedPreferences sharedpreferences;
    // JSON Object data accessed by rest of application
    public static org.json.JSONObject schoolData;
    EditText schoolCode;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        // Check for entered school-code
        sharedpreferences = getSharedPreferences("sharedPrefs",android.content.Context.MODE_PRIVATE);

        // Check for Internet access
        if(!cd.isConnectingToInternet()) {
            if(sharedpreferences.contains("school_code")) {
                offlineMode(1, 1);
            } else offlineMode(0, 1);
        }
        // Skip page if logged-in
        if(sharedpreferences.contains("school_code")) {
            offlineMode(1, 0);
        }

        schoolCode = (android.widget.EditText) findViewById(com
                .stemfbla
                .sentio.R.id.editText);
        android.widget.Button offline = (android.widget.Button) findViewById(com.stemfbla.sentio
                .R.id.offline_button);
        android.widget.Button submit = (android.widget.Button) findViewById(com.stemfbla.sentio.R.id.button);
        android.widget.ImageView schoolhouse = (android.widget.ImageView) findViewById(com.stemfbla
                .sentio.R.id.imageView2);

        // Click on schoolhouse for school list
        schoolhouse.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                new GetSchools().execute();
            }
        });

        offline.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                offlineMode(0, 1);
            }
        });

        // Start sync with school code
        submit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                new SyncData().execute(new String[]{schoolCode.getText().toString()});
            }
        });
    }
    public void offlineMode(int mode, int out) {
        if(mode == 0) {
            try {
                java.io.InputStream input = getAssets().open("stem.json");
                byte[] buffer = new byte[input.available()];
                input.read(buffer);
                input.close();
                schoolData = new org.json.JSONObject(new String(buffer));
            } catch(java.io.IOException e) {
                e.printStackTrace();
            } catch(org.json.JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                java.io.FileInputStream fin = openFileInput("data.json");
                java.io.InputStreamReader isr = new java.io.InputStreamReader(fin);
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    sb.append(line);
                fin.close();
                isr.close();
                bufferedReader.close();
                schoolData = new org.json.JSONObject(sb.toString());
            } catch(java.io.FileNotFoundException e) {
                e.printStackTrace();
            } catch(java.io.IOException e) {
                e.printStackTrace();
            } catch(org.json.JSONException e) {
                e.printStackTrace();
            }
        }
        if(out == 1) android.widget.Toast.makeText(getApplicationContext(), "Offline mode enabled!",
                android.widget.Toast.LENGTH_SHORT).show();
        startActivity(new android.content.Intent(SchoolActivity.this, MainActivity.class));
        finish();
    }
    public static java.io.File saveImage(final android.content.Context context, final String imageData) throws java.io.IOException {
        final byte[] imgBytesData = android.util.Base64.decode(imageData,
                android.util.Base64.DEFAULT);

        final java.io.File file = java.io.File.createTempFile("image", null, context.getCacheDir());
        final java.io.FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new java.io.FileOutputStream(file);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        final java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(
                fileOutputStream);
        try {
            bufferedOutputStream.write(imgBytesData);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fileOutputStream.close();
                bufferedOutputStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    private class SyncData extends android.os.AsyncTask<String, Integer, org.json.JSONObject> {
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new android.app.ProgressDialog(SchoolActivity.this);
            dialog.setMessage("Syncing school data...");
            dialog.setCancelable(false);
            dialog.show();
        }
        protected org.json.JSONObject doInBackground(String... code) {
            org.apache.http.HttpEntity httpEntity = null;
            try {
                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
                HttpConnectionParams.setSoTimeout(httpParams, 2000);
                DefaultHttpClient client = new org.apache.http.impl.client.DefaultHttpClient(httpParams);
                HttpGet httpGet = new org.apache.http.client
                        .methods.HttpGet("http://api.apoorvk.me/sentio/data.php?code=" + code[0]);
                httpEntity = client.execute(httpGet).getEntity();
            } catch (org.apache.http.client.ClientProtocolException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            org.json.JSONObject jsonObject = null;
            String entityResponse;
            if (httpEntity != null) {
                try {
                    entityResponse = org.apache.http.util.EntityUtils.toString(httpEntity);
                    if(!entityResponse.equals("")) jsonObject = new org.json.JSONObject(entityResponse);
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        protected void onPostExecute(org.json.JSONObject result) {
            if (dialog.isShowing())
                dialog.dismiss();
            if(result == null) {
                new android.app.AlertDialog.Builder(SchoolActivity.this)
                        .setTitle("Invalid School Code")
                        .setMessage("Please enter your school code and submit again.")
                        .show();
            } else {
                java.io.FileOutputStream fos = null;
                try {
                    fos = openFileOutput("data.json", android.content.Context.MODE_PRIVATE);
                    fos.write(result.toString().getBytes());
                    fos.close();
                } catch(java.io.FileNotFoundException e) {
                    e.printStackTrace();
                } catch(java.io.IOException e) {
                    e.printStackTrace();
                }
                schoolData = result;
                android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
                try {
                    editor.putString("school_code", schoolData.getString("school_code"));
                } catch(org.json.JSONException e) {
                    e.printStackTrace();
                }
                editor.commit();
                startActivity(new android.content.Intent(SchoolActivity.this, MainActivity.class));
                finish();
            }
        }
    }
    private class GetSchools extends android.os.AsyncTask<String, Integer, org.json.JSONArray> {
        protected org.json.JSONArray doInBackground(String... code) {
            org.apache.http.HttpEntity httpEntity = null;
            try {
                org.apache.http.impl.client.DefaultHttpClient client = new org.apache.http.impl.client.DefaultHttpClient();
                org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client
                        .methods.HttpGet("http://api.apoorvk.me/sentio/schools.php");
                httpEntity = client.execute(httpGet).getEntity();
            } catch (org.apache.http.client.ClientProtocolException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = null;
            String entityResponse;
            if (httpEntity != null) {
                try {
                    entityResponse = org.apache.http.util.EntityUtils.toString(httpEntity);
                    if(!entityResponse.equals("")) jsonArray = new org.json.JSONArray(entityResponse);
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
            return jsonArray;
        }

        protected void onPostExecute(JSONArray result) {
            if(result == null) {
                new android.app.AlertDialog.Builder(SchoolActivity.this)
                        .setTitle("Network Problems")
                        .setMessage("Please try again later.")
                        .show();
            } else {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(SchoolActivity.this);
                builderSingle.setIcon(R.drawable.grayschool).setTitle("School List:");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SchoolActivity.this, android.R.layout.select_dialog_singlechoice);
                try {
                    for(int a = 0; a < result.length(); a++)
                        arrayAdapter.add(result.getJSONObject(a).getString("school_name") + " (" +
                                result.getJSONObject(a).getString("school_code").toUpperCase() + ")");
                } catch(org.json.JSONException e) {
                    e.printStackTrace();
                }
                builderSingle.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which)
                    { dialog.dismiss(); }
                });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        schoolCode.setText(arrayAdapter.getItem(which).substring(arrayAdapter.getItem(which).lastIndexOf("(") + 1, arrayAdapter.getItem(which).length() - 1));
                    }
                });
                builderSingle.show();
            }
        }
    }
}