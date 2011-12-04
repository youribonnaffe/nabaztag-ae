package vl

import com.appspot.nabaztag.Mem
import com.appspot.nabaztag.Rabbit
import static com.appspot.nabaztag.Rabbit.Colors.*

log.info params.toString()

new Rabbit(params: params).with {
    if(doubleClicked()){
        reboot()
    }

    if (is7AM()) {
        play = "http://95.81.147.3/franceinter/all/franceinter-32k.mp3"
    } else if (isHalfHour()) {
        play = "http://nabaztag-cdn.appspot.com/short/ding.mp3"
    } else if (isTheHour()) {
        play = "http://nabaztag-cdn.appspot.com/surprise/" + (new Random(System.currentTimeMillis()).nextInt(298) + 1) + ".mp3"
    }
    send response
}


boolean isTheHour() {
    def now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
    def currentMinute = now.get(Calendar.MINUTE)
    if (currentMinute != Mem.lastMsg && currentMinute % Mem.surprisePeriodInMin == 0 && currentMinute == 0) {
        Mem.lastMsg = now.get(Calendar.MINUTE)
        return true
    }
    return false
}

boolean isHalfHour() {
    def now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
    def currentMinute = now.get(Calendar.MINUTE)
    if (currentMinute != Mem.lastMsg && currentMinute % Mem.surprisePeriodInMin == 0) {
        Mem.lastMsg = now.get(Calendar.MINUTE)
        return true
    }
    return false
}

boolean is7AM() {
    def now = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
    def currentMinute = now.get(Calendar.MINUTE)
    def currentHour = now.get(Calendar.HOUR_OF_DAY)
    currentHour == 7 && currentMinute == 5
}

