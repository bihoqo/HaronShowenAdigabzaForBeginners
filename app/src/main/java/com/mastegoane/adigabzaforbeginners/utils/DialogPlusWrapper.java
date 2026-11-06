package com.mastegoane.adigabzaforbeginners.utils;

import android.content.Context;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;

public class DialogPlusWrapper {
    static public DialogPlusBuilder getDialogPlusBuilder(Context context, int gravity, boolean cancelable, int holderResourceId, int contentBackgroundResourceId, int overlayBackgroundResourceId, int headerResourceId, int footerResourceId, int height, int defaultContentHeight) {
        final Holder holder = new ViewHolder(holderResourceId);
        final DialogPlusAdapter adapter = new DialogPlusAdapter(context);
        final DialogPlusBuilder builder = DialogPlus.newDialog(context)
                .setContentHolder(holder)
                .setCancelable(cancelable)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setContentBackgroundResource(contentBackgroundResourceId)
                .setContentHeight(height)
                .setOverlayBackgroundResource(overlayBackgroundResourceId);
        if (headerResourceId != 0) {
            builder.setHeader(headerResourceId);
        }
        if (footerResourceId != 0) {
            builder.setFooter(footerResourceId);
        }
        if (defaultContentHeight > 0) {
            builder.setExpanded(true, defaultContentHeight);
        } else {
            builder.setExpanded(true);
        }
        return builder;
    }
}
