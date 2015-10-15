package com.yawaranes.nestorscraper.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yawaranes.nestorscraper.App;
import com.yawaranes.nestorscraper.R;
import com.yawaranes.nestorscraper.adapters.UrlListAdapter;
import com.yawaranes.nestorscraper.base.BaseFragment;
import com.yawaranes.nestorscraper.model.UrlEntity;
import com.yawaranes.nestorscraper.network.gateway.GatewayResponse;
import com.yawaranes.nestorscraper.network.request.ErrorResponse;
import com.yawaranes.nestorscraper.utils.HtmlUtils;
import com.yawaranes.nestorscraper.utils.IntentUtils;
import com.yawaranes.nestorscraper.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class UrlListFragment extends BaseFragment {

    private List<UrlEntity> urlList;
    private ListView urlListView;
    private UrlListAdapter urlListAdapter;
    private TextView emptyUrlList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (container == null) {
            return null;
        }

        mRootView = inflater.inflate(R.layout.url_list_layout, container, false);

        initUIComponents();

        // Requesting data
        requestSourceCode();

        return mRootView;
    }

    private void initUIComponents() {
        // Add listener to view so on click outside, the keyboard is hidden
        addViewListener(mRootView);

        urlListView = (ListView) mRootView.findViewById(R.id.url_list_view);
        emptyUrlList = (TextView) mRootView.findViewById(R.id.url_list_empty);

        urlListAdapter = new UrlListAdapter();

        // Setting the list item click listener
        urlListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                startWebIntent(urlListAdapter.getItem(position));
            }
        });

        loadUITexts();
    }

    @Override
    public void loadUIComponents() {
        // If we have a valid list to print...
        if (urlList != null && !urlList.isEmpty()) {
            // Setting adapter
            urlListAdapter.init(getActivity(), urlList);
            urlListView.setAdapter(urlListAdapter);

            // Showing result
            urlListView.setVisibility(View.VISIBLE);
            emptyUrlList.setVisibility(View.GONE);
        } else {
            // Showing no data
            urlListView.setVisibility(View.GONE);
            emptyUrlList.setVisibility(View.VISIBLE);
        }
    }

    private void requestSourceCode() {
        App.getApi().getSourceCode(
                new GatewayResponse.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fillUrlListFromSourceCode(response);
                        loadUIComponents();
                    }
                }, new GatewayResponse.ErrorListener() {
                    @Override
                    public void onErrorResponse(ErrorResponse errorResponse) {
                    }
                });
    }

    private void fillUrlListFromSourceCode(String sourceCode) {
        urlList = new ArrayList<>();
        List<String> foundLinkList = HtmlUtils.extractLinksFromHtml(sourceCode);
        for (String link : foundLinkList) {
            // Adding only valid URLs (excluding "tel:", "mailto:" and so on)
            if (ValidationUtils.isValidUrl(link)) {
                urlList.add(new UrlEntity(link));
            }
        }
    }

    private void startWebIntent(UrlEntity urlEntity) {
        IntentUtils.launchWebIntent(getActivity(), urlEntity.getUrlAddress());
    }

    @Override
    public void loadUITexts() {

    }
}
