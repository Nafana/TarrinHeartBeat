package net.nafana.tarrinheartbeat.enums;


public enum CallbackType {

    SINGULAR,      // This callback only happens once, we'll use it, then forget it!
    COUNTED,       // This callback will happen a given amount of times, for example if we need to simulate a bleed effect.
    RECURRING,     // This callback will happen constantly, every so often a bell will ring!

}
