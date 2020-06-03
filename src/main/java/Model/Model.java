package Model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Model {
    Connector connector;

    public Model(Connector connector) {
        this.connector = connector;
    }

    //вывод всех полей таблицы для проверки работоспособности
    public void printTable(String tableName) {
        ResultSet resultSet;
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

    //авторизация
    //return 0- не обнаружен логин или не совпадает пароль
    //return 1- входим как администратор
    //return 2- входим как читатель
    public int authorization(String login, String password) {
        //получаем правильный пароль из таблицы
        String query = "SELECT `Пароль`, `Администратор` FROM `library`.`читатель` WHERE (`Логин` = '" +
                login + "')";
        String truePass = "";
        String isAdmin = "";
        ResultSet resultSet;
        boolean flag = false;//флаг проверки, что полученный ответ не был пустым
        try {
            resultSet = connector.statement.executeQuery(query);
            if (resultSet.next()) {
                flag = true;
                truePass = resultSet.getString(1);
                isAdmin = resultSet.getString(2);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        if (!flag) {//не обнаружен такой логин
            return 0;
        }
        //сравниваем введённый пароль с тем, что в таблице
        if (!truePass.equals(password)) {
            return 0;
        } else {
            if (isAdmin.equals("1")) {
                return 1;
            } else return 2;
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
    //return 0- что-то сломалось
    //return номер читательского билета- если всё успешно
    public String addNewReader(String surname, String name, String fathername,
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
            query += ", `Логин`";

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
            query += ", '" + "0" + "'";
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
            query += ", '" + surname + " " + name + "'";

            query += ")";

            System.out.println(query);//TODO: не забыть убрать вывод в консоль
            System.out.println("Запрос сгенерирован!");
            //отправка запроса на добавление в БД
            int num = 0;//количество добавленных строк. При успешном добавлении будет равен 1
            try {
                num = connector.statement.executeUpdate(query);
                System.out.println("Adding complete!");//TODO: не забыть убрать вывод в консоль
                System.out.println(num);//TODO: не забыть убрать вывод в консоль
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            if (num == 0) {
                return "0";
            }
            //присвоение номера читательского билета
            String cardNumber = "0";
            ResultSet resultSet;
            try {
                //сначала нужно узнать id добавленного читателя
                resultSet = connector.statement.executeQuery("SELECT `id` FROM `library`.`читатель` " +
                        "WHERE (`Фамилия` = '" + surname +
                        "' AND `Имя` = '" + name + "')");
                resultSet.next();
                int id = Integer.parseInt(resultSet.getString(1));
                System.out.println(id);//TODO: не забыть убрать вывод в консоль
                //меняем номер читательского билета
                cardNumber = "" + id;
                num = connector.statement.executeUpdate("UPDATE `library`.`читатель` SET `Номер_читательского_билета` = " +
                        "'" + cardNumber + "' WHERE (`id` = '" + id + "')");
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            if (num == 0) {
                return "0";
            }
            return cardNumber;
        } else {
            System.out.println("Некорректные данные");//TODO: не забыть убрать вывод в консоль
            return "0";
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
            System.out.println("Запрос сгенерирован!");//TODO: не забыть убрать вывод в консоль

            int num = 0;//количество внесённых строк в БД; если пройдёт удачно, то будет равно 1
            try {
                num = connector.statement.executeUpdate(query);
                System.out.println("Adding complete");//TODO: не забыть убрать вывод в консоль
                System.out.println(num);//TODO: не забыть убрать вывод в консоль
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            return num != 0;
        } else {
            System.out.println("Некорректные данные");
            return false;
        }
    }

    //выдать книгу читателю
    //return 0- не существует такого пункта выдачи
    //return 1- книга не выдана по какой-то причине, связанной с читателем
    //return 2- книга не выдана по какой-то причине, связанной с книгой
    public int giveBookToReader(String pointID, String readerCardNumber,
                                String bookName, String author){
        int resultCode;
        //проверка существования пункта выдачи с таким ID
        if(!isValidPoint(pointID)){
            return 0;
        }
        //узнаём, является ли пункт выдачи абонементом
        if(isSubscription(pointID)){
            //если является, от отдаём книгу на руки
            resultCode = giveBookHome(pointID, readerCardNumber, bookName, author);
            //какая-то причина, связанная с читателем, которая не позволяет выдать ему книгу
            if(resultCode == 0){
                return 1;
            }
            //какая-то причина, связанная с книгой, которая не позволяет её выдать
            if (resultCode == 1){
                return 2;
            }
        }
        else {
            //если это читальный зал, то выдаём на день
            //resultCode = giveBookInPlace(pointID, readerCardNumber, bookName, author);

        }
        //TODO:заглушка
        return 3;
    }


    //проверка существования пункта выдачи с введённым ID
    //return true- пункт выдачи существует
    //return false- пункт выдачи не существует
    private boolean isValidPoint(String pointID) {
        String query = "SELECT `Статус` FROM `library`.`пункт_выдачи` WHERE (`id` = '" +
                pointID + "')";
        ResultSet resultSet;
        boolean flag = false;//проверка, не является ли полученный ответ пустым
        try {
            resultSet = connector.statement.executeQuery(query);
            if (resultSet.next()){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    //проверка, каким именно является валидный пункт выдачи
    //return false- читательский зал
    //return true- абонемент
    private boolean isSubscription(String pointID) {
        String query = "SELECT `Статус` FROM `library`.`пункт_выдачи` WHERE (`id` = '" +
                pointID + "')";
        ResultSet resultSet;
        String isSub = "";
        try {
            resultSet = connector.statement.executeQuery(query);
            if (resultSet.next()) {
                isSub = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isSub.equals("1");
    }

    //выдать книгу читателю домой
    //проверка читателя и книги
    //return 0- какая-то причина, связанная с читателем, не позволяющая выдать книгу
    //return 1- какая-то причина, связанная с книгой, не позволяющая выдать книгу
    //return 2- TODO
    private int giveBookHome(String pointID, String readerCardNumber,
                             String bookName, String author){
        if(!canReaderGiveBookHome(pointID, readerCardNumber)){
            return 0;
        }
        //TODO: сюда добавляем проверку книги
        if(!canBookGiveAtHome(pointID, bookName, author)){
            return 1;
        }
        return 2;//TODO:заглушка
    }

    //можно ли получать читателю книги на руки
    //т.е. существует ли читатель,
    // соответствует ли его категория,
    //нет ли действующей дисквалификации,
    //имеется ли отметка на пункте выдачи и
    //не просрочена ли она
    //return true- на читателе нет ограничений, которые запретят ему получение книги
    //return false- читатель по какой-то из причин не может получить книгу на руки
    private boolean canReaderGiveBookHome(String pointID, String readerCardNumber){
        //проверка существования читателя с введённым ID
        if(!isExistingReader(readerCardNumber)){
            return false;
        }
        //проверка, не является ли читатель разовым
        if (!isSubscriber(readerCardNumber)){
            return false;
        }
        //проверка не имеется ли у читателя действующей дисквалификации
        if(!haveNotActiveDisq(readerCardNumber)){
            return false;
        }
        //имеется ли отметка на пункте выдачи, и не просрочена ли она
        if(!isActiveSubsription(pointID, readerCardNumber)){
            return false;
        }
        return true;
    }

    //проверка, существует ли читатель
    //return true- существует
    //return false- нет читателя с таким ID
    private boolean isExistingReader(String readerCardNumber){
        String query = "SELECT `Абонент` FROM `library`.`читатель` WHERE (`Номер_читательского_билета` = '" +
                readerCardNumber + "')";
        ResultSet resultSet;
        boolean flag = false;//флаг-проверка, что ответ не будет пустым
        try {
            resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    //проверка соответствия категории читателя для получения книги на руки
    //return false- категория не позволяет получить книги на руки
    //return true- категория позволяет получить книги на руки
    private boolean isSubscriber(String readerCardNumber) {
        String isSubscriber = "";
        String query = "SELECT `Абонент` FROM `library`.`читатель` WHERE (`Номер_читательского_билета` = '" +
                readerCardNumber + "')";
        ResultSet resultSet;
        try {
            resultSet = connector.statement.executeQuery(query);
            if (resultSet.next()) {
                isSubscriber = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isSubscriber.equals("1");
    }

    //проверка на наличие действующей дисквалификации
    //return true- нет действующей дисквалификации
    //return false- есть действующая дисквалификация
    private boolean haveNotActiveDisq(String readerCardNumber) {
        String query = "SELECT `Дата_совершения`, `Срок_дисквалификации` FROM `library`.`правонарушения` WHERE" +
                "(`id_читателя` = '" + readerCardNumber + "')";
        ResultSet resultSet;
        boolean flag = false;//флаг того, пустой получился resultset или нет
        //полученные данные храним в виде <дата совершения,срок дисквалификации>
        Map<Date, Integer> disqMap = new HashMap<>();
        try {
            resultSet = connector.statement.executeQuery(query);
            while (resultSet.next()) {
                flag = true;
                disqMap.put(resultSet.getDate(1), resultSet.getInt(2));
                System.out.println(resultSet.getDate(1));//TODO: не забыть убрать вывод в консоль
                System.out.println(resultSet.getInt(2));//TODO: не забыть убрать вывод в консоль
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если количество полученных строк равно 0, то дисквалификации никогда не было, и
        //действующей точно нет
        if (!flag) {
            System.out.println("Нет дисквала, пустой ответ");
            return true;
        }
        //проходимся по полученной Мапе и получаем дату окончания дисквалификации, которую кладём в Список
        //для получения даты окончания дисквалификации складываем начало диск. и её продолжительность
        List<Date> endingDisq = new ArrayList<>();
        for(Map.Entry<Date, Integer> item: disqMap.entrySet()){
            long beginingDate = item.getKey().getTime();
            long duration = (long)item.getValue() * 86400000; //переводим милисекунды в сутки
            long sum = beginingDate + duration;
            Date endingDate = new Date(sum);
            endingDisq.add(endingDate);
        }
        //сравниваем дату окончания диск. с текущей датой
        //если хотя бы раз встречается более поздняя дата, то есть неоконченная дисквалификация
        Calendar todayCal = new GregorianCalendar();
        long todayLong = todayCal.getTimeInMillis();
        Date today = new Date(todayLong);
        for (Date item: endingDisq){
            if (today.before(item)){
                System.out.println("Есть дисквалификация");//TODO: не забыть убрать вывод в консоль
                return false;
            }
        }
        System.out.println("Нет дисквала");//TODO: не забыть убрать вывод в консоль
        return true;
    }

    //проверка, прикреплён ли читатель к пункту выдачи, и не просрочена ли отметка
    //return true- прикреплён, и отметка действует
    //return false- одно из условий не выполнено
    private boolean isActiveSubsription(String pointID, String cardNumber){
        String query = "SELECT `Дата_отметки` FROM `library`.`читатели_пункта_выдачи` WHERE " +
                "(`id_пункта_выдачи` = '" + pointID + "' AND `id_читателя` = '" + cardNumber + "')";
        ResultSet resultSet;
        boolean flag = false;//проверка не является ли полученный ответ пустым
        //полученные данные храним в списке
        List<Date> marksDate = new ArrayList<>();
        try {
            resultSet = connector.statement.executeQuery(query);
            while (resultSet.next()){
                flag = true;
                marksDate.add(resultSet.getDate(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //System.out.println(marksDate.get(0));
        //если количество полученных строк равно 0, то отметки на этом пукте никогда не было
        if(!flag){
            System.out.println("Нет отметки, пустой ответ");
            return false;
        }
        //список, хранящий даты окончания отметок
        List<Date> endingMarksDate = new ArrayList<>();
        for(Date item: marksDate){
            long marksDateLong = item.getTime();
            long endingDateLong = marksDateLong + 31536000000L;
            Date endingDate = new Date(endingDateLong);
            endingMarksDate.add(endingDate);
        }
        //проверяем, нет ли в этом списке даты, меньшей, чем сегодня
        Calendar todayCal = new GregorianCalendar();
        long todayLong = todayCal.getTimeInMillis();
        Date today = new Date(todayLong);
        for (Date item: endingMarksDate){
            if(today.after(item)){
                System.out.println("Нет отметки");//TODO: не забыть убрать вывод в консоль
                return false;
            }
        }
        System.out.println("Есть отметка");//TODO: не забыть убрать вывод в консоль
        return true;
    }

    //можно ли выдать книгу домой
    //т.е. книга существует,
    //книга привязана к этому пункту выдачи,
    //книга не утеряна,
    //книга не находится на руках в данный момент,
    //книга не заказана
    //return true- книга может быть выдана
    //return false- книга не может быть выдана по какой-то из причин
    private boolean canBookGiveAtHome(String pointID, String bookName, String bookAuthor){
        //проверка существования книги с указанными названием и автором
        if(!isExistingBook(bookName, bookAuthor)){
            return false;
        }
        //проверка, прикреплены ли книги с введёнными параметрами к нужному пункту выдачи
        ArrayList<String> bookIdOnPoint = isBookBelongThisPoint(pointID, bookName, bookAuthor);
        if(bookIdOnPoint.isEmpty()){
            return false;
        }
        //проверка не утеряны ли книги, прошедшие предыдущие проверки
        ArrayList<String> bookIdNotLost = bookIdNotLost(bookIdOnPoint);
        if(bookIdNotLost.isEmpty()){
            return false;
        }
        //проверка, не находятся ли на руках книги, которые прошли предыдущие проверки
        ArrayList<String> bookIdNotOnHand = bookIdNotOnHand(bookIdNotLost);
        if(bookIdNotOnHand.isEmpty()){
            return false;
        }
        //проверка, не заказаны ли книги, которые прошли предыдущие проверки
        ArrayList<String> bookIdNotOrdered = bookIdNotOrdered(bookIdNotOnHand);
        if(bookIdNotOrdered.isEmpty()){
            return false;
        }
        return true;//TODO:заглушка
    }

    //проверка существования книги с указанными названием и автором
    //return true- книга существует
    //return false- книга не существует
    private boolean isExistingBook(String bookName, String bookAuthor){
        String query = "SELECT `id` FROM `library`.`книга` WHERE (`Название` = '"
                + bookName + "' AND `Автор` = '" + bookAuthor + "')";
        ResultSet resultSet;
        boolean flag = false;//проверка, не окажется ли возвращённый ответ от БД пустым
        try {
            resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                flag = true;
                System.out.println("Такая книга существует");//TODO: убрать вывод в консоль
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (!flag){
            System.out.println("Такой книги не существует");//TODO: убрать вывод в консоль
        }
        return flag;
    }

    //проверка, привязана ли хотя бы одна книга с указанными названием и автором к этому пункту выдачи
    //return список, состоящий из ID книг с подходящими названием и автором и привязанные к этому пункту
    private ArrayList<String> isBookBelongThisPoint(String pointID, String bookName, String bookAuthor){
        String query = "SELECT `книга`.`id` FROM `library`.`книга` JOIN `library`.`книги_пункта_выдачи` ON" +
                " `книга`.`id` = `книги_пункта_выдачи`.`id_книги`" +
                " WHERE (`книги_пункта_выдачи`.`id_пункта_выдачи`='" + pointID +
                "' AND `книга`.`Название` = '" + bookName + "' AND `книга`.`Автор` = '" + bookAuthor + "')";
        ResultSet resultSet;
        //спиок, хранящий ID книг с нужными названиями и авторами, которые привязаны к нужному п.в.
        ArrayList<String> bookIdOnPoint = new ArrayList<>();
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()){
                bookIdOnPoint.add(resultSet.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //TODO: убрать проверочный вывод
        for(String item: bookIdOnPoint){
            System.out.println(item);
        }
        return bookIdOnPoint;
    }

    //проверка на то, не утеряны ли книги, которые подходят по пункту, названию и автору
    //return список книг, прошедший все эти проверки
    private ArrayList<String> bookIdNotLost (ArrayList<String> bookIdOnPoint){
        ArrayList<String> bookIdNotLost = new ArrayList<>();
        for(String item: bookIdOnPoint){
            String query = "SELECT `Статус_утерянности` FROM `library`.`книга`" +
                    " WHERE (`id` = '" + item + "')";
            ResultSet resultSet;
            try {
                resultSet = connector.statement.executeQuery(query);
                if(resultSet.next()){
                    boolean lost = resultSet.getBoolean(1);
                    if(!lost){
                        bookIdNotLost.add(item);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //TODO:убрать проверочную печать в консоль
        for (String item: bookIdNotLost){
            System.out.println(item);
        }
        return bookIdNotLost;
    }

    //проверка, не находится ли книги, которые уже прошли проверку на утерянность, название, автора, пункт выдачи, в данный момент на руках
    //return список книг, которые прошли все проверки
    private ArrayList<String> bookIdNotOnHand(ArrayList<String> bookIdNotLost){
        ArrayList<String> bookIdNotOnHandList = new ArrayList<>();
        for(String item: bookIdNotLost){
            String query = "SELECT `Дата_реального_возвращения_книги` FROM `library`.`книги_читателя`" +
                    "WHERE (`id_книги` = '" + item + "')";
            ResultSet resultSet;
            try {
                resultSet = connector.statement.executeQuery(query);
                boolean flag = true;//флаг проверки, не было ли пустых дат возвращения книги
                while (resultSet.next()){
                    String dateReturn = resultSet.getString(1);
                    if(dateReturn == null){
                        flag = false;
                    }
                }
                if (flag){
                    bookIdNotOnHandList.add(item);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("Here");
        //TODO: убрать вывод в консоль
        for(String item: bookIdNotOnHandList){
            System.out.println("Книги" + item);
        }
        return bookIdNotOnHandList;
    }

    //проверка, не заказана ли книга, которая подходит по названию, автору, пункту выдачи,
    //не утеряна и не на руках
    private ArrayList<String> bookIdNotOrdered (ArrayList<String> bookIdNotOnHand){
        ArrayList<String> bookIdNotOrderedList = new ArrayList<>();
        for (String item: bookIdNotOnHand){
            String query = "SELECT `Статус_заказа` FROM `library`.`заказ_книг`" +
                    "WHERE (`id_книги` = '" + item + "')";
            ResultSet resultSet;
            try {
                resultSet = connector.statement.executeQuery(query);
                boolean flag = true;//флаг, который прверяет, нет ли действующего заказа
                while (resultSet.next()){
                    boolean notActiveOrder = resultSet.getBoolean(1);
                    if(!notActiveOrder){
                        flag = false;
                    }
                }
                if(flag){
                    bookIdNotOrderedList.add(item);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //TODO: убрать вывод в консоль
        for(String item: bookIdNotOrderedList){
            System.out.println("Заказ" + item);
        }
        return bookIdNotOrderedList;
    }

    //вывод общего перечня читателей с применением выбранных фильтров
    //return String[][], который передаётся в JTable
    public String[][] generalReadersListWithFilters(){
        String query = "SELECT `Фамилия`, `Имя`, `Отчество`, `id_пункта_выдачи`, `Кафедра`, `Факультет`, `Группа`" +
                " FROM `library`.`читатель` JOIN `library`.`читатели_пункта_выдачи`" +
                " ON `читатель`.`id` = `читатели_пункта_выдачи`.`id_читателя`";
        ResultSet resultSet;
        String [][] data = new String[30][7];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Фамилия";
        data[0][1] = "Имя";
        data[0][2] = "Отчество";
        data[0][3] = "Пункт выдачи";
        data[0][4] = "Кафедра";
        data[0][5] = "Факультет";
        data[0][6] = "Группа";
        int i = 1;
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()){
                data[i][0] = resultSet.getString(1);
                data[i][1] = resultSet.getString(2);
                data[i][2] = resultSet.getString(3);
                data[i][3] = resultSet.getString(4);
                data[i][4] = resultSet.getString(5);
                data[i][5] = resultSet.getString(6);
                data[i][6] = resultSet.getString(7);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //TODO: убрать вывод в консоль
        for(int x = 0; x <= i; x++){
            for(int y = 0; y < 7; y++){
                System.out.println(data[x][y]);
            }
        }
        return data;
    }
}
