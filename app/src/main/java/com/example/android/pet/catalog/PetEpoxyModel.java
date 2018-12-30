package com.example.android.pet.catalog;

import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.example.android.pet.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

@EpoxyModelClass(layout = R.layout.item_pet)
public abstract class PetEpoxyModel extends EpoxyModelWithHolder<PetEpoxyModel.PetEpoxyHolder> {

    @EpoxyAttribute
    String name;

    @EpoxyAttribute
    String breed;

    @Override
    public void bind(@NonNull PetEpoxyHolder holder) {
        super.bind(holder);
        holder.name.setText(name);
        holder.breed.setText(breed);
    }

    class PetEpoxyHolder extends EpoxyHolder {

        @BindView(R.id.tv_name)
        TextView name;

        @BindView(R.id.tv_breed)
        TextView breed;

        @Override
        protected void bindView(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
