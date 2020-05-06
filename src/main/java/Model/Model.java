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
       String query = "INSERT INTO `library`.`читатель` ";
       query = query + "(`Фамилия`";
       query = query + ", `Имя`";
       if (!(fathername.equals(""))){
           query = query + ", `Отчество`";
       }
       query = query + ", `Абонент`";
       query = query + ", `Должность`";
       query = query + ", `Номер_читательского_билета`";
       query = query + ", `Начало_действия_читательского_билета`";
       query = query + ", `Окончание_действия_читательского_билета`";
       if(!(department.equals(""))) {
           query = query + ", `Факультет`";
       }
       if(!(chair.equals(""))) {
           query = query + ", `Кафедра`";
       }
       if(!(group.equals(""))) {
           query = query + ", `Группа`";
       }
       query = query + ") VALUES (";
       query = query + "'" + surname + "'";
       query = query + ", '" + name + "'";
       if (!(fathername.equals(""))){
           query = query + ", '" + fathername + "'";
        }
       if(!(seasonTicket.equals(""))){
            query = query + ", '" + seasonTicket + "'";
       }
       else{
           query = query + ", '" + "1" + "'";
       }

        if(!(position.equals(""))){
            query = query + ", '" + position + "'";
        }
        else{
            query = query + ", '" + "Студент" + "'";
        }

        Calendar date = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        //int numLibraryCard =
        String beginningOfValidity = dateFormat.format(date.getTime());
        query = query + ", '" + "1000000" + "'";
        query = query + ", '" + beginningOfValidity + "'";
        date.add(Calendar.YEAR, 1);
        String endingOfValidity = dateFormat.format(date.getTime());
        query = query + ", '" + endingOfValidity + "'";

        if(!(department.equals(""))) {
            query = query + ", '" + department + "'";
        }
        if(!(chair.equals(""))) {
            query = query + ", '" + chair + "'";
        }
        if(!(group.equals(""))) {
            query = query + ", '" + group + "'";
        }

        query = query + ")";

        System.out.println(query);
        System.out.println("Adding complete!");

        /*try{
            connector.statement.executeUpdate(query);
            System.out.println("Adding complete!");
        }
        catch (SQLException throwable){
            throwable.printStackTrace();
        }*/
    }

    //добавление новой книги
    public void addNewBook(){//TODO: нужно сделать, чтобы передавались параметры
        try {
            connector.statement.executeUpdate("INSERT INTO `library`.`книга` " +
                    "(`Название`, `Автор`, `Год_издания`, " +
                    "`Дата_поступления`, `Допустимый_срок_хранения`, `Статус_утерянности`, " +
                    "`Стоимость`) VALUES ('Война и мир', 'Лев Николаевич Толстой', " +
                    "'2008', '2015.09.01', '100', '0', '5000')");
            System.out.println("Adding complete");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
