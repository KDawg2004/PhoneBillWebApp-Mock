package edu.pdx.cs.joy.kbarta;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This screen lets you search for phone calls by a customer's name.
 * You can also filter the results by a starting and ending date/time.
 */
public class SearchCallsActivity extends AppCompatActivity {

    // The date format we expect, like 3/11/2026 6:30 PM
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    /**
     * Sets up the screen layout when the activity starts.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_calls);
    }

    /**
     * Finds and displays phone calls that match the search criteria.
     * It checks the customer's name and looks for calls within the optional date range.
     */
    public void searchCalls(View view) {
        EditText customerInput = findViewById(R.id.searchCustomer);
        EditText startInput = findViewById(R.id.startTime);
        EditText endInput = findViewById(R.id.endTime);
        TextView results = findViewById(R.id.resultsText);

        String customer = customerInput.getText().toString().trim();

        // Make sure a customer name was entered
        if (customer.isEmpty()) {
            results.setText("Please enter a customer name.");
            return;
        }

        // Get the dates from the screen
        String startStr = startInput.getText().toString().trim();
        String endStr = endInput.getText().toString().trim();

        LocalDateTime start = null;
        LocalDateTime end = null;

        // Try to read the start date if provided
        if (!startStr.isEmpty()) {
            try {
                start = LocalDateTime.parse(startStr, FORMATTER);
            } catch (DateTimeParseException e) {
                results.setText("Invalid start date format.\nUse: M/d/yyyy h:mm a\nExample: 3/11/2026 6:30 PM");
                return;
            }
        }

        // Try to read the end date if provided
        if (!endStr.isEmpty()) {
            try {
                end = LocalDateTime.parse(endStr, FORMATTER);
            } catch (DateTimeParseException e) {
                results.setText("Invalid end date format.\nUse: M/d/yyyy h:mm a\nExample: 3/11/2026 6:30 PM");
                return;
            }
        }

        // Check if the start date comes before the end date
        if (start != null && end != null && start.isAfter(end)) {
            results.setText("Start time must be before end time.");
            return;
        }

        // Look for the customer's phone bill file
        File file = new File(getFilesDir(), customer + ".txt");

        if (!file.exists()) {
            results.setText("No records found for: " + customer);
            return;
        }

        try {
            // Load the bill
            TextParser parser = new TextParser(new FileReader(file));
            PhoneBill bill = parser.parse();

            StringBuilder output = new StringBuilder();
            int count = 0;

            // Go through each call and see if it fits the search
            for (PhoneCall call : bill.getPhoneCalls()) {
                LocalDateTime callStart = call.getBeginTime();

                boolean inRange = true;
                // Check if the call is too early or too late
                if (start != null && callStart.isBefore(start)) inRange = false;
                if (end != null && callStart.isAfter(end)) inRange = false;

                // If it matches, add it to the list to show the user
                if (inRange) {
                    output.append(call.toString()).append("\n\n");
                    count++;
                }
            }

            // Show results or tell the user if nothing was found
            if (count == 0) {
                results.setText("No calls found in that date range.");
            } else {
                results.setText(output.toString());
            }

        } catch (Exception e) {
            // Show an error if something goes wrong reading the file
            results.setText("Error reading file: " + e.getMessage());
        }
    }
}
