package com.test.note.pankaj.notes.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.note.pankaj.notes.R;
import com.test.note.pankaj.notes.Utils;
import com.test.note.pankaj.notes.model.Note;
import com.test.note.pankaj.notes.views.AddUpdateNoteActivity;

import java.util.List;

/**
 * Created by Pankaj on 03/12/17.
 */

public class NoteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_EMPTY = 1;
    private final static int VIEW_DATA = 2;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Note> list;

    public NoteListAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addUpdateList(List<Note> newList) {
        if (newList == null) return;
        list.clear();
        list = newList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_EMPTY) {
            return new EmptyViewHolder(layoutInflater.inflate(R.layout.row_empty_view_layout, parent,
                    false));
        } else {
            return new NoteViewHolder(layoutInflater.inflate(R.layout.row_note_item_layout, parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            return;
        }
        NoteViewHolder viewHolder = (NoteViewHolder) holder;
        Note obj = list.get(position);
        if (obj == null) return;
        viewHolder.tvTitle.setText(obj.getTitle());
        viewHolder.tvDate.setText(Utils.getFormattedDateTime(obj.getTimeStamp()));
        viewHolder.tvnote.setText(obj.getNote());

        viewHolder.constraintLayout.setTag(obj);
        viewHolder.constraintLayout.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Note obj = (Note) view.getTag();
            if (obj == null) return;
            AddUpdateNoteActivity.startActivity(context, obj);
        }
    };

    @Override
    public int getItemCount() {
        if (list == null || list.size() <= 0) return 1;
        else return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list == null || list.size() <= 0) return VIEW_EMPTY;
        return VIEW_DATA;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        TextView tvTitle, tvDate, tvnote;

        NoteViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvnote = itemView.findViewById(R.id.tv_note);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
