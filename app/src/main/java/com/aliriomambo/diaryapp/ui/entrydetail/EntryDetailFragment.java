package com.aliriomambo.diaryapp.ui.entrydetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliriomambo.diaryapp.R;
import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.ui.addeditentry.AddEditEntryActivity;
import com.aliriomambo.diaryapp.ui.addeditentry.AddEditEntryFragment;
import com.aliriomambo.diaryapp.ui.entries.EntriesActivity;
import com.google.common.base.Preconditions;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.util.ArrayList;
import java.util.List;


public class EntryDetailFragment extends Fragment implements EntryDetailContract.View, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    @NonNull
    public static final String ARGUMENT_ENTRY_ID = "ENTRY_ID";

    private RapidFloatingActionLayout mRapidFloatingActionLayout;
    private RapidFloatingActionButton mRapidFloatingActionButton;
    private RapidFloatingActionContentLabelList mRfaContent;
    private EntryDetailContract.Presenter mPresenter;

    private TextView mTitle;
    private TextView mDescription;
    private TextView mFeeling;
    private String mEntryId;
    private Entry mEntry;

    public static EntryDetailFragment newInstance(@Nullable String entryId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_ENTRY_ID, entryId);
        EntryDetailFragment fragment = new EntryDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entrydetail, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setPresenter(EntryDetailContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mEntryId = getArguments().getString(EntryDetailFragment.ARGUMENT_ENTRY_ID);

        }

        mTitle = view.findViewById(R.id.txt_title_entrydetail);
        mDescription = view.findViewById(R.id.txt_description_entrydetail);
        mFeeling = view.findViewById(R.id.txt_feeling_entrydetail);

        mRapidFloatingActionLayout = view.findViewById(R.id.rfal_entrydetail);
        mRapidFloatingActionButton = view.findViewById(R.id.rfab_entrydetail);
        mRfaContent = new RapidFloatingActionContentLabelList(getContext());
        mRfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        setupFab();
        mPresenter.loadEntry(mEntryId);

    }

    public void setupFab() {
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.action_edit))
                .setLabelSizeSp(14)
                .setResId(R.drawable.ic_edit)
                .setIconNormalColor(getResources().getColor(R.color.colorPurple))
                .setIconPressedColor(getResources().getColor(R.color.colorPurple))
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel(getResources().getString(R.string.action_delete))
                .setLabelSizeSp(14)
                .setResId(R.drawable.ic_delete)
                .setIconNormalColor(getResources().getColor(R.color.colorPurple))
                .setIconPressedColor(getResources().getColor(R.color.colorPurple))
                .setWrapper(1)
        );
        mRfaContent
                .setItems(items)
                .setIconShadowRadius(RFABTextUtil.dip2px(getContext(), 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(RFABTextUtil.dip2px(getContext(), 5));
        new RapidFloatingActionHelper(
                getContext(),
                mRapidFloatingActionLayout,
                mRapidFloatingActionButton,
                mRfaContent
        ).build();
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        Integer integer = Integer.valueOf(item.getWrapper().toString());
        switch (integer) {
            case 0:
                mPresenter.editEntry(mEntry);
                break;
            case 1:
                mPresenter.deleteEntry(mEntry);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        Integer integer = Integer.valueOf(item.getWrapper().toString());
        switch (integer) {
            case 0:
                mPresenter.editEntry(mEntry);
                break;
            case 1:
                mPresenter.deleteEntry(mEntry);
                break;
            default:
                break;
        }
    }

    @Override
    public void showEntryDetail(Entry entry) {
        mTitle.setText(entry.getTitle());
        mDescription.setText(entry.getDescription());
        mFeeling.setText("I was feeling "+ entry.getFeeling() + " when I wrote this");
        mEntry = entry;

    }

    @Override
    public void openEditScreen(String entryId) {
        Intent intent = new Intent(getContext(), AddEditEntryActivity.class);
        intent.putExtra(AddEditEntryFragment.ARGUMENT_EDIT_ENTRY_ID, entryId);
        startActivity(intent);

    }

    @Override
    public void openEntriesScreen() {
        Intent intent = new Intent(getContext(), EntriesActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();

        }
    }
}
