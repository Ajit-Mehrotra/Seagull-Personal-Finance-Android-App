package com.example.seagull;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seagull.FormSubmitListener;
import com.example.seagull.MainActivity;
import com.example.seagull.R;
import com.example.seagull.LineItem;

import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {
    //FORM ELEMENTS
    private RadioGroup typeRadioGroup;
    private EditText titleEditText;
    private EditText amountEditText;
    private DatePicker datePicker;
    //FORM LISTENER
    private FormSubmitListener formSubmitListener;
    public void setFormSubmitListener(FormSubmitListener formSubmitListener) {
        this.formSubmitListener = formSubmitListener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_form, container, false);

        //INITIALIZE ELEMENTS
        typeRadioGroup = rootView.findViewById(R.id.typeRadioGroup);
        titleEditText = rootView.findViewById(R.id.titleEditText);
        amountEditText = rootView.findViewById(R.id.amountEditText);
        datePicker = rootView.findViewById(R.id.datePicker);

        Button submitBtn = rootView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitClicked(view);
            }
        });


        return rootView;
    }
    public void onSubmitClicked(View view) {
        Log.e("bruh", "onSubmitClicked - FormFragment");

        String title = titleEditText.getText().toString();
        double amount = Double.parseDouble(amountEditText.getText().toString());
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        LineItem lineItem = new LineItem(amount, title, date);
        boolean isExpense = typeRadioGroup.getCheckedRadioButtonId() == R.id.expenseRadioButton;

        if (formSubmitListener != null) {
            Log.e("bruh", "formSubmitListener - FormFragment");
            formSubmitListener.onFormSubmit(lineItem, isExpense);
        }
        //RESET THE FORM
        typeRadioGroup.clearCheck();
        titleEditText.setText("");
        amountEditText.setText("");

        //DATE PICKER RESET
        calendar = Calendar.getInstance();
        int rYear = calendar.get(Calendar.YEAR);
        int rMonth = calendar.get(Calendar.MONTH);
        int rDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.updateDate(rYear, rMonth, rDay);
        // Switch to the first tab (TableFragment)
        ((MainActivity) getActivity()).switchToTab(0);
    }


}

