package com.example.facultyapp.ui.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.facultyapp.R;
import com.example.facultyapp.data.model.Notes;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MenuOptionsViewHolder> {
    CustomItemClickListener listener;
    private Context context;
    private List<Notes> notesList;

    public StudentAdapter(Context context, List<Notes> notesList, CustomItemClickListener listener) {
        this.context = context;
        this.notesList = notesList;
        this.listener = listener;

    }

    @Override
    public StudentAdapter.MenuOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_main_books, parent, false);
        final StudentAdapter.MenuOptionsViewHolder mViewHolder = new StudentAdapter.MenuOptionsViewHolder(itemView);
        itemView.setOnClickListener(v -> listener.onItemClick(v, mViewHolder.getPosition()));
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuOptionsViewHolder holder, int position) {
        Notes menuItem = notesList.get(position);

        holder.bookName.setText(menuItem.getBookName());
        holder.bookYear.setText(menuItem.getBookYear());
        holder.bookSemester.setText(menuItem.getBookSemester());
        holder.pdfView.fromUri(Uri.parse(menuItem.bookUrl));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public Object getItem(int location) {
        return notesList.get(location);
    }

    class MenuOptionsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bookName)
        TextView bookName;
        @BindView(R.id.bookYear)
        TextView bookYear;
        @BindView(R.id.bookSemester)
        TextView bookSemester;
        @BindView(R.id.pdfView)
        PDFView pdfView;

        MenuOptionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}