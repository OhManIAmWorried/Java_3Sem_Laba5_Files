import java.util.Date;
import java.util.Scanner;

/**
 * Created by Oly on 23.10.2016.
 */

/*
Контрольные вопросы

Приведите код, записывающий в текстовый файл 2 объекта класса Студент (имя, курс) и код, считывающий их обоих обратно.
Будет ли он работать, если поле "имя" содержит пробелы? А если поменять местами имя и курс?
Какая в вышеупомянутом упражнении используется кодировка символов? Как ее изменить?
Объясните разницу между текстовыми и бинарными файлами.
Как записать "студента" в бинарный файл? (и как прочитать обратно)
Объясните основные принципы работы с RandomAccessFile. Зачем этот класс вообще придумали, если и без него есть текстовые и двоичные потоки?
В какой директории должен находиться файл, если при его открытии указывать только имя (и не указывать путь)?
Почему в ОС Windows директории в пути нужно разделять двумя бэкслешами, а не одним: "c:\\windows\\lalala.txt"?
*/
/*
  У турагентства есть каталог "туров". О каждом "туре" в нем имеется следующая информация:

  название туристической фирмы;
  страна пребывания;
  дата отъезда, дата возвращения;
  цена;
  количество путевок.

- Сделайте (качественный!) класс "тур".

- Используйте java.util.Date для хранения даты, java.util.GregorianCalendar и
  java.text.SimpleDateFormat для преобразования в строку/из строки (помните о методе getDate() в Calendar'е!).

- 1. Реализуйте меню для пользователя со следующими возможностями:
  - просмотр информации обо всех имеющихся турах;
  - добавление тура (всегда в конец);
  - удаление тура в заданной позиции (со сдвигом);
  - редактирование тура в заданной позиции.
  2. При запуске программа считывает туры из текстового файла на диске.
  При завершении - записывает в этот файл измененный список туров (файл "перезаписывается заново").
  Проверьте свою программу по чеклисту - особенно обращаю ваше внимание на п.2.2!

- В дополнение к заданию "level three": сделать еще одну его - модифицированную  - версию, использующую бинарный файл
  ВМЕСТО текстового. Запись в файл можно реализовать через DataOutputStream и RandomAccessFile.
  ВАЖНО! Обязательно предусмотрите возможность работы с очень большими файлами (которые не помещаются в ОЗУ).
  Для этого не храните все туры в массиве, а обращайтесь каждый раз прямо к файлу: читайте его в цикле для вывода
  на экран, делайте seek() в для добавления, удаления и редактирования записей.
  (при удалении - НЕ НАДО делать сдвиг всех последующих элементов - просто помечайте элемент в файле как "удаленный"
  (в нумерации записей при этом появятся "дырки") - кстати, именно так работают с файлами реальные СУБД).
*/

public class Laba5_Files {
  private static Scanner ascanner = new Scanner(System.in);
  public static void main(String[] args){
      boolean sw = true;
      Byte cv = 0;
      while (sw){
          System.out.println("0. Exit");
          System.out.println("1. Show All Tours");
          System.out.println("2. Add the Tour");
          System.out.println("3. Delete the Tour");
          System.out.println("4. Edit the Tour");
          System.out.print("Choice: ");
          cv = ascanner.nextByte();
          switch (cv) {
              case 0:{sw = false; break;}
              case 1:{ FileOutput(); break;}
              case 2:{break;}
              case 3:{break;}
              case 4:{break;}
              default: {System.out.println("Error: BadInput"); break;}
          }
      }
  }
  public static void FileOutput(){

  }
  public static Tour getTour(Integer i){

  }
  public static void addTour(){

  }
  public static void deleteTour(){

  }
  public static void editTour(){

  }
}

class Tour{
    public String companyname;
    public String country;
    public Date departuredate;
    public Date returndate;
    public Integer amount;
    private Boolean enabled;
    public void setEnabled(Boolean b){this.enabled = b;}
    public Boolean getEnabled(){return this.enabled;}
}
