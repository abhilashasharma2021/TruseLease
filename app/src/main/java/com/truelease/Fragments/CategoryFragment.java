package com.truelease.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.truelease.Activities.BottomNavigationActivity;
import com.truelease.Adapters.CategoryAdapter;
import com.truelease.Others.BottomSheetOffers;
import com.truelease.R;
import com.truelease.viewModel.MyCategoriesViewModel;

import java.util.List;


public class CategoryFragment extends Fragment {

    public static RecyclerView categoryRecycler;
    public static FloatingActionButton fab;
    CategoryAdapter adapter;
    ImageView menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_category, container, false);

        categoryRecycler = v.findViewById(R.id.categoryRecycler);
        menu = v.findViewById(R.id.menu);
        fab = v.findViewById(R.id.fab);
        categoryRecycler.setHasFixedSize(true);
        categoryRecycler.setItemViewCacheSize(20);

        menu.setOnClickListener(view -> BottomNavigationActivity.drawer.openDrawer(GravityCompat.START));


        MyCategoriesViewModel myListViewModel = ViewModelProviders.of(this).get(MyCategoriesViewModel.class);

        myListViewModel.getMutableLiveData(getActivity()).observe(getActivity(), new Observer<List<MyCategoriesViewModel>>() {
            @Override
            public void onChanged(List<MyCategoriesViewModel> myCategoriesViewModels) {

                adapter = new CategoryAdapter(myCategoriesViewModels, getActivity());
                categoryRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                categoryRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        categoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetOffers bottomSheetOffers = new BottomSheetOffers();
                bottomSheetOffers.show(getFragmentManager(), "offers");
            }
        });

        onBack(v);


        return v;
    }


    public void onBack(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {


                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();


                    return true;
                }
                return false;
            }
        });

    }

}