package com.example.a9476.newsapp;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by a9476 on 2016/8/3.
 */
public class IosRecyclerViewAdapter extends RecyclerView.Adapter<IosRecyclerViewAdapter.ViewHolder> {

    private List<AndroidResults> androidResultsList;
    private LayoutInflater inflater;
    private Context context;
    private Handler webViewHandler = new Handler();
    public IosRecyclerViewAdapter(Context context, List<AndroidResults> androidResultsList){
        this.androidResultsList = androidResultsList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public IosRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.iositem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final IosRecyclerViewAdapter.ViewHolder holder, int position) {
        final AndroidResults androidResults = new AndroidResults();
        holder.description.setText(androidResults.getTitle());
        holder.author.setText(androidResults.getAuthor());
        webViewHandler.post(new Runnable() {
            @Override
            public void run() {
                holder.webView.loadUrl(androidResults.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return androidResultsList.size();
    }

    public void setData(List<AndroidResults> androidResultsList) {
        this.androidResultsList = androidResultsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        TextView author;
        WebView webView;
        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description_android);
            author = (TextView) itemView.findViewById(R.id.author_android);
            webView = (WebView) itemView.findViewById(R.id.webView_android);
        }
    }
}
