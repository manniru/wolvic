/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.igalia.wolvic.ui.widgets.dialogs;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.igalia.wolvic.R;
import com.igalia.wolvic.databinding.PrivacyPolicyDialogBinding;
import com.igalia.wolvic.ui.widgets.WidgetPlacement;
import com.igalia.wolvic.ui.widgets.WindowWidget;

/**
 * A dialog that displays the Privacy Policy along with two buttons to accept or reject it.
 */
public class PrivacyPolicyDialogWidget extends UIDialog {

    public interface Delegate {
        void onUserResponse(boolean response);
    }

    private PrivacyPolicyDialogBinding mBinding;
    private Delegate mPrivacyDialogDelegate;

    public PrivacyPolicyDialogWidget(Context aContext) {
        super(aContext);
        initialize(aContext);
    }

    protected void initialize(Context aContext) {
        updateUI();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateUI();
    }

    public void updateUI() {
        removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.privacy_policy_dialog, this, true);

        mBinding.acceptButton.setOnClickListener(v -> {
            if (mPrivacyDialogDelegate != null) {
                mPrivacyDialogDelegate.onUserResponse(true);
            }
            onDismiss();
        });
        mBinding.declineButton.setOnClickListener(v -> {
            if (mPrivacyDialogDelegate != null) {
                mPrivacyDialogDelegate.onUserResponse(false);
            }
            onDismiss();
        });
    }

    public void setDelegate(Delegate delegate) {
        mPrivacyDialogDelegate = delegate;
    }

    @Override
    public void onWorldClick() {
        // ignored: this is a modal dialog
    }

    @Override
    public void attachToWindow(@NonNull WindowWidget window) {
        mWidgetPlacement.parentHandle = window.getHandle();
    }

    @Override
    protected void initializeWidgetPlacement(WidgetPlacement aPlacement) {
        aPlacement.visible = false;
        aPlacement.width = WidgetPlacement.dpDimension(getContext(), R.dimen.settings_dialog_width);
        aPlacement.height = WidgetPlacement.dpDimension(getContext(), R.dimen.privacy_options_height);
        aPlacement.parentAnchorX = 0.5f;
        aPlacement.parentAnchorY = 0.0f;
        aPlacement.anchorX = 0.5f;
        aPlacement.anchorY = 0.5f;
        aPlacement.translationY = WidgetPlacement.unitFromMeters(getContext(), R.dimen.settings_world_y) -
                WidgetPlacement.unitFromMeters(getContext(), R.dimen.window_world_y);
        aPlacement.translationZ = WidgetPlacement.unitFromMeters(getContext(), R.dimen.settings_world_z) -
                WidgetPlacement.unitFromMeters(getContext(), R.dimen.window_world_z);
    }
}
