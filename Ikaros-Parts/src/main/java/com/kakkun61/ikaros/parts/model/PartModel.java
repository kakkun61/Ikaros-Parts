package com.kakkun61.ikaros.parts.model;

import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.kakkun61.ikaros.parts.db.IkarosMasterDatabase;
import com.kakkun61.ikaros.parts.model.DatabaseInfo.Parts;
import com.kakkun61.ikaros.parts.model.DatabaseInfo.Recipe;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
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
        final int partsCount = cursor.getCount();
        final Map<String, PartModel> parts = new HashMap<>(partsCount);
//        final SQLiteDatabase db = IkarosMasterDatabase.getReadableSQLiteDatabase(context);
        cursor.moveToFirst();
        for (int i = 0; i < partsCount; i++) {
//            final Cursor materialCursor = db.query(Recipe.name, null, Recipe.Columns.productId + "=?", new String[]{Integer.toString(partId)}, null, null, null);
//            final PartModel[] materials = new PartModel[materialCursor.getCount()];
//            for (int j = 0; j < materials.length; j++) {
//                // TODO ここで再帰的にパーツモデルを作ると重複してインスタインスを作ってしまうので、レシピのない状態のモデルをマップに格納
//            }

            final String name = cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.name));
            parts.put(name, new PartModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.id)),
                    name,
                    cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.rank)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.missionSkill)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.battleSkill)),
                    Position.fromInt(cursor.getInt(cursor.getColumnIndexOrThrow(Parts.Columns.rank))),
                    cursor.getString(cursor.getColumnIndexOrThrow(Parts.Columns.trigger)),
                    null//materials
            ));
            cursor.moveToNext();
//            Log.d("ikaros", "cursor.moveToNext: " + cursor.moveToNext());
        }
        // FIXME: partsArray の要素1つが null になるバグ、Items テーブルの ItemName カラムが1つダブってたからだった
        final PartModel[] partsArray = new PartModel[partsCount];
        parts.values().toArray(partsArray);
        Arrays.sort(partsArray, new Comparator<PartModel>() {
            @Override
            public int compare(PartModel lhs, PartModel rhs) {
                if (lhs == null && rhs == null) {
                    return 0;
                }
                if (lhs == null) {
                    return -1;
                }
                if (rhs == null) {
                    return 1;
                }
                return lhs.name.toString().compareTo(rhs.name.toString());
            }
        });
        for (int i = 0; i < partsCount; i++) {
            final PartModel m = partsArray[i];
            Log.d("ikaros", i + "/" + partsCount + ": " + (m == null? "null": m.name.toString()));
        }
        System.exit(0);
        return partsArray;
    }

    public static PartModel[] getAll(final Context context) throws IOException {
        final SQLiteDatabase db = IkarosMasterDatabase.getReadableSQLiteDatabase(context);
        final Cursor items = db.rawQuery("SELECT * from " + Parts.name + " WHERE " + Parts.Columns.name + " IS NOT NULL", null);
//        final Cursor items = db.query(Parts.name, null, Parts.Columns.name + " IS NOT NULL", null, null, null, null, null);
        return PartModel.convert(items, context);
    }

    @Override
    public String toString() {
        return "PartModel{" +
                "id=" + id +
                ", rank=" + rank +
                ", name=" + name +
                ", missionSkill=" + missionSkill +
                ", battleSkill=" + battleSkill +
                ", position=" + position +
                ", trigger=" + trigger +
                ", materials[" + (materials == null? "null": materials.length) + "]" +
                '}';
    }
}
