package com.axolotls.prachetadboy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import com.axolotls.prachetadboy.R;
import com.axolotls.prachetadboy.activity.OrderDetailActivity;
import com.axolotls.prachetadboy.helper.ApiConfig;
import com.axolotls.prachetadboy.helper.Constant;
import com.axolotls.prachetadboy.helper.Session;
import com.axolotls.prachetadboy.model.OrderList;

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // for load more
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    final Activity activity;
    final ArrayList<OrderList> orderTrackerArrayList;
    public boolean isLoading;


    public OrderListAdapter(Activity activity, ArrayList<OrderList> orderTrackerArrayList) {
        this.activity = activity;
        this.orderTrackerArrayList = orderTrackerArrayList;
    }

    public void add(int position, OrderList item) {
        orderTrackerArrayList.add(position, item);
        notifyItemInserted(position);
    }

    public void setLoaded() {
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.lyt_order_list, parent, false);
            return new TrackerHolderItems(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_progressbar, parent, false);
            return new ViewHolderLoading(view);
        }

        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, final int position) {

        if (holderParent instanceof TrackerHolderItems) {
            final TrackerHolderItems holder = (TrackerHolderItems) holderParent;
            final OrderList order = orderTrackerArrayList.get(position);
            holder.tvOrderId.setText(activity.getString(R.string.order_number) + order.getId());
            String[] date = order.getDate_added().split("\\s+");
            holder.tvOrderDate.setText(activity.getString(R.string.ordered_on) + date[0]);
            holder.tvOrderAmount.setText(activity.getString(R.string.for_amount_on) + new Session(activity).getData(Constant.CURRENCY) + ApiConfig.StringFormat(order.getFinal_total()));

            holder.lytMain.setOnClickListener(v -> activity.startActivity(new Intent(activity, OrderDetailActivity.class).putExtra(Constant.POSITION, position).putExtra(Constant.ITEM, order)));

            ArrayList<String> items = new ArrayList<>();
            for (int i = 0; i < order.getItems().size(); i++) {
                items.add(order.getItems().get(i).getName());
            }
            holder.tvItems.setText(Arrays.toString(items.toArray()).replace("]", "").replace("[", ""));
            holder.tvTotalItems.setText(items.size() + activity.getString(R.string.item));

        } else if (holderParent instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holderParent;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return orderTrackerArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return orderTrackerArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolderLoading extends RecyclerView.ViewHolder {
        public final ProgressBar progressBar;

        public ViewHolderLoading(View view) {
            super(view);
            progressBar = view.findViewById(R.id.itemProgressbar);
        }
    }

    public static class TrackerHolderItems extends RecyclerView.ViewHolder {
        final TextView tvOrderId;
        final TextView tvOrderDate;
        final RelativeLayout lytMain;
        final TextView tvOrderAmount;
        final TextView tvTotalItems;
        final TextView tvItems;

        public TrackerHolderItems(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            lytMain = itemView.findViewById(R.id.lytMain);
            tvOrderAmount = itemView.findViewById(R.id.tvOrderAmount);
            tvTotalItems = itemView.findViewById(R.id.tvTotalItems);
            tvItems = itemView.findViewById(R.id.tvItems);

        }
    }
}