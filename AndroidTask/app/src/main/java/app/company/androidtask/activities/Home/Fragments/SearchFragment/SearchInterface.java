package app.company.androidtask.activities.Home.Fragments.SearchFragment;

import java.util.List;

import app.company.androidtask.Models.CountriesModel;

public interface SearchInterface {
    void setAllCountries(List<CountriesModel> countriesModelsList);
    void filter(CharSequence query);
}
