package colorChat.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ChatText {
    static Path textP = Paths.get("text.txt");
    static StringBuilder text;
    static String prevF = "";
    static String prevC = "";


    public static void  getText() {
        try {
            if (Files.notExists(textP)) {

                Files.createFile(textP);
            }
            text=new StringBuilder(Files.readString(textP));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setText(List<Message> text) {

    }

    //  making text string
    public static String put(Message m) {

        if (m.getContent().equals(prevC) && m.getFrom().equals(prevF) || m.getContent().length()==0 || m.getContent().equals(" ")){
            prevF=m.getFrom();
            prevC=m.getContent();
            return rutr();
        }


        try {
            String col=m.getFrom().replace('X','#');
            if (m.isSystem()){
                if (prevF.equals(m.getFrom())) return rutr();

                text = (new StringBuilder("<font size=\"2\"  color=\""+col+"\"> "+col+" is "+ m.getContent() + "</font><br>")).append(text);
            }
            else  {
                text = (new StringBuilder("<font  color=\""+col+"\"> //:"+ m.getContent() + "</font><br>")).append(text);
            }

            Files.deleteIfExists(textP);
            Files.createFile(textP);
            Files.writeString(textP, text, StandardOpenOption.WRITE);



        } catch (IOException e) {
            e.printStackTrace();
        }
        prevF=m.getFrom();
        prevC=m.getContent();
        return rutr();
    }

    static String rutr() {
        if (text.length() > 50000) return (text.substring(0, 50000) + "<b> ... остальное в Архиве.</b>");
        else return text.toString();
    }

   public static String anek (String xxx){



       try {

           text = (new StringBuilder("<br><table style=\"border-collapse: collapse; width: 77%; margin-left: auto; margin-right: auto; border: 0px; height:;\">\n" +
                        "<tbody>\n" +
                        "<tr style=\"height: 22px;\">\n" +
                        "<td style=\"width: 25%; padding: 2px; height: 22px; background-color: #dbf3e8; text-align: left;\"><span style=\"color: #95a5a6;\">\uD83D\uDCA1 Анекдот:</span></td>\n" +
                        "</tr>\n" +
                        "<tr style=\"height:;\">\n" +
                        "<td style=\"width: ; padding: 2px; height: ; background-color: #FFFFFF; text-align: center;\" >" +
                        "<span style=\"color: #95a5a6;\"><strong style=\"color: #626262;\">"
                        +xxx+
                        "</span></strong></td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table><br>")).append(text);

           Files.deleteIfExists(textP);
           Files.createFile(textP);
           Files.writeString(textP, text, StandardOpenOption.WRITE);



       } catch (IOException e) {
           e.printStackTrace();
       }

       return rutr();
   }


}
