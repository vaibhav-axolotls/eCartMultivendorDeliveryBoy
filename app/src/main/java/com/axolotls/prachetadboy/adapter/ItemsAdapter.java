package com.axolotls.prachetadboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import com.axolotls.prachetadboy.R;
import com.axolotls.prachetadboy.helper.Constant;
import com.axolotls.prachetadboy.helper.Session;
import com.axolotls.prachetadboy.model.Items;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.OrderItemHolder> {

    final Activity activity;
    final ArrayList<Items> items;
    final Session session;

    public ItemsAdapter(Activity activity, ArrayList<Items> items) {
        this.activity = activity;
        this.items = items;
        session = new Session(activity);
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_items, null);
        return new OrderItemHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull final OrderItemHolder holder, int position) {

        final Items item = items.get(position);

        holder.tvProductName.setText(item.getName());
        holder.tvUnit.setText(activity.getString(R.string.unit_) + item.getMeasurement() + " " + item.getUnit());
        holder.tvQuantity.setText(activity.getString(R.string.qty_) + item.getQuantity());
        holder.tvPrice.setText(activity.getString(R.string.price_) + new Session(activity).getData(Constant.CURRENCY) + "): " + item.getPrice());
        holder.tvDiscountPrice.setText(activity.getString(R.string.discount_) + new Session(activity).getData(Constant.CURRENCY) + "): " + item.getDiscounted_price());
        holder.tvTaxPercentage.setText(activity.getString(R.string.tax_) + new Session(activity).getData(Constant.CURRENCY) + "): " + item.getTax_amount());
        holder.tvTax.setText(activity.getString(R.string.tax__) + item.getTax_percentage());
        holder.tvSubTotal.setText(activity.getString(R.string.subtotal_) + new Session(activity).getData(Constant.CURRENCY) + "): " + item.getSub_total());

        if (item.getActive_status().equalsIgnoreCase(Constant.CANCELLED)) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(Constant.CANCELLED);
        } else if (item.getActive_status().equalsIgnoreCase(Constant.RETURNED)) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setText(Constant.RETURNED);
        }

        Picasso.get().
                load(item.getImage())
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imgProduct);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(activity.getString(R.string.awaiting_payment));
        arrayList.add(activity.getString(R.string.received));
        arrayList.add(activity.getString(R.string.processed));
        arrayList.add(activity.getString(R.string.shipped));
        arrayList.add(activity.getString(R.string.delivered));
        arrayList.add(activity.getString(R.string.cancelled));
        arrayList.add(activity.getString(R.string.returned));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, arrayList);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class OrderItemHolder extends RecyclerView.ViewHolder {
        final TextView tvProductName, tvUnit, tvQuantity, tvPrice, tvDiscountPrice, tvSubTotal, tvTaxPercentage, tvTax, tvStatus;
        final ImageView imgProduct;

        public OrderItemHolder(View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            tvSubTotal = itemView.findViewById(R.id.tvSubTotal);
            tvTaxPercentage = itemView.findViewById(R.id.tvTaxPercentage);
            tvTax = itemView.findViewById(R.id.tvTax);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}