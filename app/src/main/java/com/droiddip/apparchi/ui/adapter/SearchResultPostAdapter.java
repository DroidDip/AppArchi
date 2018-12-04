package com.athlepic.app.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.athlepic.app.R;
import com.athlepic.app.anim.ItemPressInterpolator;
import com.athlepic.app.model.SearchPostModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Supriya on 12-04-2017.
 */
public class SearchResultPostAdapter extends RecyclerView.Adapter<SearchResultPostAdapter.ViewHolder> {

    private LayoutInflater laInflater;
    private Context pContext;
    private int clickedItemPosition = -1;
    private Handler handler;
    private long mLastClickTime;
    private static final long CLICK_TIME_INTERVAL = 300;
    private ArrayList<SearchPostModel> searchPostList;
    private ImageLoader imageLoader;
    private WeakReference<SearchResultPostClickListener> resultPostClickListener;

    public SearchResultPostAdapter(Context context, SearchResultPostClickListener callback) {
        this.pContext = context;
        laInflater = LayoutInflater.from(context);
        this.resultPostClickListener = new WeakReference<>(callback);
        handler = new Handler();
        mLastClickTime = System.currentTimeMillis();
        searchPostList = new ArrayList<>();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = laInflater.inflate(R.layout.row_view_search_result_post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SearchPostModel searchPostModel = searchPostList.get(position);

        try {
            if (!TextUtils.isEmpty(searchPostModel.getActivity_image()))
                Glide.with(pContext).load(searchPostModel.getActivity_image())
                        .placeholder(R.drawable.ic_gallery_image_placeholder).error(R.drawable.ic_gallery_image_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(holder.iv_search_post_item_image);
//                imageLoader.displayImage(searchPostModel.getActivity_image(), holder.iv_search_post_item_image, Athlepic.getDisplayImageOption(R.drawable.ic_gallery_image_placeholder));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (searchPostList != null && searchPostList.size() > 0) ? searchPostList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatImageView iv_search_post_item_image;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_search_post_item_image = (AppCompatImageView) itemView.findViewById(R.id.iv_search_post_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            long now = System.currentTimeMillis();
            if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                return;
            }
            mLastClickTime = now;
            clickedItemPosition = getAdapterPosition();
            animateItem(v);
        }
    }

    public void reloadAdapter(ArrayList<SearchPostModel> _searchPostList) {
        this.searchPostList.clear();
        this.searchPostList.addAll(_searchPostList);
        notifyDataSetChanged();
    }

    public interface SearchResultPostClickListener {
        void onSearchPostClicked(SearchPostModel searchPostModel);
    }

    private void animateItem(View view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setInterpolator(new ItemPressInterpolator())
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(final View view) {

                    }

                    @Override
                    public void onAnimationEnd(final View view) {
                        if (resultPostClickListener != null)
                            resultPostClickListener.get().onSearchPostClicked(searchPostList.get(clickedItemPosition));
                    }

                    @Override
                    public void onAnimationCancel(final View view) {

                    }
                })
                .withLayer()
                .start();
    }
}
