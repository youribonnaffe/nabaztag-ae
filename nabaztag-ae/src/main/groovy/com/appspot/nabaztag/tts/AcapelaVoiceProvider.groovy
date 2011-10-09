package com.appspot.nabaztag.tts

class AcapelaVoiceProvider implements VoiceProvider {

    List<String> convertTextToMP3(String text) {
        def url = "http://vaassl3.acapela-group.com/Services/Synthesizer"
        HttpURLConnection connection = new URL(url).openConnection()
        connection.setRequestProperty "Host", "vaassl3.acapela-group.com"
        connection.setRequestProperty "Referer", "http://www.acapela-group.com/Flash/Demo_Web_AS3/demo_web.swf?path=http://vaas.acapela-group.com/Services/DemoWeb/&lang=EN"
        connection.setRequestProperty "Content-type", "application/x-www-form-urlencoded"
        connection.setRequestProperty "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
        connection.setRequestProperty "Accept-Language", "fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3"
        connection.setRequestProperty "Accept-Encoding", "gzip,deflate"
        connection.setRequestProperty "Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7"
        connection.setRequestProperty "Keep-Alive", "300"
        connection.setRequestProperty "Connection", "keep-alive"
        connection.setRequestProperty "User-Agent", "Mozilla/5.0 (X11; U; Linux i686; fr; rv:1.9.0.1) Gecko/2008072820 Firefox/3.0.1"

        connection.setRequestMethod("POST")
        connection.setDoOutput(true)

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write("cl%5Fenv=FLASH%5FAS%5F3%2E0&req%5Fsnd%5Fkbps=EXT%5FCBR%5F128&req%5Fasw%5Ftype=INFO&cl%5Fvers=1%2D30&req%5Fsnd%5Ftype=&cl%5Flogin=demo%5Fweb&cl%5Fapp=&req%5Fvoice=julie22k&cl%5Fpwd=demo%5Fweb&prot%5Fvers=2&req%5Ftext=" + URLEncoder.encode(text, "UTF-8"));
        writer.close();

        return [connection.getInputStream().text.tokenize("&").find({ it =~ "snd_url"}).minus("snd_url=")]
    }
}
