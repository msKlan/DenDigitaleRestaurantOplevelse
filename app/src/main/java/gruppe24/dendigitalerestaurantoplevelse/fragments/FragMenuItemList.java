package gruppe24.dendigitalerestaurantoplevelse.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import gruppe24.dendigitalerestaurantoplevelse.ListAdapter;
import gruppe24.dendigitalerestaurantoplevelse.MainActivity;
import gruppe24.dendigitalerestaurantoplevelse.R;
import gruppe24.dendigitalerestaurantoplevelse.backend.Backend;
import gruppe24.dendigitalerestaurantoplevelse.backend.Dish;
import gruppe24.dendigitalerestaurantoplevelse.backend.interfaces.Menu;

/**
 * Created by Tobias Højsgaard on 05-01-2018.
 */

public class FragMenuItemList extends Fragment {

    private ListAdapter listAdapter;
    public Runnable newImagesLoaded = new Runnable() {
        @Override
        public void run() {
            listAdapter.notifyDataSetChanged();
        }
    };
    Bundle data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.listview, container, false);

        data = getArguments();
        List<String> dishes;

        try{
            dishes = Backend.getInstance().search(Backend.getInstance().getMenu(), data.getString("category")).getDishesAsStrings();
        } catch (NullPointerException e){
            dishes = Backend.getInstance().getMenu().getDishesAsStrings();
        }

        listAdapter = new gruppe24.dendigitalerestaurantoplevelse.ListAdapter(getActivity(), dishes);
        ListView listViewID = (ListView) layout.findViewById(R.id.listViewID);
        listViewID.setAdapter(listAdapter);

        listViewID.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String dish = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(getActivity(), dish, Toast.LENGTH_LONG).show();
                        MainActivity.startFoodInfoActivity(dish, getFragmentManager());

                    }
                }
        );
        Dish.newPicturesLoaded.add(newImagesLoaded);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Dish.newPicturesLoaded.remove(newImagesLoaded);
    }


}
