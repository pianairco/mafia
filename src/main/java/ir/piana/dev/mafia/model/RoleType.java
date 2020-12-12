package ir.piana.dev.mafia.model;

public enum RoleType {
    Detective("RoleType.Detective", true),
    Doctor("RoleType.Doctor", true),
    Invulnerable("RoleType.Invulnerable", true),
    Terrorist("RoleType.Terrorist", false),
    Natasha("RoleType.Natasha", false),
    Cleric("RoleType.Cleric", true),
    Negotiator("RoleType.Negotiator", false),
    Sniper("RoleType.Sniper", true),
    Butler("RoleType.Butler", true),
    Thief("RoleType.Thief", false),
    Traitor("RoleType.Traitor", false),
    Bodyguard("RoleType.Bodyguard", false);

    private String key;
    private boolean isInnocents;

    RoleType(String key, boolean isInnocents) {
        this.key = key;
        this.isInnocents = isInnocents;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isInnocents() {
        return isInnocents;
    }

    public void setInnocents(boolean innocents) {
        isInnocents = innocents;
    }
}
