package com.kakkun61.ikaros.parts.model;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import com.kakkun61.ikaros.parts.db.IkarosMasterDatabase;
import com.kakkun61.ikaros.parts.model.DatabaseInfo.Parts;
import com.kakkun61.ikaros.parts.model.DatabaseInfo.Recipe;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PartModel implements Serializable {
    public final int id;
    public final int rank;
    public final CharSequence name;
    public final CharSequence missionSkill;
    public final CharSequence battleSkill;
    public final Position position;
    public final CharSequence trigger;
    public PartModel[] materials;

    private PartModel(int id, CharSequence name, int rank, CharSequence missionSkill, CharSequence battleSkill, Position position, CharSequence trigger, PartModel[] materials) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.missionSkill = missionSkill;
        this.battleSkill = battleSkill;
        this.position = position;
        this.trigger = trigger;
        this.materials = materials;
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

    private static PartModel[] convert(final Cursor cursor, final Context context) throws IOException {
        // TODO まずは配列をマップに変える
        final int partsNumber = cursor.getCount();
        final Map<Id, PartModel> parts = new HashMap<>(partsNumber);
//        final SQLiteDatabase db = IkarosMasterDatabase.getReadableSQLiteDatabase(context);
        cursor.moveToFirst();
        for (int i = 0; i < partsNumber; i++) {
            final int partId = cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.id));

//            final Cursor materialCursor = db.query(Recipe.name, null, Recipe.Columns.productId + "=?", new String[]{Integer.toString(partId)}, null, null, null);
//            final PartModel[] materials = new PartModel[materialCursor.getCount()];
//            for (int j = 0; j < materials.length; j++) {
//                // TODO ここで再帰的にパーツモデルを作ると重複してインスタインスを作ってしまうので、レシピのない状態のモデルをマップに格納
//            }

            final int rank = cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.rank));
            parts.put(new Id(rank, partId), new PartModel(
                    partId,
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.name)),
                    rank,
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.missionSkill)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.battleSkill)),
                    Position.fromInt(cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.rank))),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.trigger)),
                    null//materials
            ));
            cursor.moveToNext();
        }
        final PartModel[] partsArray = new PartModel[partsNumber];
        return parts.values().toArray(partsArray);
    }

    public static PartModel[] getAll(final Context context) throws IOException {
        final SQLiteDatabase db = IkarosMasterDatabase.getReadableSQLiteDatabase(context);
        final Cursor items = db.query(Parts.name, null, null, null, null, null, null, null);
        return PartModel.convert(items, context);
    }

    private static class Id {
        private final int rank;
        private final int id;

        private Id(int rank, int id) {
            this.rank = rank;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id1 = (Id) o;

            if (id != id1.id) return false;
            if (rank != id1.rank) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = rank;
            result = 31 * result + id;
            return result;
        }
    }
}
