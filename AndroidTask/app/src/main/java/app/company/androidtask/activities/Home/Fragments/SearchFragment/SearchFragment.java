package app.company.androidtask.activities.Home.Fragments.SearchFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import app.company.androidtask.Models.CountriesModel;
import app.company.androidtask.R;
import app.company.androidtask.adapters.Country.CountryAdapter;


public class SearchFragment extends Fragment implements SearchInterface {
    View view;
    SearchPresenter presenter;
    CountryAdapter countryAdapter;
    EditText searchBarEdiText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchBarEdiText = view.findViewById(R.id.search_bar_edit_text);
        presenter = new SearchPresenter(this, getActivity());
        presenter.getAllCountries();

        searchBarEdiText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.searchInRecycler(s);

            }
        });
        return view;
    }

    @Override
    public void setAllCountries(List<CountriesModel> countriesModelsList) {
        countryAdapter = new CountryAdapter(getActivity(), countriesModelsList);
        RecyclerView recyclerView = view.findViewById(R.id.countries_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(countryAdapter);
    }

    @Override
    public void filter(CharSequence query) {
        countryAdapter.getFilter().filter(query);
    }
}
