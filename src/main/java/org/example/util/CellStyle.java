package org.example.util;

import jxl.format.*;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public enum CellStyle {

    BLANK_STYLE(new CellStyle.Builder()
            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
            .setBorder(Border.ALL, BorderLineStyle.THIN)
            .setFont(FontStyle.NORMAL_BOLD.font)
            .setBack(Colour.WHITE)
            .setWrap(true)),

    NAMES_STYLE(new CellStyle.Builder()
            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
            .setBorder(Border.ALL, BorderLineStyle.THIN)
            .setFont(FontStyle.NORMAL_BOLD.font)
            .setBack(Colour.GRAY_25)
            .setWrap(true)),

    DATE_STYLE(new CellStyle.Builder()
            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
            .setBorder(Border.ALL, BorderLineStyle.THIN)
            .setFont(FontStyle.NORMAL_BOLD.font)
            .setBack(Colour.LIGHT_TURQUOISE)
            .setWrap(true)),


    ABSENT_STYLE(new CellStyle.Builder()
            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
            .setBorder(Border.ALL, BorderLineStyle.THIN)
            .setFont(FontStyle.NORMAL_NOBOLD.font)
            .setBack(Colour.RED)
            .setWrap(true)),

    REWORKED_STYLE(new CellStyle.Builder()
            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
            .setBorder(Border.ALL, BorderLineStyle.THIN)
            .setFont(FontStyle.NORMAL_NOBOLD.font)
            .setBack(Colour.ORANGE)
            .setWrap(true)),

    CAME_STYLE(new CellStyle.Builder()
            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
            .setBorder(Border.ALL, BorderLineStyle.THIN)
            .setFont(FontStyle.NORMAL_NOBOLD.font)
            .setBack(Colour.GREEN)
            .setWrap(true));

    static class Builder {
        private WritableCellFormat style;

        public Builder() {
            style = new WritableCellFormat();
        }

        public Builder setAlignment(Alignment ha, VerticalAlignment va) {
            try {
                style.setAlignment(ha);
                style.setVerticalAlignment(va);
            } catch (WriteException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder setFont(WritableFont f) {
            style.setFont(f);
            return this;
        }

        public Builder setBorder(Border b, BorderLineStyle bls) {
            try {
                style.setBorder(b, bls);
            } catch (WriteException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder setWrap(boolean wrap) {
            try {
                style.setWrap(wrap);
            } catch (WriteException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public Builder setBack(Colour c) {
            try {
                style.setBackground(c);
            } catch (WriteException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public WritableCellFormat build() {
            return style;
        }

    }

    public WritableCellFormat format;

    CellStyle(CellStyle.Builder builder) {
        this.format = builder.build();
    }

}
