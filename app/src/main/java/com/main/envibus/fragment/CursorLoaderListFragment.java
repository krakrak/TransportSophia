package com.main.envibus.fragment;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.main.envibus.R;


public class CursorLoaderListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    SimpleCursorAdapter mAdapter;
    String mCurlFilter;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEmptyText("Pas de résultats");

        setHasOptionsMenu(true);

        String [] results = getResources().getStringArray(R.array.stops_array);

        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null, results,
                new int[] {android.R.id.text1}, 0);

        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }




    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }



}
