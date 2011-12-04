package com.appspot.nabaztag

import com.appspot.nabaztag.tts.AcapelaVoiceProvider
import com.appspot.nabaztag.tts.VoiceProvider
import groovy.transform.ToString
import static com.appspot.nabaztag.MessageEncoder.encode

@ToString
class Rabbit {

    private static final DOUBLE_CLICK = '1'
    private static final SINGLE_CLICK = '3'

    private static final byte REBOOT = 0x9
    private static final byte PING_INTERVAL = 0x3
    private static final byte EARS_COLORS = 0x4

    private static VoiceProvider voiceProvider = new AcapelaVoiceProvider()

    enum Colors {
        NONE, RED, GREEN, YELLOW
    }

    private def interval = 0
    private def hasToReboot = false

    def ears = ["left": 0, "right": 0]
    def leds = ["left": Colors.NONE, "middle": Colors.NONE, "right": Colors.NONE, "nose": Colors.NONE, "bottom": Colors.NONE]

    def choregraphy
    def choregraphyPlay
    def say
    def play

    def params

    def setLeds(color) {
        leds.each { it.value = color }
    }

    def doubleClicked() {
        return params.sd == DOUBLE_CLICK
    }

    def clicked() {
        return params.sd == SINGLE_CLICK
    }

    def reboot() {
        hasToReboot = true
    }

    def send(response) {
        def fullPacket = buildPacket()

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
            def chorCommand = "ID " + 138 + "\n"
            if (choregraphyPlay) {
                chorCommand += "MS " + choregraphyPlay + "\n"
            }
            chorCommand += "CH " + "broadcast/" + choregraphy + "\n"
            def data = encode(chorCommand)
            fullPacket += [0xA, 0x0, 0x0, data.size()] + data
        }

        if (say) {
            def command = "ID " + 138 + "\n"
            voiceProvider.convertTextToMP3(say).each {
                command += "MS " + it + "\n"
            }
            def data = encode(command)
            fullPacket += [0xA, 0x0, 0x0, data.size()] + data
        }

        if (play) {
            def command = "ID " + 138 + "\n" + "MS " + play + "\n"
            def data = encode(command)
            fullPacket += [0xA, 0x0, 0x0, data.size()] + data
        }

        fullPacket += [0xFF, 0xA]
        return fullPacket
    }

}
