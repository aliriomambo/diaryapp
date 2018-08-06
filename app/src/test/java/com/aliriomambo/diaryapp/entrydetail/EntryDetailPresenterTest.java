package com.aliriomambo.diaryapp.entrydetail;

import com.aliriomambo.diaryapp.data.model.Entry;
import com.aliriomambo.diaryapp.data.source.Repository;
import com.aliriomambo.diaryapp.ui.entrydetail.EntryDetailContract;
import com.aliriomambo.diaryapp.ui.entrydetail.EntryDetailPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of {@link EntryDetailPresenter}
 */
public class EntryDetailPresenterTest {

    private static final String TITLE_TEST = "title";

    private static final String DESCRIPTION_TEST = "description";
    private static final String FEELING_TEST = "feeling";

    private static final String ENTRY_ID = "entry_id";

    public static final Entry ENTRY = new Entry(TITLE_TEST, DESCRIPTION_TEST, FEELING_TEST, ENTRY_ID);

    @Mock
    private Repository mEntryRepository;

    @Mock
    private EntryDetailContract.View mEntryDetailView;


    private EntryDetailPresenter mEntryDetailPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mEntryDetailPresenter = new EntryDetailPresenter(
                mEntryRepository, mEntryDetailView);

        // Then the presenter is set to the view
        verify(mEntryDetailView).setPresenter(mEntryDetailPresenter);
    }

    @Test
    public void deleteEntry() {

        mEntryDetailPresenter = new EntryDetailPresenter(
                mEntryRepository, mEntryDetailView);
        mEntryDetailPresenter.deleteEntry(ENTRY);
    }


}
