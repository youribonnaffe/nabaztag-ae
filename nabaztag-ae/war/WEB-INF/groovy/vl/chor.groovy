package vl

import com.appspot.nabaztag.choregraphy.Choregraphy
import static com.appspot.nabaztag.choregraphy.Choregraphy.Colors.*
import com.appspot.nabaztag.choregraphy.Choregraphy.Colors

log.info "chor, params=" + params

new Choregraphy().with {
    it.tempo = 0x1
/*
    leftRightAndMiddle it
    middleAndLeftRight it

    it.leds 1, NONE

    leftToRight it, GREEN
    rightToLeft it
*/

    fade(it)
    it.send response
}

private def leftRightAndMiddle(Choregraphy it) {
    it.led 0, LEFT, RED
    it.led 0, RIGHT, RED
    it.led 1, MIDDLE, RED
    it.led 0, LEFT, NONE
    it.led 0, RIGHT, NONE
}

private def middleAndLeftRight(Choregraphy it) {
    it.led 0, MIDDLE, RED
    it.led 0, LEFT, NONE
    it.led 0, RIGHT, NONE

    it.led 1, LEFT, RED
    it.led 0, RIGHT, RED
    it.led 0, MIDDLE, NONE

    it.led 1, MIDDLE, RED
    it.led 0, LEFT, NONE
    it.led 0, RIGHT, NONE
}


private def rightToLeft(Choregraphy it) {
    it.led 0, RIGHT, RED

    it.led 1, MIDDLE, RED

    it.led 1, RIGHT, NONE
    it.led 0, LEFT, RED

    it.led 1, MIDDLE, NONE

    it.led 1, LEFT, NONE
}

private def leftToRight(Choregraphy it, Colors color) {
    it.led 0, LEFT, color

    it.led 1, MIDDLE, color

    it.led 1, LEFT, NONE
    it.led 0, RIGHT, color

    it.led 1, MIDDLE, NONE

    it.led 1, RIGHT, NONE
}

private def fade(Choregraphy it) {
    it.leds 0, RED
    for (j in (0..10)) {
        for (int i = 0; i < 255; i += 10) {
            it.leds 1, [0xFF - i, i, 0x0]
        }
        for (int i = 0; i < 255; i += 10) {
            it.leds 1, [0, 0xFF - i, i]
        }
        for (int i = 0; i < 255; i += 10) {
            it.leds 1, [i, 0, 0xFF - i]
        }
    }
}