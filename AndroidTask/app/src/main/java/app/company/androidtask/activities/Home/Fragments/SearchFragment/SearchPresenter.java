package app.company.androidtask.activities.Home.Fragments.SearchFragment;


import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import app.company.androidtask.Models.CountriesModel;
import app.company.androidtask.Network.NetworkUtil;
import app.company.androidtask.Utills.Constant;
import app.company.androidtask.Utills.Validation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static app.company.androidtask.Utills.Constant.buildDialog;

public class SearchPresenter {

    private CountriesModel countriesModel;
    private List<CountriesModel> countriesModelList = new ArrayList<>();
    private SearchInterface searchInterface;
    private Context mContext;

    private CompositeSubscription mSubscriptions;

    public SearchPresenter(SearchInterface searchInterface, Context mContext) {
        this.countriesModel = new CountriesModel();
        this.searchInterface = searchInterface;
        this.mContext = mContext;
        mSubscriptions = new CompositeSubscription();
    }

    public void searchInRecycler(CharSequence query) {
        searchInterface.filter(query);
    }


    public void getAllCountries() {
        if (Validation.isConnected(mContext)) {

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .getCountries()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            buildDialog((Activity) mContext).show().setCanceledOnTouchOutside(false);
        }
    }

    private void handleError(Throwable throwable) {
        Constant.errorBuildDialog((Activity) mContext, throwable.getMessage());
    }

    private void handleResponse(List<CountriesModel> countriesModelsList) {
        searchInterface.setAllCountries(countriesModelsList);
    }


}

