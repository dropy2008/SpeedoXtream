/*
 * Copyright 2017 Isaac Pateau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zaclimon.xipl.ui.components.cardview;

import android.widget.ImageView;

/**
 * Interface that loads the image to an ImageCardView.
 *
 * @author zaclimon
 * Creation date: 07/07/17
 */

public interface CardViewImageProcessor {

    /**
     * Loads an image from a location to a given ImageView
     *
     * @param url            the link to the image.
     * @param cardViewWidth  the width of the image region of CardView. The unit is in pixels.
     * @param cardViewHeight the height of the image region of the CardView. The unit is in pixels.
     * @param imageView      the ImageView in which the image will be loaded.
     */
    void loadImage(String url, int cardViewWidth, int cardViewHeight, ImageView imageView);
}