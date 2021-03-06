package com.example.zozo.myapplication.Adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.zozo.myapplication.DataModel.Postion;
import com.example.zozo.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkersSectionAdapter extends RecyclerView.Adapter<WorkersSectionAdapter.ViewHolder> {
    //Data
    int size;
    ArrayList<String> keys;
    HashMap<String,ArrayList<String>> data=new HashMap<>();
    HashMap<Integer,Postion>postionHashMap=new HashMap<>();
    public WorkersSectionAdapter(int Size,HashMap<String,ArrayList<String>> Data)
    {
        this.size=Size;
        this.data=Data;
        keys=new ArrayList<>(Data.keySet());
        mapingPostions();
    }
    @NonNull
    @Override
    public WorkersSectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.worker_section_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkersSectionAdapter.ViewHolder viewHolder, int i) {
        if (postionHashMap.get(i).workerPos==0)
        {
            viewHolder.sectionTitleText.setText(keys.get(postionHashMap.get(i).keyPos));
            viewHolder.nameText.setText(data.get(keys.get(postionHashMap.get(i).keyPos)).get(postionHashMap.get(i).workerPos));
            viewHolder.sectionTitleText.setVisibility(View.VISIBLE);
            viewHolder.dataArea.setVisibility(View.VISIBLE);
        }else
        {
            viewHolder.nameText.setText(data.get(keys.get(postionHashMap.get(i).keyPos)).get(postionHashMap.get(i).workerPos));
            viewHolder.sectionTitleText.setVisibility(View.GONE);
            viewHolder.dataArea.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView sectionTitleText,nameText;
        public ConstraintLayout dataArea;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionTitleText=itemView.findViewById(R.id.sectionTitleTV);
            nameText=itemView.findViewById(R.id.nameSectionTV);
            dataArea=itemView.findViewById(R.id.dataArea);
        }
    }
    void mapingPostions()
    {
        int counter=0;
        for (int i=0;i<keys.size();i++)
        {
            ArrayList<String> work=data.get(keys.get(i));
            for (int ii=0;ii<work.size();ii++)
            {
                postionHashMap.put(counter,new Postion(i,ii));
                counter++;
            }
        }
    }
}
