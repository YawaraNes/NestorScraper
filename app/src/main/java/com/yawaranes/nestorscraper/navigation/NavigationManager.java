package com.yawaranes.nestorscraper.navigation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yawaranes.nestorscraper.R;
import com.yawaranes.nestorscraper.base.BaseFragment;
import com.yawaranes.nestorscraper.fragments.ViewFactory;

public class NavigationManager {

    public static final int DEFAULT_CONTENT_FRAME_RES_ID = R.id.container;

    public static void navigateToFragmentCleanStack(FragmentActivity activity, int viewId,
            ViewFactory viewFactory) {
        cleanBackStack(activity);
        navigateToFragmentWithFactory(activity, viewId, viewFactory);
    }

    public static void navigateToFragmentWithFactory(FragmentActivity activity, int viewId,
            ViewFactory viewFactory) {
        navigateToFragmentWithFactory(activity, viewId, viewFactory, null);
    }

    public static void navigateToFragmentWithFactory(FragmentActivity activity, int viewId,
            ViewFactory viewFactory, Bundle bundle) {
        navigateToFragmentWithFactory(activity, viewId, viewFactory, bundle, 0, true);
    }

    public static void navigateToFragmentWithFactory(FragmentActivity activity, int viewId,
            ViewFactory viewFactory, Bundle bundle, int contentFrame, boolean addToBackStack) {
        pushFragment(activity, viewFactory.getFragmentWithViewId(viewId), bundle, contentFrame,
                addToBackStack);
    }

    private static void pushFragment(FragmentActivity activity, BaseFragment fragment,
            Bundle bundle, int contentFrame, boolean addToBackStack) {
        if (fragment == null) {
            return;
        }

        int frameId = (contentFrame > 0 ? contentFrame : DEFAULT_CONTENT_FRAME_RES_ID);

        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameId, fragment, fragment.getViewName()).setBreadCrumbTitle(
                fragment.getViewName());

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getViewName());
        }

        fragmentTransaction.commit();
    }

    public static void navigateBack(FragmentActivity activity) {
        // Pop back stack last element if more than one
        if (getBackStackSize(activity) > 1) {
            activity.getSupportFragmentManager().popBackStackImmediate();
        }
        // If not, closing the Activity as no more previous steps are stored in stack
        else {
            activity.finish();
        }
    }

    private static void cleanBackStack(FragmentActivity context) {
        // Not provided view ID and inclusive by default
        cleanBackStack(context, null, true);
    }

    /**
     * Method to clear back stack of fragments until a certain point. It will remove all
     * fragments until the fragment specified by the parameter
     *
     * @param activity The {@link android.support.v4.app.FragmentActivity} context
     * @param viewStringId String ID of fragment
     * @param isInclusive If the Fragment is included in the popped items or not
     */
    private static void cleanBackStack(FragmentActivity activity, String viewStringId,
            boolean isInclusive) {
        // Cleaning back stack until that view if exists
        int flags = isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0;

        // Trying to find the provided view ID and cleaning back stack until it if found
        if (viewStringId != null && !viewStringId.isEmpty()) {
            activity.getSupportFragmentManager().popBackStackImmediate(viewStringId, flags);
        }
        // Cleaning all back stack as not valid view ID provided
        else {
            activity.getSupportFragmentManager().popBackStackImmediate(null, flags);
        }
    }

    public static int getBackStackSize(FragmentActivity activity) {
        return activity.getSupportFragmentManager().getBackStackEntryCount();
    }
}
