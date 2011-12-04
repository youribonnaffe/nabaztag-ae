package com.appspot.nabaztag

import org.htmlcleaner.HtmlCleaner
import org.htmlcleaner.SimpleXmlSerializer
import java.net.URLEncoder

class Ginko {

    public static String readTimeTable(String stop) {
        String htmlTimeTable = new URL("http://www.ginkotempo.com/TempoMobile/tempoMobile.do"
            + "?choix_station=" + URLEncoder.encode(stop, "UTF-8") + "&methode=afficheStation").text

        HtmlCleaner cleaner = new HtmlCleaner();
        def node = cleaner.clean(htmlTimeTable)
        // Convert from HTML to XML
        def props = cleaner.getProperties()
        def serializer = new SimpleXmlSerializer(props)
        def xml = serializer.getXmlAsString(node)

        // Parse the XML into a document we can work with
        def page = new XmlSlurper(false, false).parseText(xml)

        def timeTable = "Station ${stop} "
        page.body.depthFirst().
            grep { it.name() == "tr"}.
            grep { it.name() == "tr"}.
            each {
                def next = textFromFirstChild(it.td[1].a)
                def nextNext = textFromFirstChild(it.td[2].a)
                if (next && nextNext) {
                    timeTable += "  " + it.td[0].div[0].text() + " " + it.td[0].div[1].text()
                    timeTable += " " + next
                    timeTable += " " + nextNext
                }
            }
        return timeTable.replaceAll("min", "minutes")
    }

    private static String textFromFirstChild(def parent) {
        String all = parent.text()
        StringBuilder subtract = new StringBuilder()
        parent.children().each { subtract.append(it.text()) }
        return all.substring(0, all.size() - subtract.toString().size())
    }

}
