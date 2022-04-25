package vcc.viv.ads.demo.bin.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class RequestResultViewModel extends ViewModel {
    /*
     * Area : Variable
     */
    private MutableLiveData<List<RequestResultAdapter.AdsInfo>> adsInfoList;

    /*
     * Area : Override
     */

    /*
     * Area : Function
     */
    public RequestResultViewModel() {
        adsInfoList = new MutableLiveData<>();
    }

    public void setViewList(List<RequestResultAdapter.AdsInfo> infoList) {
        this.adsInfoList.setValue(infoList);
    }

    public LiveData<List<RequestResultAdapter.AdsInfo>> getViewList() {
        return adsInfoList;
    }

    /*
     * Area : Inner Class
     */
}
