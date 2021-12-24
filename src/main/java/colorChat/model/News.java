package colorChat.model;

import colorChat.websocket.ChatEndpoint;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class News {
    private static News instance;
   static XMLStreamReader xmlr;
   static int i = 0;


    private static void init(){
        try {
        String URL_NEWS = "https://www.anekdot.ru/rss/export_top.xml";


        String fileName = "rss.xml";

        InputStream in = new URL(URL_NEWS).openStream();

             xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileName, in);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void make() {
        // Получение фабрики, чтобы после получить билдер документов.
        try {
            // Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);


            String value = "";

            boolean itm = false;


            while (xmlr.hasNext()) {
                xmlr.next();


                //// START TAG  - item
                if (xmlr.isStartElement() && xmlr.getLocalName().equals("item")) {

                    itm = true;


                }
                //// START TAG  -not item
                if (xmlr.isStartElement() && xmlr.getLocalName().equals("description") && itm) {
                    //  System.out.println(xmlr.getLocalName());

                    value = xmlr.getElementText();
                    System.out.println(value + ";))");

                    ChatEndpoint.sysMes(value);
                    i++;
                    break;


                    // ChatEndpoint.sysMes(value);
                } else if (xmlr.isEndElement() && itm && !xmlr.getLocalName().equals("item")) {
                    // System.out.println("/" + xmlr.getLocalName() +"\n ***   param="+param+"/value="+value+";   i="+i);

                } else if (xmlr.isEndElement() && xmlr.getLocalName().equals("item")) {
                    // System.out.println("/" + xmlr.getLocalName());

                    itm = false;


                }
            }
            if (!xmlr.hasNext())init();

        } catch (Exception e) {
            System.out.println(" ERROR");
            e.printStackTrace();
        }


    }

    public static void start() {
    init();

        System.out.println("timer");
        java.util.Timer timer = new java.util.Timer("TimeR");
        try {
            TimerTask time = new TimerTask() {
                public void run() {
                    make();
                }
            };


            timer.schedule(time, 2000, 15 * 60 * 1000l);

        } catch (Exception e) {
            System.out.println("TIMER ERROR");
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        start();
    }

}


