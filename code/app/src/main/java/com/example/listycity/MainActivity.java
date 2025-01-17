package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //3 different lists
    //ListView = displays list of city names on android screen
    ListView cityList;
    //Connects data (dataList) to ListView and manage updates
    ArrayAdapter<String> cityAdapter;
    //store list of city names dynamically in backend
    ArrayList<String> dataList;

    //new variable to store user selected city (to delete later)
    String selectedCity = null;

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

        //initialize ListView and EditText
        cityList = findViewById(R.id.city_list);
        EditText cityInput = findViewById(R.id.text_box);

        String[] cities = {"Edmonton", "Paris", "London", "Ottawa", "Calgary", "Vancouver", "Toronto", "Tokyo", "Bombay", "Cairo"};

        //initialize the data list
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // Create the adapter and set it to the ListView
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        //Item click listener for ListView
        //selecting city
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedCity = dataList.get(position); //remember selected city to be deleted if user clicks
            }
        });

        //ADD button logic
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = cityInput.getText().toString(); //get the text from input field
                if (!cityName.isEmpty()) { //if text != empty
                    dataList.add(cityName); //then, add city to list
                    cityAdapter.notifyDataSetChanged(); //notify android adapter of data change, so refreshes the screen
                    cityInput.setText(""); //clear input field
                }
            }
        });

        //DELETE button logic
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedCity != null) { //if city is selected
                    dataList.remove(selectedCity); //remove city from list
                    cityAdapter.notifyDataSetChanged(); //notify adapter of data change, so android refreshes the ListView/screen
                    selectedCity = null; //reset selected city
                }
            }
        });
    }
}
