package com.example.zozo.myapplication;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkersSectionAdapter extends RecyclerView.Adapter<WorkersSectionAdapter.ViewHolder> {
    //Data
    int size;
    int keyCounter=0;
    int dataCounter=0;
    ArrayList<String> keys;
    ArrayList<String> workers=new ArrayList<>();
    HashMap<String,ArrayList<String>> data=new HashMap<>();
    HashMap<Integer,Postion>postionHashMap=new HashMap<>();
    public WorkersSectionAdapter(int Size,HashMap<String,ArrayList<String>> Data)
    {
        this.size=Size;
        this.data=Data;
        keys=new ArrayList<>(Data.keySet());
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
        Log.v("res","i am done");
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
        /*if (i==0&&keyCounter==0)
        {
            viewHolder.sectionTitleText.setText(keys.get(keyCounter));
            viewHolder.sectionTitleText.setVisibility(View.VISIBLE);
            workers=data.get(keys.get(keyCounter));
            viewHolder.nameText.setText(workers.get(dataCounter));
            dataCounter++;
        }else if (dataCounter<workers.size())
        {
            viewHolder.nameText.setText(workers.get(dataCounter));
            viewHolder.sectionTitleText.setVisibility(View.GONE);
            dataCounter++;
        }else if (keyCounter<keys.size()&&(keyCounter+1)<keys.size())
        {
            keyCounter++;
            viewHolder.sectionTitleText.setText(keys.get(keyCounter));
            viewHolder.sectionTitleText.setVisibility(View.VISIBLE);
            workers=data.get(keys.get(keyCounter));
            dataCounter=0;
            viewHolder.nameText.setText(workers.get(dataCounter));
            dataCounter++;
        }*/
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
}
