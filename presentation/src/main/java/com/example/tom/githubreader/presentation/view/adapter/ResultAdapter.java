package com.example.tom.githubreader.presentation.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tom.githubreader.presentation.R;
import com.example.tom.githubreader.presentation.model.ResultModel;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.height;
import static android.R.attr.width;

/**
 * Created by Tom on 1.11.2016..
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.UserViewHolder>{

    public interface OnItemClickListener {
        void onUserItemClicked(ResultModel resultModel);
    }

    private List<ResultModel> usersCollection;
    private List<ResultModel> tempUsersCollection=null;
    private final LayoutInflater layoutInflater;
    private Context ctx;

    private ResultAdapter.OnItemClickListener onItemClickListener;

    @Inject
    public ResultAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.usersCollection = Collections.emptyList();
        this.ctx=context;
    }

    @Override public int getItemCount() {
        return (this.usersCollection != null) ? this.usersCollection.size() : 0;
    }

    @Override public ResultAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_user, parent, false);
        return new ResultAdapter.UserViewHolder(view);
    }

    @Override public void onBindViewHolder(ResultAdapter.UserViewHolder holder, final int position) {
        final ResultModel resultModel = this.usersCollection.get(position);
        holder.user.setText(resultModel.getUserName());
        holder.repo.setText("Repo: " + resultModel.getRepoName());
        holder.watchers.setText("Watchers:" + resultModel.getWatchers());
        holder.issues.setText("Issues:" + resultModel.getIssues());
        holder.forks.setText("forks:" + resultModel.getForks());
        Picasso.with(ctx).load(String.valueOf(resultModel.getAvatarUrl())).resize(25,25).into(holder.avatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (ResultAdapter.this.onItemClickListener != null) {
                    ResultAdapter.this.onItemClickListener.onUserItemClicked(resultModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setResultCollection(Collection<ResultModel> usersCollection) {
        try {
            this.validateUsersCollection(usersCollection);

            if(this.tempUsersCollection!=null)
                this.tempUsersCollection.addAll((List<ResultModel>) usersCollection);
            else
                this.tempUsersCollection = (List<ResultModel>) usersCollection;
            this.usersCollection=tempUsersCollection;
            this.notifyDataSetChanged();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setOnItemClickListener (ResultAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    private void validateUsersCollection(Collection<ResultModel> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView user;

        @Bind(R.id.repoTitle)
        TextView repo;

        @Bind(R.id.watchers)
        TextView watchers;

        @Bind(R.id.issues)
        TextView issues;

        @Bind(R.id.forks)
        TextView forks;

        @Bind(R.id.avatar)
        ImageView avatar;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
