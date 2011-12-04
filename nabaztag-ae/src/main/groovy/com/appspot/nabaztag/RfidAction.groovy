package com.appspot.nabaztag

enum RfidAction {
    do_nothing("Do nothing"),
    tell_me_something("Tell me something"),
    ginko("Ginko"),
    play_music("Play music"),
    christmas("Christmas tree");

    private final String label;

    public RfidAction(String label) {
        this.label = label
    }

    public String label() {
        return label
    }
}
