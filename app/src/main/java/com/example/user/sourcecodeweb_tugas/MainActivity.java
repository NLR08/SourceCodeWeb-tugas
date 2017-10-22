package com.example.user.sourcecodeweb_tugas;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.user.sourcecodeweb_tugas.R.id.spinner;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    TextView tview;
    Spinner nspinner;
    Button nbtnGet;
    EditText netInput;
    public String url = null;
    ProgressBar npbLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tview = (TextView) findViewById(R.id.textView);
        nspinner = (Spinner) findViewById(spinner);
        nbtnGet = (Button) findViewById(R.id.btnGet);
        netInput = (EditText) findViewById(R.id.etInput);
        npbLoad = (ProgressBar) findViewById(R.id.pbLoad);

        npbLoad.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapater = ArrayAdapter.createFromResource(this, R.array.Spinner, android.R.layout.simple_spinner_item);
        adapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nspinner.setAdapter(adapater);

        nbtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = nspinner.getSelectedItem() + netInput.getText().toString();
                boolean valid = Patterns.WEB_URL.matcher(url).matches();

                if (valid){
                    getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                    npbLoad.setVisibility(View.VISIBLE);
                    tview.setVisibility(View.GONE);
                } else{
                    Loader loader = getSupportLoaderManager().getLoader(0);
                    if (loader != null){
                        loader.cancelLoad();
                    }
                    npbLoad.setVisibility(View.GONE);
                    tview.setVisibility(View.VISIBLE);
                    tview.setText("This Website is no Valid");
                }
            }
        });
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderWeb(this, url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        npbLoad.setVisibility(View.GONE);
        tview.setVisibility(View.VISIBLE);
        tview.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
