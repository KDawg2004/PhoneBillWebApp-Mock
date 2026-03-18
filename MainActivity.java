package edu.pdx.cs.joy.kbarta;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The main screen of the Phone Bill app. 
 * From here, you can choose to add a new call, view calls, or search for calls.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Sets up the main screen when the app starts.
     * It connects the buttons on the screen to the different parts of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addCallButton = findViewById(R.id.addCallButton);
        Button prettyPrintButton = findViewById(R.id.prettyPrintButton);
        Button searchButton = findViewById(R.id.searchCallButton);

        // When the "Add Call" button is clicked, open the Add Call screen
        addCallButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCallActivity.class);
            startActivity(intent);
        });

        // When the "Pretty Print" button is clicked, open the screen to view calls
        prettyPrintButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PrettyPrintActivity.class);
            startActivity(intent);
        });

        // When the "Search" button is clicked, open the Search screen
        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchCallsActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Creates the menu in the top corner of the screen.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles what happens when a menu item is selected.
     * For example, it shows the README information when that option is picked.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_readme) {
            new AlertDialog.Builder(this)
                    .setTitle("README")
                    .setMessage("Phone Bill App\n\n" +
                            "This app allows you to manage phone bills.\n\n" +
                            "Add Call: Enter caller, callee, start and end times " +
                            "in M/d/yyyy h:mm a format (3/11/2026 6:30 PM).\n\n" +
                            "Search Calls: Enter a customer name to view all calls. " +
                            "(Optionally) enter a date range to filter results.\n\n" +
                            "Data is saved automatically to internal storage.")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
