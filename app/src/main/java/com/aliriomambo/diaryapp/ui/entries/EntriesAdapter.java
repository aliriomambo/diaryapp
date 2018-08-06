package com.aliriomambo.diaryapp.ui.entries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.data.model.Entry;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder> implements Filterable {
    private ArrayList<Entry> entryArrayList;
    private EntriesRecyclerViewListener recycleItemClick;
    private List<Entry> entryListFiltered;
    private Context mContext;

    public EntriesAdapter(Context context, ArrayList<Entry> entryArrayList,
                          EntriesRecyclerViewListener recycleItemClick) {
        this.recycleItemClick = recycleItemClick;
        this.entryArrayList = entryArrayList;
        entryListFiltered = entryArrayList;
        mContext = context;
    }

    @NonNull
    @Override
    public EntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recycler_item_entries, parent, false);
        return new EntriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntriesViewHolder holder, final int position) {
        final Entry entry = entryArrayList.get(position);
        holder.mTitle.setText(entry.getTitle());
        holder.mFeeling.setText(entry.getFeeling());
        holder.mDateTime.setText(entry.getDateTime());

        holder.mImageFeeling.setImageResource(verifyWhichImage(entry.getFeeling()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recycleItemClick != null) {
                    recycleItemClick.onItemClick(view, holder.getAdapterPosition(), entry.getId());
                }
            }
        });

    }

    private int verifyWhichImage(String feeling) {
        switch (feeling) {
            case "Happiness":
                return R.drawable.ic_happy_emoji;
            case "Fear":
                return R.drawable.ic_fear_emoji;
            case "Disgust":
                return R.drawable.ic_disgust_emoji;
            case "Anger":
                return R.drawable.ic_angry_emoji;
            case "Sadness":
                return R.drawable.ic_sad_emoji;
            case "Surprise":
                return R.drawable.ic_surprise_emoji;
            case "Contempt":
                return R.drawable.ic_contempt_emoji;
            default:
                return 0;
        }
    }


    @Override
    public int getItemCount() {
        return entryListFiltered.size();
    }

    public void replaceData(ArrayList<Entry> entries) {
        setList(entries);
        notifyDataSetChanged();
    }

    private void setList(ArrayList<Entry> entryList) {
        entryArrayList.clear();
        entryListFiltered.clear();
        entryArrayList = checkNotNull(entryList);
        entryListFiltered = checkNotNull(entryList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    entryListFiltered = entryArrayList;
                } else {
                    List<Entry> filteredList = new ArrayList<>();
                    for (Entry row : entryArrayList) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getFeeling().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    entryListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = entryListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                entryListFiltered = (ArrayList<Entry>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class EntriesViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mFeeling;
        private TextView mDateTime;
        private ImageView mImageFeeling;

        private EntriesViewHolder(final View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.txt_title);
            mDateTime = itemView.findViewById(R.id.txt_date_time);
            mFeeling = itemView.findViewById(R.id.txt_feeling);
            mImageFeeling = itemView.findViewById(R.id.img_feeling);
        }
    }


}

