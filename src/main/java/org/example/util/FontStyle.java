package org.example.util;

import jxl.write.WritableFont;

public enum FontStyle {

    NORMAL_BOLD(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)),
    NORMAL_NOBOLD(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD));

    public final WritableFont font;

    FontStyle(WritableFont font) {
        this.font = font;
    }

}
