package com.example.comconn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HttpDemosActivity extends AppCompatActivity {

    private final String IMAGE_URL = "http://wherever.ch/hslu/homer.jpg";
    private final String TEXT_DOCUMENT_URL = "http://wherever.ch/hslu/loremIpsum.txt";
    private final String JSON__DOCUMENT_URL = "http://www.nactem.ac.uk/software/acromine/dictionary.py?sf=HTTP";
    private final String XML__DOCUMENT_URL = "http://services.aonaware.com/DictService/DictService.asmx/Define?word=android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_demos);
    }

    // TODO
}
