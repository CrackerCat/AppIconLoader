/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zhanghai.android.appiconloader.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

public class CoordinatorScrollingFrameLayout extends FrameLayout
        implements CoordinatorLayout.AttachedBehavior {
    public CoordinatorScrollingFrameLayout(@NonNull Context context) {
        super(context);
    }

    public CoordinatorScrollingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatorScrollingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                           @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CoordinatorScrollingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                           @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @NonNull
    @Override
    public WindowInsets dispatchApplyWindowInsets(@NonNull WindowInsets insets) {
        insets = new WindowInsetsCompat.Builder(WindowInsetsCompat.toWindowInsetsCompat(insets))
                .setSystemWindowInsets(Insets.of(insets.getSystemWindowInsetLeft(), 0,
                        insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom()))
                .build()
                .toWindowInsets();
        super.dispatchApplyWindowInsets(insets);
        return insets;
    }

    @NonNull
    @Override
    public WindowInsets onApplyWindowInsets(@NonNull WindowInsets insets) {
        return insets;
    }

    @NonNull
    @Override
    public CoordinatorLayout.Behavior getBehavior() {
        return new Behavior();
    }

    private static class Behavior extends AppBarLayout.ScrollingViewBehavior {
        @Override
        public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child,
                                      int parentWidthMeasureSpec, int widthUsed,
                                      int parentHeightMeasureSpec, int heightUsed) {
            @SuppressLint("RestrictedApi")
            WindowInsetsCompat parentInsets = parent.getLastWindowInsets();
            if (parentInsets != null) {
                int parentHeightSize = View.MeasureSpec.getSize(parentHeightMeasureSpec);
                parentHeightSize -= parentInsets.getSystemWindowInsetTop()
                        + parentInsets.getSystemWindowInsetBottom();
                int parentHeightMode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
                parentHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(parentHeightSize,
                        parentHeightMode);
            }
            return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed,
                    parentHeightMeasureSpec, heightUsed);
        }
    }
}
