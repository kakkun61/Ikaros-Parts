package com.kakkun61.ikaros.parts.model;

public class DatabaseInfo {
    private DatabaseInfo() {}

    public static class Parts {
        private Parts() {}
        public static final String name = "Items";
        public static class Columns {
            private Columns() {}
            public static final String position = "Type";
            public static final String rank = "Rank";
            public static final String id = "ID";
            public static final String name = "ItemName";
            public static final String missionSkill = "MSkill";
            public static final String battleSkill = "BSkill";
            public static final String trigger = "SkillNeed";
        }
    }

    public static class Recipe {
        private Recipe() {}
        public static final String name = "Recipe";
        public static class Columns {
            private Columns() {}
            public static final String productPosition = "CraftType";
            public static final String productId = "CraftID";
            public static final String productName = "CraftItem";
            public static final String level = "Lv";
            public static final String materialId = "MaterialID";
            public static final String materialName = "MaterialName";
            public static final String materialNumber = "MaterialNum";
        }
    }
}
