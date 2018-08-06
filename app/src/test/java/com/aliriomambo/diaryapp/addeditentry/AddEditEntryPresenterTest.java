
package com.aliriomambo.diaryapp.addeditentry;

import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.data.source.Repository;
import com.aliriomambo.diaryapp.ui.addeditentry.AddEditEntryContract;
import com.aliriomambo.diaryapp.ui.addeditentry.AddEditEntryPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


public class AddEditEntryPresenterTest {

    @Mock
    private Repository mEntryRepository;

    @Mock
    private AddEditEntryContract.View mAddEditEntryView;


    private AddEditEntryPresenter mAddEditEntryPresenter;

    @Before
    public void setupMocksAndView() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        mAddEditEntryPresenter = new AddEditEntryPresenter(
                null, mAddEditEntryView);

        verify(mAddEditEntryView).setPresenter(mAddEditEntryPresenter);
    }

    @Test
    public void saveNewEntryToRepository_showsSuccessMessageUi() {
        mAddEditEntryPresenter = new AddEditEntryPresenter(
                null, mEntryRepository, mAddEditEntryView);

        mAddEditEntryPresenter.saveEntry("Title", "Description", "Nothing", "MASTER");

        verify(mEntryRepository).addEntry(any(Entry.class)); // saved to the model
        verify(mAddEditEntryView).showEntryList(); // shown in the UI
    }

    @Test
    public void saveEntry_emptyTaskShowsErrorUi() {
        mAddEditEntryPresenter = new AddEditEntryPresenter(
                null, mEntryRepository, mAddEditEntryView);

        mAddEditEntryPresenter.saveEntry("", "", "", "");

        verify(mAddEditEntryView).showEmptyEntryError();
    }


}
