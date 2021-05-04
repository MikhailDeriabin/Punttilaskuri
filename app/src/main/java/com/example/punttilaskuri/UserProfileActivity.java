package com.example.punttilaskuri;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles user information input and storage into userinfo.txt
 * @author Sami Miettinen
 * @version 04.05.2021
 */
public class UserProfileActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText nameEdit, ageEdit, weightEdit, heightEdit;
    private UserInfo userInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        //Initialize UI-components
        updateUi();

        //OnClickListener for saveButton
        saveButton.setOnClickListener( v -> {
            //Append user info into a single String-variable with newlines
            String userInput = nameEdit.getText().toString() + "\n";
            userInput += ageEdit.getText().toString() + "\n";
            userInput += weightEdit.getText().toString() + "\n";
            userInput += heightEdit.getText().toString() + "\n";

            saveToFile("", this);
            saveToFile(userInput, this);
            updateUi();

        });
    }
    //Initializes UI-components and updates text in EditText-fields
    public void updateUi(){
        ArrayList<String> fileData = new ArrayList<>(readFromFile(this));
        userInfo = new UserInfo(fileData);
        nameEdit= (EditText) findViewById(R.id.nameInput);
        nameEdit.setText(fileData.get(0));
        ageEdit= (EditText) findViewById(R.id.ageInput);
        ageEdit.setText(fileData.get(1));
        weightEdit= (EditText) findViewById(R.id.weightInput);
        weightEdit.setText(fileData.get(2));
        heightEdit= (EditText) findViewById(R.id.heightInput);
        heightEdit.setText(fileData.get(3));
        saveButton = (Button) findViewById(R.id.saveButton);

    }
    //Writes userinfo to userinfo.txt
    private void saveToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("userinfo.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    //Reads userinfo from userinfo.txt into an ArrayList and returns it
    private List<String> readFromFile(Context context) {

        ArrayList<String> fileData = new ArrayList<>();

        try {
            InputStream inputStream = context.openFileInput("userinfo.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String fileContent;

                while ( (fileContent = bufferedReader.readLine()) != null ) {
                    fileData.add(fileContent);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            fileData.add("Name");
            fileData.add("Age");
            fileData.add("Weight");
            fileData.add("Height");
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return fileData;
    }
}
