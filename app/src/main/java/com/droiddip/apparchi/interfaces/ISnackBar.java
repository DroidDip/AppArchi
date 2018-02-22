package com.droiddip.apparchi.interfaces;

import android.graphics.Typeface;

/**
 * Created by Dipanjan Chakraborty on 21-02-2018.
 */

public interface ISnackBar {

    enum SnackbarStyle {
        NORMAL(Typeface.NORMAL),
        BOLD(Typeface.BOLD),
        ITALIC(Typeface.ITALIC),
        BOLD_ITALIC(Typeface.BOLD_ITALIC);

        private final int value;

        private SnackbarStyle(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    enum SnackbarPosition {
        BOTTOM, TOP
    }
}
