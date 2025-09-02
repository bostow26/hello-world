package com.example.stickystatecongressionalappchallenge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    TextView test;
    Button mybutton;
    Button restartbutton;
    EditText edit;
    ImageView ImageView;
    ImageView ImageView2;
    TextView pointsnlives;

    int points = 0;
    int lives = 3;
    String savedFile = "points.txt";
    ArrayList<String> myValues = new ArrayList<>();
    int randomIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        points = getSavedData();

        //this sets different messages depending on whether you are a first time player or have entered the app before
        test = findViewById(R.id.textView);
        if(points == 0) {
            test.setText("Hello, welcome to guess the p-value");
        }else{
            test.setText("You ended with " + points + " points");
        }

        pointsnlives = findViewById(R.id.subTextView);
        pointsnlives.setText("This area will tell you how many points you have scored and lives you have left");

        restartbutton = findViewById(R.id.button2);
        restartbutton.setText("Restart Game");

        mybutton = findViewById(R.id.button);
        mybutton.setText("Submit Answer");

        edit = findViewById(R.id.editTextText);
        edit.setText("0.");

        ImageView = findViewById(R.id.imageView);
        ImageView2 = findViewById(R.id.imageView2);



        try {
            InputStream is = getAssets().open("values.txt");
            Scanner scanner = new Scanner(is);

            // Read the file line by line, adding each to the list
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                myValues.add(line);
            }
        } catch (Exception e) {
            ;
        }

        randomIndex = (int)(Math.random()*myValues.size());

        try {
            ImageView2.setImageDrawable(Drawable.createFromStream(getAssets().open("images/Logo.png"), null));
        } catch (IOException e) {
            ImageView2.setImageDrawable(null);
        }

        try {
            ImageView.setImageDrawable(Drawable.createFromStream(getAssets().open("images/NormalDistributions/" + myValues.get(randomIndex) + ".png"), null));
        } catch (IOException e) {
            ImageView.setImageDrawable(null);
        }
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        restartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
    }
    private void submit() {
        if(lives>1) {
            double inputValue = Double.parseDouble(edit.getText().toString());
            double targetValue = Double.parseDouble(myValues.get(randomIndex)); // assuming myValues contains strings

            //this block gives points depending on how close your guess is from the correct answer
            if (Math.abs(inputValue - targetValue) <= 0.05) {
                points += 5;
                test.setText("Within 0.05! Good job!");
                pointsnlives.setText("Correct Answer = " +myValues.get(randomIndex)+" Points = " + points + " Lives = " + lives);
            } else if (Math.abs(inputValue - targetValue) <= 0.10) {
                points += 2;
                test.setText("Within 0.1! Good job!");
                pointsnlives.setText("Correct Answer = " +myValues.get(randomIndex)+" Points = " + points + " Lives = " + lives);
            } else {
                lives -= 1;
                test.setText("Wrong :(");
                pointsnlives.setText("Correct Answer = " +myValues.get(randomIndex)+" Points = " + points + " Lives = " + lives);
            }

            edit.setText("0.");

        }
        else{
            test.setText("Game Over");
            pointsnlives.setText("You ended with: "+points+" points");
            saveData(0);
        }

        randomIndex = (int)(Math.random()*myValues.size());

        try {
            ImageView.setImageDrawable(Drawable.createFromStream(getAssets().open("images/NormalDistributions/" + myValues.get(randomIndex) + ".png"), null));
        } catch (IOException e) {
            ImageView.setImageDrawable(null);
        }


        saveData(points);

    }
    private void restart() {
        //restarts your lives and points
        points = 0;
        lives = 3;
        saveData(0);
        test.setText("You have restarted the game!");
        pointsnlives.setText("Correct Answer Was = " +myValues.get(randomIndex)+" Points = " + points + " Lives = " + lives);

        randomIndex = (int)(Math.random()*myValues.size());
        try {
            ImageView.setImageDrawable(Drawable.createFromStream(getAssets().open("images/NormalDistributions/" + myValues.get(randomIndex) + ".png"), null));
        } catch (IOException e) {
            ImageView.setImageDrawable(null);
        }
        }
    private void saveData(int data) {
        String fileContents = data + "";
        try (FileOutputStream fos = getApplicationContext().openFileOutput(savedFile, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getSavedData() {
        int savedData = 0;

        try {
            FileInputStream fis = getApplicationContext().openFileInput(savedFile);
            Scanner scanner = new Scanner(fis);

            // Read the file
            while ((scanner.hasNextLine())) {
                savedData = Integer.parseInt(scanner.nextLine()); // Get the number from the file
            }
        } catch (Exception e) {
            ; // Don't do anything. No one needs to know there was an error.
        }

        return savedData;
    }
    }