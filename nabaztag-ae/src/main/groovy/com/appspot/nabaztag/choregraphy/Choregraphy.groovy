package com.appspot.nabaztag.choregraphy

import groovy.transform.ToString

@ToString
class Choregraphy {

    enum Colors {
        NONE([0, 0, 0]), RED([0xFF, 0, 0]), GREEN([0, 0xFF, 0]), BLUE([0, 0, 0xFF])

        Colors(bytes) { this.bytes = bytes}

        private final def bytes

    }

    enum Actions {
        LED(0x7), LEDS(0x9), EAR(0x8)

        Actions(code) { this.code = code}

        private final byte code
    }

    enum Leds {
        RIGHT(0x1), MIDDLE(0x2), LEFT(0x3), NOSE(0x4)

        Leds(code) { this.code = code}

        private final byte code
    }

    enum Direction {
        FORWARD, BACKWARD
    }

    def tempo
    def header = [0, 0, 0, 0, 0x0, 0x01, 0x0]
    def footer = [0x02, 0x0A, 0x04]
    def packet = []

    def led(step, Leds led, Colors color) {
        packet += [step, Actions.LED.code]
        packet += [led.code]
        packet += color.bytes
        packet += [0, 0]
    }

    def leds(step, Colors color) {
        packet += [step, Actions.LEDS.code]
        packet += color.bytes
    }

    def leds(step, def bytes) {
        packet += [step, Actions.LEDS.code]
        packet += bytes
    }

    def send(response) {
        def fullPacket = header + packet + footer
        fullPacket[3] = header.size() + packet.size()
        fullPacket[6] = tempo

        def bytePacket = fullPacket as byte[]
        response.contentLength = bytePacket.length
        response.outputStream.write bytePacket
        response.status = 200

    }

    private def buildPacket() {
        def fullPacket = [0x7F]
        def pingIntervalPacket = [PING_INTERVAL, 0x0, 0x0, 0x1, interval]

        fullPacket += pingIntervalPacket

        def colorsPacket = [EARS_COLORS, 0x0, 0x0, 0x1C, 0x7F, 0xFF, 0xFF, 0xFF]

        colorsPacket += [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ears.left, ears.right, 0, leds.left.ordinal(), leds.middle.ordinal(), leds.right.ordinal(), 0, 0]

        fullPacket += colorsPacket

        if (hasToReboot)
            fullPacket = [0x7F, REBOOT, 0x0, 0x0, 0x1, 0x7F, 0xFF, 0xFF, 0xFF, 0xFF, 0xA]

        if (choregraphy) {
            def chorCommand = "ID " + 139 + "\n" + "CH " + "broadcast/" + choregraphy + "\n"
            def data = encode(chorCommand)
            fullPacket += [0xA, 0x0, 0x0, data.size()] + data
        }

        fullPacket += [0xFF, 0xA]
        return fullPacket
    }


}
