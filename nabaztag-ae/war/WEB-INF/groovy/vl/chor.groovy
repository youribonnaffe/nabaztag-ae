package vl

import com.appspot.nabaztag.choregraphy.Choregraphy
import com.appspot.nabaztag.choregraphy.Choregraphy.Colors
import static com.appspot.nabaztag.choregraphy.Choregraphy.Colors.*
import static com.appspot.nabaztag.choregraphy.Choregraphy.Leds.*

log.info "chor, params=" + params

new Choregraphy().with {
    switch (params.type) {
        case 'ambient':
            it.tempo = 0xF
            fade(it)
            break;
        case 'christmas':
            it.tempo = 0xFD
            def random = new Random(System.currentTimeMillis())
            (0..100).each {range ->
                it.led 0, MIDDLE, randomColor(random)
                it.led 0, LEFT, randomColor(random)
                it.led 0, RIGHT, randomColor(random)
                it.led 0, NOSE, randomColor(random)

                it.led 1, LEFT, randomColor(random)
                it.led 0, RIGHT, randomColor(random)
                it.led 0, MIDDLE, randomColor(random)
                it.led 0, NOSE, randomColor(random)
            }
            break;
    }

/*
    leftRightAndMiddle it
    middleAndLeftRight it

    it.leds 1, NONE

    leftToRight it, GREEN
    rightToLeft it
*/
    it.send response
}

private randomColor(Random random) {
    return [random.nextInt(255), random.nextInt(255), random.nextInt(255)]
}

private def leftRightAndMiddle(Choregraphy it, Colors color) {
    it.led 0, LEFT, color
    it.led 0, RIGHT, color
    it.led 1, MIDDLE, color
    it.led 0, LEFT, NONE
    it.led 0, RIGHT, NONE
}

private def middleAndLeftRight(Choregraphy it, Colors color) {
    it.led 0, MIDDLE, color
    it.led 0, LEFT, NONE
    it.led 0, RIGHT, NONE

    it.led 1, LEFT, color
    it.led 0, RIGHT, color
    it.led 0, MIDDLE, NONE

    it.led 1, MIDDLE, color
    it.led 0, LEFT, NONE
    it.led 0, RIGHT, NONE
}


private def rightToLeft(Choregraphy it, Colors color) {
    it.led 0, RIGHT, color

    it.led 1, MIDDLE, color

    it.led 1, RIGHT, NONE
    it.led 0, LEFT, color

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