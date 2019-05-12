package app.company.androidtask.Network;


import java.util.List;

import app.company.androidtask.Models.CountriesModel;
import retrofit2.http.GET;
import rx.Observable;

public interface RetrofitInterface {

    @GET("rest/v1/all")
    Observable<List<CountriesModel>> getCountries();


}