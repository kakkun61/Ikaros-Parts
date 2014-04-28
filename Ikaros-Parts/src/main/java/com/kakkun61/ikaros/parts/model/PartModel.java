package com.kakkun61.ikaros.parts.model;

public class PartModel {
    public final int rank;
    public final CharSequence name;
    public final CharSequence missionSkill;
    public final CharSequence battleSkill;
    public final Position position;

    public PartModel(int rank, CharSequence name, CharSequence missionSkill, CharSequence battleSkill, Position position) {
        this.rank = rank;
        this.name = name;
        this.missionSkill = missionSkill;
        this.battleSkill = battleSkill;
        this.position = position;
    }

    public enum Position {
        HEAD,
        TAIL,
        TOP,
        BOTTOM,
        SIDE
    }
}
