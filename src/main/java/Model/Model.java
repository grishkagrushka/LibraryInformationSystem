package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Model {
    Connector connector;

    public Model(Connector connector){
        this.connector = connector;
    }

    //вывод всех полей таблицы для проверки работоспособности
    public void printTable(String tableName){
        ResultSet resultSet = null;
        int columnsNum = 0;
        try {
            resultSet = connector.statement.executeQuery("SELECT * FROM " + tableName);
            columnsNum = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNum; i++) {
                    System.out.println(resultSet.getString(i) + "\t");//распечатываем все поля построчно
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    //добавление нового читателя
    public void addNewReader(String surname, String name, String fathername,
                             String seasonTicket, String position, String department,
                             String chair, String group){
        //первая часть запроса
       String query = "INSERT INTO `library`.`читатель` ";
       query += "(`Фамилия`";
       query += ", `Имя`";
       if (!(fathername.equals(""))){
           query += ", `Отчество`";
       }
       query += ", `Абонент`";
       query += ", `Должность`";
       query += ", `Номер_читательского_билета`";
       query += ", `Начало_действия_читательского_билета`";
       query += ", `Окончание_действия_читательского_билета`";
       if(!(department.equals(""))) {
           query += ", `Факультет`";
       }
       if(!(chair.equals(""))) {
           query += ", `Кафедра`";
       }
       if(!(group.equals(""))) {
           query += ", `Группа`";
       }

       //часть запроса с переменными значениями
       query += ") VALUES (";
       query += "'" + surname + "'";
       query += ", '" + name + "'";
       if (!(fathername.equals(""))){
           query += ", '" + fathername + "'";
        }
       if(!(seasonTicket.equals(""))){
            query += ", '" + seasonTicket + "'";
       }
       else{
           query += ", '" + "1" + "'";
       }

        if(!(position.equals(""))){
            query += ", '" + position + "'";
        }
        else{
            query += ", '" + "Студент" + "'";
        }

        //определение сегодняшней даты, когда читетель вводится в библиотеку
        Calendar date = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        //int numLibraryCard =
        String beginningOfValidity = dateFormat.format(date.getTime());
        query += ", '" + "1000000" + "'";
        query += ", '" + beginningOfValidity + "'";
        date.add(Calendar.YEAR, 1);
        String endingOfValidity = dateFormat.format(date.getTime());
        query += ", '" + endingOfValidity + "'";

        if(!(department.equals(""))) {
            query += ", '" + department + "'";
        }
        if(!(chair.equals(""))) {
            query += ", '" + chair + "'";
        }
        if(!(group.equals(""))) {
            query += ", '" + group + "'";
        }

        query += ")";

        System.out.println(query);
        System.out.println("Adding complete!");//TODO: не забыть убрать вывод в консоль

        /*try{
            connector.statement.executeUpdate(query);
            System.out.println("Adding complete!");//TODO: не забыть убрать вывод в консоль
        }
        catch (SQLException throwable){
            throwable.printStackTrace();
        }*/
    }

    //добавление новой книги
    public void addNewBook(String bookName, String author, String publishingYear,
                           String arrivalDate, String allowPeriod, String cost){
        //первая часть запроса
        String query = "INSERT INTO `library`.`книга`";
        query += " (`Название`";
        query += ", `Автор`";
        query += ", `Год_издания`";
        query += ", `Дата_поступления`";
        query += ", `Допустимый_срок_хранения`";
        query += ", `Стоимость`) VALUES (";

        //вторая часть запроса с переменными значениями
        query += "'" + bookName + "'";
        query += ", '" + author + "'";
        query += ", '" + publishingYear + "'";
        //если не указана дата прихода книги, то присваевается сегодняшняя
        Calendar date = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String today = dateFormat.format(date.getTime());
        if (arrivalDate.equals("")){
            query += ", '" + today + "'";
        }
        else {
            query += ", '" + arrivalDate + "'";
        }

        //если не указан срок хранения книги, то устанавливается по умолчанию 30 дней
        if(allowPeriod.equals("")){
            query += ", '" + "30" + "'";
        }
        else {
            query += ", '" + allowPeriod + "'";
        }

        //если не указана стоимость, то устанавливается по умолчанию 2000
        if (cost.equals("")){
            query += ", '" + "2000" + "'";
        }
        else{
            query += ", '" + cost + "'";
        }

        query += ")";

        System.out.println(query);
        System.out.println("Adding complete!");//TODO: не забыть убрать вывод в консоль


        try {
            connector.statement.executeUpdate(query);
            System.out.println("Adding complete");//TODO: не забыть убрать вывод в консоль
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
