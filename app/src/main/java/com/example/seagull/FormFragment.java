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

import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {
    //LOG TAG
    private static final String TAG = "FORM_FRAG";
    //UI ELEMENTS
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
        //INFLATER
        View rootView = inflater.inflate(R.layout.fragment_form, container, false);
        //UI ELEMENTS
        typeRadioGroup = rootView.findViewById(R.id.typeRadioGroup);
        titleEditText = rootView.findViewById(R.id.titleEditText);
        amountEditText = rootView.findViewById(R.id.amountEditText);
        datePicker = rootView.findViewById(R.id.datePicker);

        //SUBMIT BUTTON
        Button submitBtn = rootView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onSubmitClicked(view);}
        });

        return rootView;
    }

    //SUBMIT
    public void onSubmitClicked(View view) {
        Log.i(TAG, "onSubmitClicked - FormFragment");

        //CREATE LINE ITEM
        String title = titleEditText.getText().toString();

        double amount = Double.parseDouble(amountEditText.getText().toString());

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        LineItem lineItem = new LineItem(amount, title, date);
        //TYPE OF LINE ITEM
        boolean isExpense = typeRadioGroup.getCheckedRadioButtonId() == R.id.expenseRadioButton;

        if (formSubmitListener != null) {
            Log.i(TAG, "formSubmitListener - FormFragment");
            formSubmitListener.onFormSubmit(lineItem, isExpense);
        }

        //SWITCH TO TABLE
        ((MainActivity) getActivity()).switchToTab(0);
    }


}

