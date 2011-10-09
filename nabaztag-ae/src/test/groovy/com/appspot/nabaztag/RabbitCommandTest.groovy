package com.appspot.nabaztag;

import com.appspot.nabaztag.tts.VoiceProvider
import org.junit.Test
import static org.junit.Assert.assertNotNull

class RabbitCommandTest {

    @Test
    public void testSayText() {
        RabbitCommand.voiceProvider = new VoiceProvider() {
            List<String> convertTextToMP3(String text) {
                return ["test"]
            }
        }
        def packet = RabbitCommand.say "hello"
        assertNotNull packet
    }

}
