package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.lang.Integer.parseInt;

public class Model {
    Connector connector;

    public Model(Connector connector) {
        this.connector = connector;
    }

    //вывод всех полей таблицы для проверки работоспособности
    public void printTable(String tableName) {
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

    //проверка корректности введёных данных при добавлении нового читателя
    private boolean validatorToAddNewReader(String surname, String name, String fathername) {
        //проверка фамилии: строка должна начинаться на большую букву и состоять только из кириллицы
        boolean isValidSurname = surname.matches("[А-Я][а-я]*");
        if (!isValidSurname) {
            return false;
        }
        //проверка имени таким же образом
        boolean isValidName = name.matches("[А-Я][а-я]*");
        if (!isValidName) {
            return false;
        }
        //проверка отчества таким же образом; разрешено, чтобы отчество было не заполнено
        boolean isValidFathername = fathername.matches("[А-Я][а-я]*");
        if (!isValidFathername && !fathername.equals("")) {
            return false;
        }

        return true;
    }

    //проверка корректности введённых данных при добавлении новой книги
    private boolean validatorToAddNewBook(String bookName, String author, String publishingYear,
                                          String arrivalDate, String allowPeriod, String cost) {
        //проверка, что название книги не пустое
        if (bookName.equals("")) {
            return false;
        }
        //проверка, что в имени автора нет ничего, кроме кириллицы, тире и пробела, и оно начинается с большой буквы
        boolean isValidAuthor = author.matches("[А-Я][А-Я,а-я, ,-]*");
        if (!isValidAuthor) {
            return false;
        }
        //проверка, что год издания книги находится в промежутке между 1990 и 2020, включая концы, и вообще является цифрами
        boolean isValidPublishingYear = publishingYear.matches("[0-9][0-9][0-9][0-9]");
        if (!isValidPublishingYear) {
            return false;
        }
        int pubYear = Integer.parseInt(publishingYear);
        if (pubYear < 1900 || pubYear > 2020) {
            return false;
        }
        //проверка, что дата введена в формате yyyy.MM.dd
        boolean isValidArrivalDate = arrivalDate.matches("[1,2][0,1,9][0-9][0-9]\\.[0,1][0-9]\\.[0-3][0-9]");
        if (!isValidArrivalDate && !arrivalDate.equals("")) {
            return false;
        }
        //проверка, что в поле "Допустимый срок хранения" введено число, или оно пустое
        boolean isValidAllowPeriod = allowPeriod.matches("[0-9]*");
        if (!isValidAllowPeriod && !allowPeriod.equals("")) {
            return false;
        }
        //проверка, что цена книги является целым или нецелым числом
        boolean isValidCost = cost.matches("[0-9]*\\.?[0-9]*");
        if (!isValidCost && !cost.equals("")) {
            return false;
        }

        return true;
    }

    //добавление нового читателя
    public boolean addNewReader(String surname, String name, String fathername,
                                String seasonTicket, String position, String department,
                                String chair, String group) {
        //проверка переданных данных на валидность
        if (validatorToAddNewReader(surname, name, fathername)) {
            //первая часть запроса
            String query = "INSERT INTO `library`.`читатель` ";
            query += "(`Фамилия`";
            query += ", `Имя`";
            if (!(fathername.equals(""))) {
                query += ", `Отчество`";
            }
            query += ", `Абонент`";
            query += ", `Должность`";
            query += ", `Номер_читательского_билета`";
            query += ", `Начало_действия_читательского_билета`";
            query += ", `Окончание_действия_читательского_билета`";
            if (!(department.equals(""))) {
                query += ", `Факультет`";
            }
            if (!(chair.equals(""))) {
                query += ", `Кафедра`";
            }
            if (!(group.equals(""))) {
                query += ", `Группа`";
            }

            //часть запроса с переменными значениями
            query += ") VALUES (";
            query += "'" + surname + "'";
            query += ", '" + name + "'";
            if (!(fathername.equals(""))) {
                query += ", '" + fathername + "'";
            }
            if (!(seasonTicket.equals(""))) {
                query += ", '" + seasonTicket + "'";
            } else {
                query += ", '" + "1" + "'";
            }

            if (!(position.equals(""))) {
                query += ", '" + position + "'";
            } else {
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

            if (!(department.equals(""))) {
                query += ", '" + department + "'";
            }
            if (!(chair.equals(""))) {
                query += ", '" + chair + "'";
            }
            if (!(group.equals(""))) {
                query += ", '" + group + "'";
            }

            query += ")";

            System.out.println(query);//TODO: не забыть убрать вывод в консоль
            System.out.println("Adding complete!");//TODO: не забыть убрать вывод в консоль

            int num = 0;//количество добавленных строк. При успешном добавлении будет равен 1
            try {
                num = connector.statement.executeUpdate(query);
                System.out.println("Adding complete!");//TODO: не забыть убрать вывод в консоль
                System.out.println(num);//TODO: не забыть убрать вывод в консоль
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            return num != 0;
        } else {
            System.out.println("Некорректные данные");//TODO: не забыть убрать вывод в консоль
            return false;
        }
    }

    //добавление новой книги
    public boolean addNewBook(String bookName, String author, String publishingYear,
                           String arrivalDate, String allowPeriod, String cost) {
        if (validatorToAddNewBook(bookName, author, publishingYear, arrivalDate, allowPeriod, cost)) {
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
            if (arrivalDate.equals("")) {
                query += ", '" + today + "'";
            } else {
                query += ", '" + arrivalDate + "'";
            }

            //если не указан срок хранения книги, то устанавливается по умолчанию 30 дней
            if (allowPeriod.equals("")) {
                query += ", '" + "30" + "'";
            } else {
                query += ", '" + allowPeriod + "'";
            }

            //если не указана стоимость, то устанавливается по умолчанию 2000
            if (cost.equals("")) {
                query += ", '" + "2000" + "'";
            } else {
                query += ", '" + cost + "'";
            }

            query += ")";

            System.out.println(query);//TODO: не забыть убрать вывод в консоль
            System.out.println("Adding complete!");//TODO: не забыть убрать вывод в консоль

            int num = 0;//количество внесённых строк в БД; если пройдёт удачно, то будет равно 1
            try {
                num = connector.statement.executeUpdate(query);
                System.out.println("Adding complete");//TODO: не забыть убрать вывод в консоль
                System.out.println(num);//TODO: не забыть убрать вывод в консоль
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            return num != 0;
        }
        else{
            System.out.println("Некорректные данные");
            return false;
        }
    }

}
