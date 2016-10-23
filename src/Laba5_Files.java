import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Laba5_Files {
    private static Scanner ascanner = new Scanner(System.in);
    private static final String dir = "tours.txt";
    private static final DateFormat adateform = new SimpleDateFormat("dd.MM.yyyy");
    private static boolean swf = false;
    public static void main(String[] args){
        boolean sw = true;
        Byte cv = 0;

        while (sw){
            System.out.println("0. Exit \n1. Show All Tours\n2. Add the Tour\n3. Delete the Tour\n4. Edit the Tour");
            System.out.print("Choice: ");
            cv = ascanner.nextByte();
            switch (cv) {
                case 0:{sw = false; break;}
                case 1:{ fileOutput(); break;}
                case 2:{ addTour(); break;}
                case 3:{ deleteTour(); break;}
                case 4:{ editTour(); break;}
                default: {System.out.println("Error: BadInput"); break;}
            }
        }
    }

    private static String getString(Tour tmp){
        return "Company: " + tmp.companyname
                + "| to: " + tmp.country
                + "| Departure: " + adateform.format(tmp.departuredate)
                + "| Return: " + adateform.format(tmp.returndate)
                + "| Amount: " + tmp.amount;
    }

    public static void fileOutput() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir));
            Tour tmp;
            int i = 0;
            while ((tmp = (Tour) ois.readObject()) != null) {
                System.out.println("[" + ++i + "] " + getString(tmp));
            }
            ois.close();
        } catch (EOFException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTour(){
        try {
            ObjectOutputStream oos;
            File f = new File(dir);
            if ((!f.exists()) || (swf == false)){
                oos = new ObjectOutputStream(new FileOutputStream(dir,true));
            }
            else{
                oos = new AppendingObjectOutputStream(new FileOutputStream(dir,true));
            }
            Tour t = new Tour();
            System.out.print("Company name: ");
            t.companyname = ascanner.next();
            System.out.print("Country: ");
            t.country = ascanner.next();
            System.out.print("Departure date [dd.MM.yyyy]: ");
            try{t.departuredate = adateform.parse(ascanner.next());}catch(ParseException e){e.printStackTrace();}
            System.out.print("Return date: [dd.MM.yyyy]: ");
            try{t.returndate = adateform.parse(ascanner.next());}catch(ParseException e){e.printStackTrace();}
            System.out.print("Amount of vouchers: ");
            t.amount = ascanner.nextInt();
            t.enable();
            oos.writeObject(t);
            oos.flush();
            oos.close();
            swf = true;
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
    }

    public static void deleteTour(){
        System.out.print("Tour index: "); int i = ascanner.nextInt();
    }

    public static void editTour(){

    }
}

class Tour implements Serializable{
    public String companyname;
    public String country;
    public Date departuredate;
    public Date returndate;
    public Integer amount;
    private Boolean enabled;
    public void enable(){this.enabled = true;}
    public void disable(){this.enabled = false;}
    public Boolean getEnabled(){return this.enabled;}
}

class AppendingObjectOutputStream extends ObjectOutputStream
{
    public AppendingObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }
    @Override
    protected void writeStreamHeader() throws IOException
    {
        reset();
    }
}

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