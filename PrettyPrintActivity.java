package edu.pdx.cs.joy.kbarta;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

/**
 * This screen allows you to view a customer's phone bill in a readable format.
 * You enter a customer's name, and it shows all their saved calls.
 */
public class PrettyPrintActivity extends AppCompatActivity {

    /**
     * Sets up the screen layout when the activity starts.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_print);
    }

    /**
     * Finds the phone bill for a specific customer and displays it on the screen.
     * It reads the customer's file and formats the list of calls to be easy to read.
     */
    public void prettyPrint(View view) {
        EditText customerInput = findViewById(R.id.prettyCustomer);
        TextView results = findViewById(R.id.prettyResults);

        String customer = customerInput.getText().toString().trim();

        // Make sure a name was actually entered
        if (customer.isEmpty()) {
            results.setText("Please enter a customer name.");
            return;
        }

        // Look for the file that matches the customer's name
        File file = new File(getFilesDir(), customer + ".txt");

        // If the file doesn't exist, tell the user no records were found
        if (!file.exists()) {
            results.setText("No records found for: " + customer);
            return;
        }

        try {
            // Load the bill from the file
            TextParser parser = new TextParser(new FileReader(file));
            PhoneBill bill = parser.parse();

            // Format the bill into a nice, readable string
            StringWriter sw = new StringWriter();
            PrettyPrinter printer = new PrettyPrinter(sw);
            printer.dump(bill);

            // Show the final list of calls on the screen
            results.setText(sw.toString());

        } catch (Exception e) {
            // Show an error if something goes wrong while reading the file
            results.setText("Error reading file: " + e.getMessage());
        }
    }
}
