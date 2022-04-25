package vcc.viv.ads.demo.base;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    /*
     * Area : Variable
     */
    protected Toolkit toolkit;
    protected Resources resources;


    /*
     * Area : Constructor
     */
    public BaseViewModel(Context context) {
        super();
        this.toolkit = new Toolkit();
        this.resources = context.getResources();
    }

    /*
     * Area : Function
     */
}
