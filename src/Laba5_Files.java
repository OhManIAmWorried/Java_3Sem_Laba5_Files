import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;


public class Laba5_Files {
    private static Scanner ascanner = new Scanner(System.in);
    private static final String dir = "tours.txt";
    private static final DateFormat adateform = new SimpleDateFormat("dd.MM.yyyy");
    private static boolean swf;
    private static int currenti = 0;

    public static void main (String[] args) throws IOException,ClassNotFoundException,ParseException{
        boolean sw = true;
        swf = false;
        Byte cv = 0;
        while (sw){
            System.out.println("0. Exit \n1. Show All Tours\n2. Add the Tour\n3. Delete the Tour\n4. Edit the Tour");
            System.out.print("Choice: ");
            cv = ascanner.nextByte();
            switch (cv) {
                case 0:{sw = false; break;}
                case 1:{fileOutput(); break;}
                case 2:{addTour(); break;}
                case 3:{deleteTour(); break;}
                case 4:{editTour(); break;}
                default: {System.out.println("Error: BadInput"); break;}
            }
        }
    }

    private static String getString(Tour tmp){
        return "Company: " + tmp.companyname
                + "| to: " + tmp.country
                + "| Departure: " + adateform.format(tmp.departuredate)
                + "| Return: " + adateform.format(tmp.returndate)
                + "| Price: " + tmp.price
                + "| Amount: " + tmp.amount;
    }

    private static void fileOutput() throws ClassNotFoundException,IOException {
        FileInputStream fis = new FileInputStream(dir);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            Tour tmp;
            while (fis.available() > 0) {
                tmp = (Tour) ois.readObject();
                if (tmp.getEnabled()){System.out.println("[" + tmp.index + "] " + getString(tmp));}
            }
        } finally{
            ois.close();
        }
    }

    private static void addTour() throws IOException,ParseException{
        ObjectOutputStream oos;
        File f = new File(dir);
        if ((!f.exists()) || (!swf)){
            oos = new ObjectOutputStream(new FileOutputStream(dir,true));
        }
        else{
            oos = new AppendingObjectOutputStream(new FileOutputStream(dir,true));
        }
        try {
            Tour t = new Tour();
            System.out.print("Company name: ");                      t.companyname = ascanner.next();
            System.out.print("Country: ");                           t.country = ascanner.next();
            System.out.print("Departure date [dd.MM.yyyy]: ");       t.departuredate = adateform.parse(ascanner.next());
            System.out.print("Return date [dd.MM.yyyy]: ");          t.returndate = adateform.parse(ascanner.next());
            System.out.print("Price [_,_]: ");                       t.price = ascanner.nextFloat();
            System.out.print("Amount of vouchers: ");                t.amount = ascanner.nextInt();
            t.enable();
            t.index = currenti++;
            swf = true;
            oos.writeObject(t);
        } finally {
            oos.flush();
            oos.close();
        }
    }

    private static void deleteTour() throws IOException,ClassNotFoundException {
        System.out.print("Tour index: "); int index = ascanner.nextInt();
        ArrayList<Tour> al = getAL();
        Tour a = al.get(index);
        a.disable();
        al.set(index,a);
        File f = new File(dir);
        if (f.delete()){System.out.println("Successful file deletion");} else {System.out.print("file is not deleted");}
        swf = false;
        writeAL(al);
        swf = true;
    }

    private static ArrayList<Tour> getAL() throws ClassNotFoundException, IOException, EOFException {
        Tour tmp;
        ArrayList<Tour> al = new ArrayList<Tour>();
        FileInputStream fis = new FileInputStream(dir);
        ObjectInputStream ois = new ObjectInputStream(fis);
        while (fis.available() > 0) {
            tmp = (Tour) ois.readObject();
            al.add(tmp);
        }
        fis.close();
        ois.close();
        return al;
    }

    private static void writeAL(ArrayList<Tour> al) throws IOException,ClassNotFoundException{
        ObjectOutputStream oos;
        File f = new File(dir);
        if ((!f.exists()) || (!swf)){
            oos = new ObjectOutputStream(new FileOutputStream(dir,false));
        }
        else{
            oos = new AppendingObjectOutputStream(new FileOutputStream(dir,true));
        }
        for (Tour i: al) {
            oos.writeObject(i);
        }
        swf = true;
        oos.flush();
        oos.close();
    }

    private static void editTour() throws IOException,ClassNotFoundException,ParseException{
        System.out.print("Tour index: "); int index = ascanner.nextInt();
        ArrayList<Tour> al = getAL();
        for (Tour i: al){System.out.println(getString(i));}
        Boolean sw = true;
        while (sw) {
            System.out.println("Change:\n0. Return\n1. Company name\n2. Country\n3. Departure date\n4. Return date\n" +
                    "5. Price\n6. Amount of vouchers\n7. Reestablish the tour ");
            System.out.print("Choice: "); Byte cv = ascanner.nextByte();
            Tour a = al.get(index);
            switch (cv){
                case 0: {sw = false; break;}
                case 1:{System.out.print("New company name: "); a.companyname = ascanner.next(); break;}
                case 2:{System.out.print("New country: "); a.country = ascanner.next(); break;}
                case 3:{
                    System.out.print("New departure date [dd.MM.yyyy]: ");
                    a.departuredate = adateform.parse(ascanner.next());
                    break;
                }
                case 4:{
                    System.out.print("New return date [dd.MM.yyyy]: "); a.returndate = adateform.parse(ascanner.next());
                    break;
                }
                case 5:{System.out.print("New price [_,_]: "); a.price = ascanner.nextFloat(); break;}
                case 6:{System.out.print("New amount: "); a.amount = ascanner.nextInt(); break;}
                case 7:{
                    if (a.getEnabled()){
                        System.out.print("already existing");
                    }
                    else{
                        a.enable();
                        System.out.println("success!");
                    }
                    break;
                }
                default:{System.out.println("Error: BadInput"); break;}
            }
            al.set(index,a);
        }
        File f = new File(dir);
        if (f.delete()){
            System.out.println("Successful file deletion");
        } else {System.out.println("file is not deleted, exist = " + f.exists());}
        swf = false;
        writeAL(al);
        swf = true;
    }
}

class Tour implements Serializable{
    String companyname;
    String country;
    Date departuredate;
    Date returndate;
    Float price;
    Integer amount;
    private Boolean enabled;
    int index;
    void enable(){this.enabled = true;}
    void disable(){this.enabled = false;}
    Boolean getEnabled(){return this.enabled;}
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