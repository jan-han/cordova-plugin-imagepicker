package com.lzy.imagepicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;

import com.lzy.imagepicker.util.Utils;
import com.lzy.imagepicker.bean.ImageFolder;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImageFolderAdapter extends BaseAdapter {

    private ImagePicker imagePicker;
    private Activity mActivity;

    private int res_adapter_folder_list_item;
    private int res_ip_folder_image_count;
    private int res_iv_cover;
    private int res_tv_folder_name;
    private int res_tv_image_count;
    private int res_iv_folder_check;
    private LayoutInflater mInflater;
    private int mImageSize;
    private List<ImageFolder> imageFolders;
    private int lastSelected = 0;

    public ImageFolderAdapter(Activity activity, List<ImageFolder> folders) {
        mActivity = activity;
        Context appContext = activity.getApplicationContext();
        Resources resource = appContext.getResources();
        String pkgName = appContext.getPackageName();
        res_adapter_folder_list_item = resource.getIdentifier("adapter_folder_list_item", "layout", pkgName);
        res_ip_folder_image_count = resource.getIdentifier("ip_folder_image_count", "string", pkgName);
        res_iv_cover = resource.getIdentifier("iv_cover", "id", pkgName);
        res_tv_folder_name = resource.getIdentifier("tv_folder_name", "id", pkgName);
        res_tv_image_count = resource.getIdentifier("tv_image_count", "id", pkgName);
        res_iv_folder_check = resource.getIdentifier("iv_folder_check", "id", pkgName);
        if (folders != null && folders.size() > 0) imageFolders = folders;
        else imageFolders = new ArrayList<>();

        imagePicker = ImagePicker.getInstance();
        mImageSize = Utils.getImageItemWidth(mActivity);
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void refreshData(List<ImageFolder> folders) {
        if (folders != null && folders.size() > 0) imageFolders = folders;
        else imageFolders.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageFolders.size();
    }

    @Override
    public ImageFolder getItem(int position) {
        return imageFolders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(res_adapter_folder_list_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageFolder folder = getItem(position);
        holder.folderName.setText(folder.name);
        holder.imageCount.setText(mActivity.getString(res_ip_folder_image_count, folder.images.size()));
        imagePicker.getImageLoader().displayImage(mActivity, folder.cover.path, holder.cover, mImageSize, mImageSize);

        if (lastSelected == position) {
            holder.folderCheck.setVisibility(View.VISIBLE);
        } else {
            holder.folderCheck.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void setSelectIndex(int i) {
        if (lastSelected == i) {
            return;
        }
        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    private class ViewHolder {
        ImageView cover;
        TextView folderName;
        TextView imageCount;
        ImageView folderCheck;

        public ViewHolder(View view) {
            cover = (ImageView) view.findViewById(res_iv_cover);
            folderName = (TextView) view.findViewById(res_tv_folder_name);
            imageCount = (TextView) view.findViewById(res_tv_image_count);
            folderCheck = (ImageView) view.findViewById(res_iv_folder_check);
            view.setTag(this);
        }
    }
}
