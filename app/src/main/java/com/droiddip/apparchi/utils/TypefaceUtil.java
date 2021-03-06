/**
 * Copyright 2017 Dipanjan Chakraborty (chakraborty.dipanjan07@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.music.arts.utils;


import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * TypefaceUtil
 *
 * @author Dipanjan Chakraborty
 */

public class TypefaceUtil {

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context context, String fontPath) {
        synchronized (cache) {
            if (!cache.containsKey(fontPath)) {

                Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath);

                cache.put(fontPath, typeface);
            }
            return cache.get(fontPath);
        }
    }
}
