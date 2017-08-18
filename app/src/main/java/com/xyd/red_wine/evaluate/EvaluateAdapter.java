package com.xyd.red_wine.evaluate;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xyd.red_wine.R;

import java.io.File;
import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/24
 * @time: 10:31
 * @description:
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder>{
    private OnDeleteClick click;
    private Context context;
    private List<File>  images;
    public EvaluateAdapter(Context context, List<File>  images,OnDeleteClick click){
        this .context=context;
        this.images=images;
        this.click=click;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_evaluate_image,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.iv.setImageBitmap(BitmapFactory.decodeFile(images.get(position).getAbsolutePath()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.delete(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final ImageView iv;
        private final ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.evaluate_image_iv);
            delete = (ImageView) itemView.findViewById(R.id.evaluate_image_iv);
        }
    }
    interface OnDeleteClick{
        void delete(int position);
    }
}
