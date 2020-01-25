package de.benedikt_werner.ktanepasswordcracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private EditText[] txtChars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result = (TextView) findViewById(R.id.textView);
        txtChars = new EditText[5];
        txtChars[0] = (EditText) findViewById(R.id.editText);
        txtChars[1] = (EditText) findViewById(R.id.editText2);
        txtChars[2] = (EditText) findViewById(R.id.editText3);
        txtChars[3] = (EditText) findViewById(R.id.editText4);
        txtChars[4] = (EditText) findViewById(R.id.editText5);

        for (EditText txtChar : txtChars) {
            txtChar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    updatePossibilities();
                }
            });
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private String[] getWords() {
        return new String[] {
                "about", "after", "again", "below", "could", "every", "first", "found", "great",
                "house", "large", "learn", "never", "other", "place", "plant", "point", "right",
                "small", "sound", "spell", "still", "study", "their", "there", "these", "thing",
                "think", "three", "water", "where", "which", "world", "would", "write"
        };
    }

    private void updatePossibilities() {
        StringBuilder patternString = new StringBuilder();
        for (EditText txtChar : txtChars) {
            Editable chars = txtChar.getText();
            if (chars.length() == 0)
                patternString.append(".");
            else {
                patternString.append("[");
                patternString.append(chars);
                patternString.append("]");
            }
        }
        Pattern pattern = Pattern.compile(patternString.toString());
        LinkedList<String> possibleWords = new LinkedList<>();
        for (String word : getWords())
            if (pattern.matcher(word).matches())
                possibleWords.add(word);

        if (possibleWords.size() > 3)
            result.setText("Too many possibilities! (" + possibleWords.size() + ")");
        else if (possibleWords.isEmpty())
            result.setText(R.string.result_no_possible_words);
        else {
            String resultText = "";
            while (!possibleWords.isEmpty())
                resultText += possibleWords.removeFirst() + " ";
            result.setText(resultText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            for (EditText txtChar : txtChars)
                txtChar.setText("");
            txtChars[0].requestFocus();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
