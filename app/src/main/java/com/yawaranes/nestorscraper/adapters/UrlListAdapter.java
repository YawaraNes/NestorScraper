package com.yawaranes.nestorscraper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yawaranes.nestorscraper.R;
import com.yawaranes.nestorscraper.base.CustomBaseAdapter;
import com.yawaranes.nestorscraper.model.UrlEntity;

public class UrlListAdapter extends CustomBaseAdapter<UrlEntity> {

    static class ViewHolder {

        protected TextView urlAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        UrlEntity item = list.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.url_list_item_layout, parent, false);

            holder = new ViewHolder();

            holder.urlAddress = (TextView) convertView.findViewById(R.id.url_address);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Erasing possible previously data if no valid item retrieved
        holder.urlAddress.setText("");

        if (item != null) {
            holder.urlAddress.setText(item.getUrlAddress());
        }

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
