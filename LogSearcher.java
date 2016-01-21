/*
*  2. Написать парсер логов, который выполняет следующие действия над логами в п.1:
*   а) С помощью регулярного выражения задать фразу поиска и все найденные строки 
*   записать в новый файл
*   п.2. а) 3 параметра: <фраза_поиска>,<путь_до_лог_файла(ов)>,<имя_нового_файла>
*/

package logsearcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogSearcher {

  public static void main(String[] args) throws UnsupportedEncodingException {
    args = changeDelim(args);
    
    if (args.length < 3) {
      System.out.println("Количество аргументов должно быть больше 3.");
      return;
    }
    
    String regexp = args[0];
    Pattern pattern = Pattern.compile(regexp);
    Matcher matcher = pattern.matcher(""); 
    String outFile = args[args.length - 1];
    
    try {
      PrintWriter out =new PrintWriter(new OutputStreamWriter(
                      new FileOutputStream(outFile), "CP1251"));
      
      for (int i = 1; i < args.length - 1; i++) {
        String inFile = args[i];
        //заходим в файл
        try(BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(inFile), "CP1251"))) {
          for(String line; (line = br.readLine()) != null; ) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
              out.println(line);
            }
          }
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
      out.close();
    } 
    catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage());
    }
    
    System.out.println("Готово.");
  } 
  
  public static String[] changeDelim(String[] args){
    StringBuilder strArgs = new StringBuilder("");
    for (int i = 0; i < args.length; i++) {
      strArgs.append(" " + args[i]);
    }
    String[] newArgs = strArgs.toString().split(",");
    for (int i = 0; i < newArgs.length; i++){
      newArgs[i] = newArgs[i].trim();
    }
    return newArgs;
  }
           
}
