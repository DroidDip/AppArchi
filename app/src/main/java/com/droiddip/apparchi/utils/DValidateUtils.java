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

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * DValidateUtils
 *
 * @author Dipanjan Chakraborty
 */

public class DValidateUtils {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private DValidateUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * To check whether in email is valid or not
     *
     * @param target
     * @return
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * To check whether the phone number is valid or not
     *
     * @param phone
     * @return
     */
    public final static boolean isValidPhone(String phone) {

        int zeroCount = 0;
        for (int i = 0; i < phone.length(); i++) {
            if (phone.charAt(i) == '0') {
                zeroCount++;
            }
        }
        if (phone == null || phone.length() < 10 || zeroCount > 8 || phone.indexOf("0") == 0) {
            return false;
        } else {
            phone = phone.trim();
            return Patterns.PHONE.matcher(phone).matches();
        }
    }

    public static final boolean isOnlyDigit(String text){
        if (text.isEmpty()){
            return false;
        } else {
            Pattern p = Pattern.compile("^[0-9].*");
            return p.matcher(text).matches();
        }
    }
}
