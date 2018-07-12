package com.example.administrator.materialdesign.context;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.materialdesign.activity.MainActivity;
import com.example.core.context.AppContext;
import com.example.core.device.Repository;
import com.example.core.listener.CompleteListener;
import com.example.core.model.User;
import com.example.core.router.UIRouter;
import com.example.core.utils.StringUtils;
import com.example.core.utils.Tools;

import java.util.HashMap;

public class HostUIRouter implements UIRouter {


    @Override
    public boolean openUri(String url, CompleteListener completeListener) {
        return false;
    }

    @Override
    public boolean openUri(String url) {
        return false;
    }

    @Override
    public boolean openUri(Uri url, CompleteListener completeListener) {
        return false;
    }

    @Override
    public boolean verifyUri(Uri url) {
        return false;
    }
}

