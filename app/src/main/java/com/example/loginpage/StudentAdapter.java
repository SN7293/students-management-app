package com.example.loginpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<ModelStudent> studentList;
    private Context context;
    private OnStudentClickListener onStudentClickListener;

    public interface OnStudentClickListener {
        void onStudentClick(ModelStudent student);
    }

    public StudentAdapter(List<ModelStudent> studentList, Context context, OnStudentClickListener listener) {
        this.studentList = studentList;
        this.context = context;
        this.onStudentClickListener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        ModelStudent student = studentList.get(position);
        holder.nameTextView.setText(student.getName()); // Assuming ModelStudent has a getName() method
        holder.regNoTextView.setText(student.getRegNo()); // Assuming ModelStudent has a getRegNumber() method

        holder.itemView.setOnClickListener(v -> onStudentClickListener.onStudentClick(student));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView regNoTextView;
        TextView tv_father_name;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            tv_father_name = itemView.findViewById(R.id.tv_father_name);
            regNoTextView = itemView.findViewById(R.id.tv_reg_no);
        }
    }
}
