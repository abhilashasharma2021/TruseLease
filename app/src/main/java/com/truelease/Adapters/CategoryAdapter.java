package com.truelease.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.truelease.Fragments.ProductFragment;
import com.truelease.Others.AppConstats;
import com.truelease.Others.SharedHelper;
import com.truelease.R;
import com.truelease.databinding.MyBinding;
import com.truelease.viewModel.MyCategoriesViewModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<MyCategoriesViewModel> myCategoriesViewModelList;
    Context context;
    private LayoutInflater layoutInflater;

    public CategoryAdapter(List<MyCategoriesViewModel> myCategoriesViewModelList, Context context) {
        this.myCategoriesViewModelList = myCategoriesViewModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MyBinding myListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.category_layout, parent, false);
        return new ViewHolder(myListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, final int position) {

        final MyCategoriesViewModel data = myCategoriesViewModelList.get(position);
        holder.bind(data);

        holder.myListBinding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedHelper.putKey(context, AppConstats.CATEGORY_ID, data.getId());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ProductFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

            }
        });


    }


    @Override
    public int getItemCount() {
        return myCategoriesViewModelList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MyBinding myListBinding;


        public ViewHolder(@NonNull MyBinding myListBinding) {
            super(myListBinding.getRoot());
            this.myListBinding = myListBinding;
        }


        public void bind(MyCategoriesViewModel myCategoriesViewModel) {
            this.myListBinding.setMylistmodel(myCategoriesViewModel);
            myListBinding.executePendingBindings();

        }

        public MyBinding getMyListBinding() {
            return myListBinding;
        }

    }
}


