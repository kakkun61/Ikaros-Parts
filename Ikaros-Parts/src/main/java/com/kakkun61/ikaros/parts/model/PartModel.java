package com.kakkun61.ikaros.parts.model;

import android.database.Cursor;

import com.kakkun61.ikaros.parts.model.DatabaseInfo.Parts;

import java.io.Serializable;

public class PartModel implements Serializable {
    public final int rank;
    public final CharSequence name;
    public final CharSequence missionSkill;
    public final CharSequence battleSkill;
    public final Position position;
    public final CharSequence trigger;

    public PartModel(CharSequence name, int rank, CharSequence missionSkill, CharSequence battleSkill, Position position, CharSequence trigger) {
        this.name = name;
        this.rank = rank;
        this.missionSkill = missionSkill;
        this.battleSkill = battleSkill;
        this.position = position;
        this.trigger = trigger;
    }

    public static enum Position {
        HEAD,
        TAIL,
        TOP,
        BOTTOM,
        SIDE;

        public static Position fromInt(final int position) {
            switch (position) {
                case 1:
                    return HEAD;
                case 2:
                    return TOP;
                case 3:
                    return BOTTOM;
                case 4:
                    return TAIL;
                case 5:
                    return SIDE;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public static PartModel[] convert(final Cursor cursor) {
        final int count = cursor.getCount();
        final PartModel[] parts= new PartModel[count];
        cursor.moveToFirst();
        for (int i = 0; i < count; i++) {
            parts[i] = new PartModel(
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.name)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.rank)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.missionSkill)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.battleSkill)),
                    Position.fromInt(cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.rank))),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.trigger))
            );
            cursor.moveToNext();
        }
        return parts;
    }
}
