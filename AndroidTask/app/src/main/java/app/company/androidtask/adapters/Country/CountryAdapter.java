package app.company.androidtask.adapters.Country;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.company.androidtask.Models.CountriesModel;
import app.company.androidtask.R;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {


    private Context context;
    private List<CountriesModel> itemList;
    private List<CountriesModel> CountriesListFiltered;


    public CountryAdapter(Context context, List<CountriesModel> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.CountriesListFiltered = itemList;


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    CountriesListFiltered = itemList;
                } else {
                    List<CountriesModel> filteredList = new ArrayList<>();
                    for (CountriesModel row : itemList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getCapital().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    CountriesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = CountriesListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults
                    filterResults) {
                CountriesListFiltered = (ArrayList<CountriesModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return CountriesListFiltered == null ? 0 : CountriesListFiltered.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {

        holder.countryNameTextView.setText(CountriesListFiltered.get(listPosition).getName());
        holder.capitalTextView.setText(CountriesListFiltered.get(listPosition).getCapital());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView countryNameTextView;
        TextView capitalTextView;

        ViewHolder(View itemView) {
            super(itemView);
            countryNameTextView = itemView.findViewById(R.id.country_name_text_view);
            capitalTextView = itemView.findViewById(R.id.capital_text_view);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
