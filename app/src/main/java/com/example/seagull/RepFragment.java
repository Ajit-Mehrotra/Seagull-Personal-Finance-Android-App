    package com.example.seagull;

    import static com.example.seagull.R.layout.custom_spinner;

    import android.content.Context;
    import android.content.Intent;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.Spinner;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.example.seagull.R;
    import com.example.seagull.SQLHelper;
    import com.example.seagull.BankRep;
    import com.example.seagull.Rep;

    import java.util.ArrayList;
    import java.util.HashSet;

    public class RepFragment extends Fragment {

        Bank bank1 = new Bank(1,"Citi", "www.citi.com");
        Bank bank2 = new Bank(2,"Bank of America", "www.bankofamerica.com");
        Bank bank3 = new Bank(3,"US Bank", "www.usbank.com");
        Bank bank4 = new Bank(4,"PNC Bank", "www.pnc.com");
        Rep rep1 = new Rep(1001,"John Doe", 1, "jdoe@citi.com", 123456789);
        Rep rep2 = new Rep(1002,"Mary Doe", 1, "mdoe@citi.com", 123456789);
        Rep rep3 = new Rep(1003,"Harry Potter", 2, "hpotter@bofa.com", 123456789);
        Rep rep4 = new Rep(1004,"Saul Good", 2, "sgood@bofa.com", 123456789);
        Rep rep5 = new Rep(1005,"Terry Crews", 3, "tcrews@usb.com", 123456789);
        Rep rep6 = new Rep(1006,"Bob Ross", 3, "bross@usb.com", 123456789);
        Rep rep7 = new Rep(1007,"Henry Ford", 4, "hford@pnc.com", 123456789);
        Rep rep8 = new Rep(1008,"Jack Monroe", 4, "jmonroe@pnc.com", 123456789);

        SQLHelper helper = new SQLHelper(getContext());
        private SQLiteDatabase db;
        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            // Initialize the database here
            helper = new SQLHelper(context);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rep, container, false);


            helper.addBank(bank1);
            helper.addBank(bank2);
            helper.addBank(bank3);
            helper.addBank(bank4);

            helper.addRep(rep1);
            helper.addRep(rep2);
            helper.addRep(rep3);
            helper.addRep(rep4);
            helper.addRep(rep5);
            helper.addRep(rep6);
            helper.addRep(rep7);
            helper.addRep(rep8);

            HashSet<String> banks = helper.getBanks();
            ArrayList<String> bankList = new ArrayList<>(banks);

            Spinner spin = (Spinner) rootView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), custom_spinner, bankList); // or getActivity() if used in a fragment
            adapter.setDropDownViewResource(R.layout.custom_dropdown);
            spin.setAdapter(adapter);
            spin.setSelection(adapter.NO_SELECTION, true);


            //RETURN ALL BANK REPS
            ArrayList<BankRep> bankRepList = helper.getJoinData();

            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String bankName = (String) parent.getItemAtPosition(position);
                    for (BankRep bankRep : bankRepList) {
                        if (bankRep.getBankName().equals(bankName)) {
                            View bankRepView = inflater.inflate(R.layout.bankrep, parent, false);


                            // Find the TextView elements in the XML layout
                            TextView tvRepName = bankRepView.findViewById(R.id.tvRepName);
                            TextView tvRepEmail = bankRepView.findViewById(R.id.tvRepEmail);
                            TextView tvRepPhone = bankRepView.findViewById(R.id.tvRepPhone);

                            Button emailButton = bankRepView.findViewById(R.id.emailButton);
                            Button smsButton = bankRepView.findViewById(R.id.smsButton);

                            // Set the data to the TextView elements
                            tvRepName.setText("Representative Name: " + bankRep.getRepName());
                            tvRepEmail.setText("Representative Email: " + bankRep.getEmail());
                            tvRepPhone.setText("Representative Phone: " + bankRep.getPhone());

                            emailButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Create an intent to send an email
                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("mailto:")); // Only email apps should handle this
                                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{bankRep.getEmail()}); // Email recipient(s)
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry to Seagull Rep!"); // Email subject
                                    intent.putExtra(Intent.EXTRA_TEXT, "This is a submission for inquiry on the Seagull app."); // Email body
                                    startActivity(intent);

                                }
                            });

                            smsButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Create an intent to send an SMS
                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("smsto:" +(bankRep.getPhone()))); // SMS recipient(s)
                                    intent.putExtra("sms_body", "This is an inquiry text!"); // SMS body
                                        startActivity(intent);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        return rootView;
        }

    }


