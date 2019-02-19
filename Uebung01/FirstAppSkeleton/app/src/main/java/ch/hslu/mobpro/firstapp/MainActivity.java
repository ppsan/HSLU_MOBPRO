package ch.hslu.mobpro.firstapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * The main activity, displays some buttons.
 *
 * @author Ruedi Arnold
 */


public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23; // Arbitrary number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void startLogActivity(View v) {
        // start LifecylceLogActivity
        Intent intent = new Intent(this, LifecycleLogActivity.class);
        startActivity(intent);
    }

    public void startBrowser(View v) {
        // start Browser with http://www.hslu.ch
        String url = "http://www.hslu.ch";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void startQuestionActivity(View v) {
        // start QuestionActivity with question and wait for result.
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("question", "Do fish get thirsty?");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Use return data and print to log
        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {
            TextView textView = (TextView) findViewById(R.id.main_textView_result);
            String answer = getResources().getString(R.string.main_text_gotAnswer) + "'" + data.getExtras().getString("answer") + "'";
            textView.setText(answer);
        }
    }
}
