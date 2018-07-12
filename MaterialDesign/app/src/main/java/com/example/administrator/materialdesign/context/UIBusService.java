package com.example.administrator.materialdesign.context;

import android.net.Uri;

import com.example.core.listener.CompleteListener;
import com.example.core.router.UIRouter;

public class UIBusService implements UIRouter {
    public int PRIORITY_NORMAL = 0;
    public int PRIORITY_LOW = -1000;
    public int PRIORITY_HEIGHT = 1000;

    private UIRouter uiRouter;

    public void register(UIRouter uiRouter) {
        this.uiRouter = uiRouter;
    }

    public void unregister(UIRouter var1) {
        this.uiRouter = null;
    }

    public boolean openUri(String url, CompleteListener completeListener) {
        if (uiRouter != null) {
            return uiRouter.openUri(url, completeListener);
        }
        return false;
    }

    @Override
    public boolean openUri(String url) {
        return openUri(url, null);
    }

    public boolean openUri(Uri uri, CompleteListener completeListener) {
        if (uiRouter != null) {
            return uiRouter.openUri(uri, completeListener);
        }
        return false;
    }

    public boolean verifyUri(Uri uri) {
        if (uiRouter != null) {
            return uiRouter.verifyUri(uri);
        }
        return false;
    }
}
