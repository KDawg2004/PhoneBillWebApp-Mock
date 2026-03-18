package edu.pdx.cs.joy.kbarta;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Activity for adding a new phone call to a customer's phone bill.
 */
public class AddCallActivity extends AppCompatActivity {

    /**
     * Initializes the activity and sets the content view.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_call);
    }

    /**
     * Displays an error message in an AlertDialog.
     * @param message The error message to display.
     */
    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * Validates input fields, creates a {@link PhoneCall}, and saves it to a text file
     * named after the customer. The calls are sorted before being saved.
     * @param view The view that triggered this method.
     */
    public void saveCall(View view) {

        EditText customerField = findViewById(R.id.customerName);
        EditText callerField = findViewById(R.id.callerNumber);
        EditText calleeField = findViewById(R.id.calleeNumber);
        EditText startField = findViewById(R.id.startTime);
        EditText endField = findViewById(R.id.endTime);

        String customer = customerField.getText().toString().trim();
        String caller = callerField.getText().toString().trim();
        String callee = calleeField.getText().toString().trim();
        String start = startField.getText().toString().trim();
        String end = endField.getText().toString().trim();

        // Checking for empty feilds
        if (customer.isEmpty()) { showError("Customer name is required."); return; }
        if (caller.isEmpty()) { showError("Caller number is required."); return; }
        if (callee.isEmpty()) { showError("Callee number is required."); return; }
        if (start.isEmpty()) { showError("Start time is required."); return; }
        if (end.isEmpty()) { showError("End time is required."); return; }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

        LocalDateTime startTime;
        LocalDateTime endTime;

        try {
            startTime = LocalDateTime.parse(start, formatter);
        } catch (DateTimeParseException e) {
            showError("Invalid start time format.\nUse: M/d/yyyy h:mm a\nExample: 3/11/2026 6:30 PM");
            return;
        }

        try {
            endTime = LocalDateTime.parse(end, formatter);
        } catch (DateTimeParseException e) {
            showError("Invalid end time format.\nUse: M/d/yyyy h:mm a\nExample: 3/11/2026 6:30 PM");
            return;
        }

        PhoneCall call;
        try {
            call = new PhoneCall(caller, callee, startTime, endTime);
        } catch (IllegalArgumentException e) {
            showError("End time cannot be before start time.");
            return;
        }

        File file = new File(getFilesDir(), customer + ".txt");
        PhoneBill bill;

        if (file.exists()) {
            try {
                TextParser parser = new TextParser(new FileReader(file));
                bill = parser.parse();
            } catch (Exception e) {
                bill = new PhoneBill(customer);
            }
        } else {
            bill = new PhoneBill(customer);
        }

        bill.addPhoneCall(call);

        // Sort calls before saving
        ArrayList<PhoneCall> calls = new ArrayList<>(bill.getPhoneCalls());
        Collections.sort(calls);
        PhoneBill sortedBill = new PhoneBill(customer);
        for (PhoneCall c : calls) {
            sortedBill.addPhoneCall(c);
        }

        try {
            Writer writer = new FileWriter(file);
            TextDumper dumper = new TextDumper(writer);
            dumper.dump(sortedBill);
            writer.close();

            Toast.makeText(this, "Call saved!", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            showError("Failed to save call: " + e.getMessage());
        }
    }
}
