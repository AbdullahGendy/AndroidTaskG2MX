package app.company.androidtask.activities.Home.Fragments.FacebookFriendsFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.company.androidtask.R;
import app.company.androidtask.Utills.Constant;
import app.company.androidtask.adapters.FacebookFriends.FacebookFriend;
import app.company.androidtask.adapters.FacebookFriends.FacebookFriendsAdapter;

import static android.content.Context.MODE_PRIVATE;


public class FacebookFriendsFragment extends Fragment {
    int FACEBOOK_FRIENDS_COUNTER;
    View view;
    List<FacebookFriend> facebookFriendList;
    SharedPreferences mSharedPreferences;
    AccessToken accessToken;
    String facebookID = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_facebook_friends, container, false);
        mSharedPreferences = getActivity().getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(Constant.accessToken, "");
        facebookID = mSharedPreferences.getString(Constant.facebookID, "");
        accessToken = gson.fromJson(json, AccessToken.class);
        FACEBOOK_FRIENDS_COUNTER = 0;
        recyclerInit();
        return view;
    }

    List<FacebookFriend> getFacebookFriends() {
        facebookFriendList = new ArrayList<>();
        GraphRequest request =
                new GraphRequest(
                        accessToken,
                        "/" + facebookID + "/taggable_friends",
                        null,
                        HttpMethod.GET,
                        response -> {
                            JSONObject obj = response.getJSONObject();
                            JSONArray arr;
                            try {
                                arr = obj.getJSONArray("data");
                                for (int i = 0; i < arr.length(); i++) {
                                    JSONObject oneByOne = arr.getJSONObject(i);
                                    facebookFriendList.add(new FacebookFriend(oneByOne.opt("name").toString(), oneByOne.opt("picture").toString()  ));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name");
        request.setParameters(parameters);
        request.executeAsync();

        return facebookFriendList;
    }

    void recyclerInit() {
        FacebookFriendsAdapter facebookFriendsAdapter = new FacebookFriendsAdapter(getActivity(), getFacebookFriends());
        RecyclerView recyclerView = view.findViewById(R.id.facebook_friends_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(facebookFriendsAdapter);

    }


}
