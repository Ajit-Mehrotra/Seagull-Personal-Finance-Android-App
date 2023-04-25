package com.example.seagull;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Form extends AppCompatActivity {

    private RadioGroup typeRadioGroup;
    private EditText titleEditText;
    private EditText amountEditText;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        typeRadioGroup = findViewById(R.id.typeRadioGroup);
        titleEditText = findViewById(R.id.titleEditText);
        amountEditText = findViewById(R.id.amountEditText);
        datePicker = findViewById(R.id.datePicker);
    }

    public void onSubmitClicked(View view) {
        // Get the selected type (expense/earning)
        boolean isExpense = typeRadioGroup.getCheckedRadioButtonId() == R.id.expenseRadioButton;

        // Get the title
        String title = titleEditText.getText().toString();

        // Get the amount
        double amount = 0;
        try {
            amount = Double.parseDouble(amountEditText.getText().toString());
        } catch (NumberFormatException e) {
            // Handle invalid input
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the date
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // add 1 to account for 0-based index
        int year = datePicker.getYear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            // Handle invalid date
            Toast.makeText(this, "Invalid date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new LineItem object with the input data
        LineItem lineItem = new LineItem(amount, title, date);

//        // Add the LineItem to the appropriate ArrayList based on the selected type (expense/earning)
//        if (isExpense) {
//            MainActivity.expenseList.add(lineItem);
//        } else {
//            MainActivity.earningsList.add(lineItem);
//        }

        // Close the activity and return to the main activity
        finish();
    }
}