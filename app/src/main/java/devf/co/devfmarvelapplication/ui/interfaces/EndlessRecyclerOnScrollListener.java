package devf.co.devfmarvelapplication.ui.interfaces;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import devf.co.devfmarvelapplication.ui.adapters.CharactersListAdapter;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 12; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = ((CharactersListAdapter)recyclerView.getAdapter()).getCharacterItemsCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();


        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
    }

        if (!loading && ((totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold))) {

            loading = true; 

            // Do something
            current_page++;

            onLoadMore(current_page);

        }
    }

    public abstract void onLoadMore(int current_page);
}
