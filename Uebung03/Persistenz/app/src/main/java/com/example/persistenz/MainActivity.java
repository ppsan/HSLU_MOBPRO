package com.example.persistenz;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String FILE_NAME = "myTextFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        final SharedPreferences sharedPreferences = getSharedPreferences("ch.hslu.persistenz", MODE_PRIVATE);

        // Get counter and increment
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final int newResumeCount = sharedPreferences.getInt("resumeCounter", 0) + 1;
        editor.putInt("resumeCounter", newResumeCount);
        editor.apply();

        // Update label
        final TextView counterView = (TextView) findViewById(R.id.counterTextView);
        int cnt = sharedPreferences.getInt("resumeCounter", 0);
        counterView.setText("MainActivity.onResume() wurde seit der Installation "+ cnt +" mal aufgerufen.");

        this.printTeaPreferences();
    }

    public void editTeaPreferences(View v) {
        Intent intent = new Intent(this, AppPreferenceActivity.class);
        startActivity(intent);
    }

    public void resetTeaPreferences(View v) {
        SharedPreferences teaPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = teaPrefs.edit();
        editor.putString("teaPreferred", "Pilsner Urquell");
        editor.putString("teaSweetener", "super_duper_natural");
        editor.putBoolean("teaWithSugar", true);
        editor.apply();

        this.printTeaPreferences();
    }

    public void printTeaPreferences() {
        SharedPreferences teaPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean hasSweetener = teaPrefs.getBoolean("teaWithSugar", true);
        String sweetenerKey = teaPrefs.getString("teaSweetener","super_natural");
        String teaPreferred = teaPrefs.getString("teaPreferred", "Grüntee");
        final TextView prefView = (TextView) findViewById(R.id.prefTextView);

        String labelText = "Ich trinke am liebsten "+ teaPreferred ;
        if (hasSweetener) {
            labelText += ", mit " + getValueFromKey(sweetenerKey) + " gesüsst";
        } else {
            labelText += " (ungesüsst)";
        }
        labelText += ".";

        prefView.setText(labelText);

    }

    public String getValueFromKey(String key) {
        String[] keys = getResources().getStringArray(R.array.teaSweetenerValues);
        String[] values = getResources().getStringArray(R.array.teaSweetener);

        for (int i = 0; i < keys.length; i++) {
            if (key.equals(keys[i])) {
                return values[i];
            }
        }
        return "";
    }

    public void saveFile(View v) {
        CheckBox checkExt = (CheckBox) findViewById(R.id.checkExternal);
        if(checkExt.isChecked() && Environment.getExternalStorageState().equals("mounted")) {
            saveExtFileWithPermission();
        } else {
            writeInternal();
        }
    }

    public void loadFile(View v) {
        CheckBox checkExt = (CheckBox) findViewById(R.id.checkExternal);
        if(checkExt.isChecked() && Environment.getExternalStorageState().equals("mounted")) {
            loadExternal();
        } else {
            loadInternal();
        }
    }

    public void writeInternal() {
        EditText editText = (EditText) findViewById(R.id.someText);
        String text = editText.getText().toString();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(text);
            outputStreamWriter.flush();
            outputStreamWriter.close();
            System.out.println("In lokalen Speicher geschrieben: " + text);
            Toast.makeText(this, "In lokalen Speicher geschrieben.", Toast.LENGTH_SHORT).show();
            TextView statusText = (TextView) findViewById(R.id.status);
            statusText.setText("In lokalen Speicher geschrieben: "+text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadInternal() {
        EditText editText = (EditText) findViewById(R.id.someText);
        ArrayList<String> linesArray = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            if (fileInputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    linesArray.add(line);
                }
                fileInputStream.close();
            }
            if (linesArray.size()>0) {
                editText.setText(linesArray.get(0));
                Toast.makeText(this, "Aus lokalen Speicher gelesen", Toast.LENGTH_SHORT).show();
                TextView statusText = (TextView) findViewById(R.id.status);
                statusText.setText("Aus lokalem Speicher gelesen");
            } else {
                System.out.println("Nichts zum lesen.");
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void writeExternal() {
        EditText editText = (EditText) findViewById(R.id.someText);
        String text = editText.getText().toString();
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/ordner");
            directory.mkdir();
            File file = new File(directory, FILE_NAME);
            FileOutputStream outputStream = new FileOutputStream(file);
            String data = text;
            outputStream.write(data.getBytes());
            outputStream.close();
            Toast.makeText(this, "Auf SD-Karte geschrieben", Toast.LENGTH_SHORT).show();
            TextView statusText = (TextView) findViewById(R.id.status);
            statusText.setText("Auf SD-Karte geschrieben: "+text);
        } catch (Exception e) {
            // NOP
        }
    }

    public void loadExternal() {
        EditText editText = (EditText) findViewById(R.id.someText);
        int grant = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (grant == PackageManager.PERMISSION_GRANTED) {
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/ordner");
                directory.mkdir();
                File file = new File(directory, FILE_NAME);
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                char[] inputBuffer = new char[100];
                String string = "";
                int charRead;

                while((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    string += readString;
                }
                inputStreamReader.close();
                editText.setText(string);
                System.out.println("Von SD-Karte gelesen: "+string);
                Toast.makeText(this, "Von SD-Karte gelesen", Toast.LENGTH_SHORT).show();
                TextView statusText = (TextView) findViewById(R.id.status);
                statusText.setText("Von SD-Karte gelesen");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 24);
        }
    }


    private void saveExtFileWithPermission() {
        int grant = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 25);
        } else {
            writeExternal();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 24:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission " + permissions[0] + " denied! ", Toast.LENGTH_SHORT).show();
                } else {
                    loadExternal();
                }
                break;
            case 25:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission " + permissions[0] + " denied! ", Toast.LENGTH_SHORT).show();
                } else {
                    writeExternal();
                }
                break;
            }
        }

    }
