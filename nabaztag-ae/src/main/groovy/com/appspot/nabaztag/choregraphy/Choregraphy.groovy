package com.appspot.nabaztag.choregraphy

import groovy.transform.ToString

@ToString
class Choregraphy {

    enum Colors {
        NONE([0, 0, 0]), RED([0xFF, 0, 0]), GREEN([0, 0xFF, 0]), BLUE([0, 0, 0xFF]),
        GREY([192, 192, 192]), LIGHT_GREEN([173, 255, 47]), ORANGE([255, 165, 0]);

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

    def led(step, Leds led, def bytes) {
        packet += [step, Actions.LED.code]
        packet += [led.code]
        packet += bytes
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

}
