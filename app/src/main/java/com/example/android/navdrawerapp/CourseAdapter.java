package com.example.android.navdrawerapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by katherine on 6/4/18.
 */

public class CourseAdapter extends ArrayAdapter<Course> {
    CourseAdapter(Context context, int resource, List<Course> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.course_list_item, parent, false);
        }

        TextView courseTextview = (TextView) convertView.findViewById(R.id.course);

        Course courseName = getItem(position);

        boolean isCourse = courseName.getCourseUrl() != null;
        if (isCourse) {
            courseTextview.setVisibility(View.VISIBLE);
            Picasso.with(courseTextview.getContext())
                    .load(courseName.getCourseUrl())
                    .into((Target) courseTextview);
        } else {
            courseTextview.setVisibility(View.GONE);
        }

        return convertView;
    }
}