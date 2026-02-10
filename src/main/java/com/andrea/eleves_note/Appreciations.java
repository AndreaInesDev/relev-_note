package com.andrea.eleves_note;

public enum Appreciations {
    FAIBLE, PASSABLE, ASSEZ_BIEN, BIEN, TRES_BIEN, PARFAIT;

    public static Appreciations getFromNote(double note) {
        if (note < 0 || note > 20) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 20. Valeur reçue : " + note);
        }
        if (note < 10) return FAIBLE;
        if (note < 12) return PASSABLE;
        if (note < 14) return ASSEZ_BIEN;
        if (note < 16) return BIEN;
        if (note < 18) return TRES_BIEN;
        return PARFAIT;
    }
}
