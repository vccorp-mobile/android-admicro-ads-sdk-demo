package vcc.viv.ads.demo.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vcc.viv.ads.demo.R;

public class BaseActivity extends AppCompatActivity {
    /*
     * Area : Variable
     */
    private final String tag = BaseActivity.class.getSimpleName();
    protected Toolkit toolkit;

    /*
     * Area : Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolkit = new Toolkit();
    }

    /*
     * Area : Function
     */

    /*
     * Area : Inner Class
     */
}