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

    //проверка фамилии при её редактировании
    //return true - фамилия прошла проверку
    //return false - фамилия не прошла проверку
    private boolean nameValidator(String name){
        //проверка имени, фамилии или отчества: строка должна начинаться на большую букву и состоять только из кириллицы
        boolean isValidName = name.matches("[А-Я][а-я]*");
        return isValidName;
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

    //проверка корректности введённых данных при редактировании
    private boolean validatorToEditBookProfile(String bookName, String author, String publishingYear,
                                               String arrivalDate, String allowPeriod, String cost){
        //проверка, что в имени автора нет ничего, кроме кириллицы, тире и пробела, и оно начинается с большой буквы
        boolean isValidAuthor = author.matches("[А-Я][А-Я,а-я, ,-]*");
        if (!isValidAuthor && !author.equals("")) {
            return false;
        }
        //проверка, что год издания книги находится в промежутке между 1990 и 2020, включая концы, и вообще является цифрами
        boolean isValidPublishingYear = publishingYear.matches("[0-9][0-9][0-9][0-9]");
        if (!isValidPublishingYear && !publishingYear.equals("")) {
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

            //отправка запроса на добавление в БД
            int num = 0;//количество добавленных строк. При успешном добавлении будет равен 1
            try {
                num = connector.statement.executeUpdate(query);
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

            int num = 0;//количество внесённых строк в БД; если пройдёт удачно, то будет равно 1
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            return num != 0;
        } else {
            return false;
        }
    }

    //выдать книгу читателю
    //return 0- не существует такого пункта выдачи
    //return 1- книга не выдана по какой-то причине, связанной с читателем
    //return 2- книга не выдана по какой-то причине, связанной с книгой
    //return n > 2- id книги, которую можно выдать читателю
    public String giveBookToReader(String pointID, String readerCardNumber,
                                String bookName, String author){
        String resultCode;
        //проверка существования пункта выдачи с таким ID
        if(!isValidPoint(pointID)){
            return "0";
        }
        //узнаём, является ли пункт выдачи абонементом
        if(isSubscription(pointID)){
            //если является, от отдаём книгу на руки
            resultCode = giveBookHome(pointID, readerCardNumber, bookName, author);
            //какая-то причина, связанная с читателем, которая не позволяет выдать ему книгу
            if(resultCode.equals("0")){
                return "1";
            }
            //какая-то причина, связанная с книгой, которая не позволяет её выдать
            if (resultCode.equals("1")){
                return "2";
            }
            //если все проверки прошли успешно, то возвращаем id книги, которую можно выдать
            //для этого узнаём сегодняшнюю дату
            Calendar todayCal = new GregorianCalendar();
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            String today = dateFormat.format(todayCal.getTime());

            String query = "INSERT INTO `library`.`книги_читателя`" +
                    " (`id_читателя`, `id_книги`, `дата_получения_книги`)" +
                    " VALUES ('" + readerCardNumber + "', '" + resultCode + "' ,'" + today + "')";
            //количество изменённых строк
            int num;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return resultCode;
        }
        else {
            //если это читальный зал, то выдаём на день
            resultCode = giveBookInPlace(pointID, readerCardNumber, bookName, author);
            //какая-то причина, связанная с читателем, не позволяющая выдать ему книгу
            if(resultCode.equals("0")){
                return "1";
            }
            //какая-то причина, связанная с книгой, которая не позволяет её выдать
            if (resultCode.equals("1")){
                return "2";
            }
            //если все проверки прошли успешно, то возвращаем id книги, которую можно выдать
            //для этого узнаём сегодняшнюю дату
            Calendar todayCal = new GregorianCalendar();
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            String today = dateFormat.format(todayCal.getTime());

            String query = "INSERT INTO `library`.`книги_читателя`" +
                    " (`id_читателя`, `id_книги`, `дата_получения_книги`)" +
                    " VALUES ('" + readerCardNumber + "', '" + resultCode + "' ,'" + today + "')";
            //количество изменённых строк
            int num;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return resultCode;
        }
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
    //return n > 2- id книги, которую можно выдать
    private String giveBookHome(String pointID, String readerCardNumber,
                             String bookName, String author){
        //проверка всего, связанного с читателем
        if(!canReaderGiveBookHome(pointID, readerCardNumber)){
            return "0";
        }
        //проверка всего, связанного с книгой
        String bookId = whichBookGiveOnPoint(pointID, bookName, author);
        if(bookId.equals("0")){
            return "1";
        }
        else{
            return bookId;
        }
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
                return false;
            }
        }
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
                return false;
            }
        }
        return true;
    }

    //можно ли выдать книгу на этом пункте выдачи (неважно, на дом или нет)
    //т.е. книга существует,
    //книга привязана к этому пункту выдачи,
    //книга не утеряна,
    //книга не находится на руках в данный момент,
    //книга не заказана
    //return 0- нет книги, которую можно было бы выдать
    //return (String) n > 2- id книги, которую можно выдать
    private String whichBookGiveOnPoint(String pointID, String bookName, String bookAuthor){
        //проверка существования книги с указанными названием и автором
        if(!isExistingBook(bookName, bookAuthor)){
            return "0";
        }
        //проверка, прикреплены ли книги с введёнными параметрами к нужному пункту выдачи
        ArrayList<String> bookIdOnPoint = isBookBelongThisPoint(pointID, bookName, bookAuthor);
        if(bookIdOnPoint.isEmpty()){
            return "0";
        }
        //проверка не утеряны ли книги, прошедшие предыдущие проверки
        ArrayList<String> bookIdNotLost = bookIdNotLost(bookIdOnPoint);
        if(bookIdNotLost.isEmpty()){
            return "0";
        }
        //проверка, не находятся ли на руках книги, которые прошли предыдущие проверки
        ArrayList<String> bookIdNotOnHand = bookIdNotOnHand(bookIdNotLost);
        if(bookIdNotOnHand.isEmpty()){
            return "0";
        }
        //проверка, не заказаны ли книги, которые прошли предыдущие проверки
        ArrayList<String> bookIdNotOrdered = bookIdNotOrdered(bookIdNotOnHand);
        if(bookIdNotOrdered.isEmpty()){
            return "0";
        }
        //выбираем первую книгу из списка подходящих книг
        return bookIdNotOrdered.get(0);
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
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
                //флаг, который проверяет, нет ли действующего заказа
                //true - заказа нет, либо он не активен
                //false - существует действующий заказ
                boolean flag = true;
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
        return bookIdNotOrderedList;
    }

    //выдача книги в читальном зале
    //проверяются читатель и книга
    //return 0- какая-то причина, связанная с читателем, не позволяющая выдать книгу
    //return 1- какая-то причина, связанная с книгой, не позволяющая выдать книгу
    //return n > 2- id книги, которую можно выдать
    private String giveBookInPlace(String pointId, String readerCardNumber,
                            String bookName, String bookAuthor){
        //проверка всего, связанного с читаталем
        if(!canReaderGiveBookInPlace(pointId, readerCardNumber)){
            return "0";
        }
        //проверка всего, связанного с книгой
        String bookId = whichBookGiveOnPoint(pointId, bookName, bookAuthor);
        if(bookId.equals("0")){
            return "1";
        }
        else{
            return bookId;
        }
    }

    //можно ли получать читателю книги в читальном зале
    //т.е. существует ли читатель,
    //нет ли действующей дисквалификации,
    //имеется ли отметка на пункте выдачи и
    //не просрочена ли она
    //return true- на читателе нет ограничений, которые запретят ему получение книги
    //return false- читатель по какой-то из причин не может получить книгу в читальном зале
    private boolean canReaderGiveBookInPlace(String pointId, String readerCardNumber){
        //проверка существования читателя с введёным id
        if (!isExistingReader(readerCardNumber)){
            return false;
        }
        //проверка не имеется ли у читателя действующей дисквалификации
        if (!haveNotActiveDisq(readerCardNumber)){
            return false;
        }
        //имеется ли отметка на пункте выдачи, и не просрочена ли она
        if(!isActiveSubsription(pointId, readerCardNumber)){
            return false;
        }
        return true;
    }

    //отметить читателя на пункте выдачи
    //return 0- такого пункта выдачи не существует
    //return 1- такого читателя не существует
    //return 2- попытка разового читателя отметиться на абонементе
    //return 3- что-то пошло не так
    //return 4- отметка успешно обновлена
    //return 5- отметка успешно добавлена
    public int signReaderAtPoint(String pointId, String readerCardNumber){
        //проверка существования пункта выдачи
        if(!isValidPoint(pointId)){
            return 0;
        }
        //проверка существования читателя
        if (!isExistingReader(readerCardNumber)){
            return 1;
        }
        //узнаём статус пункта выдачи
        //true - абонемент
        //false - читальный зал
        boolean subscription = isSubscription(pointId);

        //узнаём статус читателя
        //true - можно отмечаться на всех пунктах выдачи
        //false - можно отмечаться только в читальный залах
        boolean subscriber = isSubscriber(readerCardNumber);
        //одноразовым читателям нельзя отмечаться на абонементах
        if(subscription && !subscriber){
            return 2;
        }

        //проверяем, была ли отметка у этого читателя на этом пункте выдачи раньше
        String query = "SELECT `Дата_отметки` FROM `читатели_пункта_выдачи`" +
                " WHERE (`id_пункта_выдачи` = '" + pointId + "'" +
                " AND `id_читателя` = '" + readerCardNumber + "')";
        //узнаём текущую дату
        Calendar todayCal = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String today = dateFormat.format(todayCal.getTime());

        ResultSet resultSet;
        boolean flag = false; //проверка, будет ли пустым полученный ответ
        try {
            resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если отметка была, то обновляем её на текущую дату
        if(flag){
            //запрос на обновление
            query = "UPDATE `library`.`читатели_пункта_выдачи` SET `Дата_отметки` = '" + today + "'" +
                    " WHERE (`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `id_читателя` = '" + readerCardNumber + "')";
            int result = 0;//проверяем сколько строк было обновлено
            try {
                result = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если что-то пошло не так, и не обновилась ни одна строка
            if (result == 0){
                return 3;
            }
            //если отметка была обновлена
            else{
                return 4;
            }
        }
        else {
            //если отметки не было
            query = "INSERT INTO `library`.`читатели_пункта_выдачи`" +
                    " (`id_пункта_выдачи`, `id_читателя`, `Дата_отметки`)" +
                    " VALUES ('" + pointId + "', '" + readerCardNumber + "', '" + today + "')";
            int result = 0;//проверяем сколько строк было обновлено
            try {
                result = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если что-то пошло не так, и не обновилась ни одна строка
            if (result == 0){
                return 3;
            }
            //если отметка была обновлена
            else{
                return 5;
            }
        }
    }

    //выдача читателю заказа
    //return 0 - нет такого активного заказа
    //return 1 - заказ находится на другом пункте выдачи
    //return 2 - что-то пошло не так
    //return 4 - у читателя нет отметки на этом пункте выдачи
    //return 3 - заказ успешно выдан
    public int giveOrderedBookToReader(String pointId, String readerCardNumber, String bookId){
        //узнаем существует ли данный заказ и активен ли он
        String query = "SELECT `Статус_заказа` FROM `library`.`заказ_книг`" +
                " WHERE (`id_читателя` = '" + readerCardNumber + "'" +
                " AND `id_книги` = '" + bookId + "')";
        //флаг для проверки, не является ли полученный ответ пустым
        //false - ответ пустой
        //true - в ответе есть хотя бы одна строка
        boolean flag = false;

        //статус заказа
        //false - заказ действующий
        //true - заказ не активен
        boolean status = true;
        ResultSet resultSet;
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()){
                flag = true;
                //расчёт на то, что если есть активный заказ, то он окажется последним
                status = resultSet.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если не поступило ни одного ответа или если заказ уже не активен
        if(!flag || status){
            System.out.println("Нет ответа или нет активных заказов");
            return 0;
        }
        //если есть активный заказ
        //проверяем на правильном ли пункте выдачи производится выдача заказа
        query = "SELECT `id_книги` FROM `library`.`книги_пункта_выдачи`" +
                " WHERE (`id_пункта_выдачи` = '" + pointId + "'" +
                " AND `id_книги` = '" + bookId + "')";
        //проверяем не пустой ли будет ответ
        flag = false;
        try {
            resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если заказ находится на другом пункте выдачи
        if(!flag){
            return 1;
        }
        //проверяем, есть ли у читателя отметка на этом пункте выдачи
        //если нет, то сообщаем об этом
        if(!isActiveSubsription(pointId, readerCardNumber)){
            return 4;
        }

        //так как все проверки прошли, выдаём книгу и меняем статус заказа
        //выдаём книгу
        //для этого узнаём текущую дату
        Calendar todayCal = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String today = dateFormat.format(todayCal.getTime());

        query = "INSERT INTO `library`.`книги_читателя`" +
                " (`id_читателя`, `id_книги`, `дата_получения_книги`)" +
                "VALUES ('" + readerCardNumber + "', '" + bookId + "' ,'" + today + "')";
        //количество изменённых строк
        int num = 0;
        try {
            num = connector.statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если строка не добавилась
        if(num == 0){
            return 2;
        }
        //обновляем статус заказа на неактивный
        query = "UPDATE `library`.`заказ_книг` SET" +
                " `Статус_заказа` = '1'" +
                " WHERE (`id_читателя` = '" + readerCardNumber + "'" +
                " AND `id_книги` = '" + bookId + "')";
        num = 0;
        try {
            num = connector.statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если строка не обновилась
        if(num == 0){
            return 2;
        }
        //если всё прошло успешно, то заказ выдан
        return 3;
    }

    //редактирование профиля читателя
    //return 0 - нет читателя с таким номером читательского билета
    //return 1 - введены не соответствующие данные
    //return 2 - что-то пошло не так
    //return 3 - обновление прошло успешно
    public int editReadersProfile(String readerCardNumber, String surname, String name,
                                      String fathername, String position, String department,
                                      String chair, String group){
        //проверка существования читателя с введённым номером читательского билета
        if(!isExistingReader(readerCardNumber)){
            return 0;
        }
        //если была введена фамилия
        if (!surname.equals("")){
            //проверка фамилии на корректность
            if (!nameValidator(surname)){
                return 1;
            }
            String query = "UPDATE `library`.`читатель`" +
                    " SET `Фамилия` = '" + surname + "'" +
                    " WHERE (`Номер_читательского_билета` = '" + readerCardNumber +"')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if(num == 0){
                return 2;
            }
        }
        //если было введено имя
        if (!name.equals("")) {
            //проверка имени на корректность
            if (!nameValidator(name)) {
                return 1;
            }
            String query = "UPDATE `library`.`читатель`" +
                    " SET `Имя` = '" + name + "'" +
                    " WHERE (`Номер_читательского_билета` = '" + readerCardNumber + "')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if (num == 0) {
                return 2;
            }
        }
        //если было введено отчество
        if (!fathername.equals("")) {
            //проверка отчества на корректность
            if (!nameValidator(fathername)) {
                return 1;
            }
            String query = "UPDATE `library`.`читатель`" +
                    " SET `Отчество` = '" + fathername + "'" +
                    " WHERE (`Номер_читательского_билета` = '" + readerCardNumber + "')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if (num == 0) {
                return 2;
            }
        }
        //если была введена должность
        if (!position.equals("")) {
            //вместе с должностью должно обновляться поле Абонент
            //если теперь читатель разовый
            String query = "";
            if(position.equals("Слушатель ФПК")||
            position.equals("Стажёр") || position.equals("Абитуриент")) {
                query = "UPDATE `library`.`читатель`" +
                        " SET `Должность` = '" + position + "'," +
                        " `Абонент` = '0'" +
                        " WHERE (`Номер_читательского_билета` = '" + readerCardNumber + "')";
            }
            //если теперь читатель абонент
            else {
                query = "UPDATE `library`.`читатель`" +
                        " SET `Должность` = '" + position + "'," +
                        " `Абонент` = '1'" +
                        " WHERE (`Номер_читательского_билета` = '" + readerCardNumber + "')";
            }
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if (num == 0) {
                return 2;
            }
        }
        //если был введён факультет
        if (!department.equals("")) {
            String query = "UPDATE `library`.`читатель`" +
                    " SET `Факультет` = '" + department + "'" +
                    " WHERE (`Номер_читательского_билета` = '" + readerCardNumber + "')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if (num == 0) {
                return 2;
            }
        }
        //если была введена кафедра
        if (!chair.equals("")) {
            String query = "UPDATE `library`.`читатель`" +
                    " SET `Кафедра` = '" + chair + "'" +
                    " WHERE (`Номер_читательского_билета` = '" + readerCardNumber + "')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if (num == 0) {
                return 2;
            }
        }
        //если была введена группа
        if (!group.equals("")) {
            String query = "UPDATE `library`.`читатель`" +
                    " SET `Группа` = '" + group + "'" +
                    " WHERE (`Номер_читательского_билета` = '" + readerCardNumber + "')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if (num == 0) {
                return 2;
            }
        }
        return 3;
    }

    //проверка существования книги по id
    private boolean isExistingBook(String bookId){
        String query = "SELECT `Название` FROM `library`.`книга`" +
                " WHERE (`id` = '" + bookId + "')";
        ResultSet resultSet;
        boolean flag = false;//проверка, не окажется ли возвращённый ответ от БД пустым
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

    //валидатор для числовых значений
    private boolean validatorToNumericValues(String values){
        return values.matches("[0-9]*");
    }

    //применить санкции к читателю
    //return 0 - не существует такого читателя
    //return 1 - не существует такой книги
    //return 2 - введены не корректные числовые значения
    //return 3 - что-то пошло не так
    //return 4 - всё прошло успешно
    public int applySunctions(String readerCardNumber, String bookId,
                              String periodOfDisq, String penalty){
        //проверка существования читателя
        if(!isExistingReader(readerCardNumber)){
            return 0;
        }
        //проверка существования книги
        if (!isExistingBook(bookId)){
            return 1;
        }
        //проверка числовых значений
        if(!validatorToNumericValues(periodOfDisq) || !validatorToNumericValues(penalty)){
            return 2;
        }
        //если все проверки прошли успешно, добавляем данные в таблицу Правонарушения
        //узнаём текущую дату
        Calendar todayCal = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String today = dateFormat.format(todayCal.getTime());

        String query = "INSERT INTO `library`.`правонарушения`" +
                " (`id_читателя`, `id_книги`, `Срок_дисквалификации`, `Дата_совершения`, `Наложенный_штраф`)" +
                "VALUES ('" + readerCardNumber + "', '" + bookId + "', '" + periodOfDisq + "', '" + today + "', '" + penalty + "')";
        int num = 0;
        try {
            num = connector.statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если не добавилась строка
        if(num == 0){
            return 3;
        }
        //если всё прошло успешно
        return 4;
    }

    //выписать читателя из библиотеки
    //return 0 - есть несданные книги, нельзя выписывать
    //return 1 - что-то пошло не так
    //return 2 - удаление произведено
    //return 3 - не существует такого читателя
    public int removeReader(String readerCardNumber){
        //проверка существования читателя
        if(!isExistingReader(readerCardNumber)){
            return 3;
        }
        //проверка, все ли книги возвращены им в библиотеку
        String query = "SELECT `дата_реального_возвращения_книги`" +
                " FROM `library`.`книги_читателя`" +
                " WHERE (`id_читателя` = '" + readerCardNumber + "')";
        //флаг того, что есть невозвращенные книги
        boolean flag = false;
        ResultSet resultSet;
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()){
                String dateReturn = resultSet.getString(1);
                if(dateReturn == null){
                    flag = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если есть несданные книги
        if (flag){
            return 0;
        }
        //если нет несданных книг, то можно выписывать
        query = "DELETE FROM `library`.`читатель`" +
                " WHERE (`id` = '" + readerCardNumber + "')";
        int num = 0;
        try {
            num = connector.statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (num == 0){
            return 1;
        }
        return 2;
    }

    //проверка принадлежности книги к пункту выдачи по id
    private boolean isBookBelongThisPoint(String pointId, String bookId){
        String query = "SELECT * FROM `library`.`книги_пункта_выдачи`" +
                " WHERE (`id_пункта_выдачи` = '" + pointId + "'" +
                " AND `id_книги` = '" + bookId + "')";
        boolean flag = false;
        ResultSet resultSet;
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

    //получить от читателя книгу обратно
    //return 0 - книга не принадлежит к этому пункту выдачи
    //return 1 - у читателя не должно быть книги на руках
    //return 2 - что-то пошло не так
    //return 3 - книга возвращена с опозданием
    //retutn 4 - книга возвращена вовремя
    public int getBookBack(String pointId, String readerCardNumber, String bookId){
        //проверка, на тот ли пункт выдачи возвращается книга
        if(!isBookBelongThisPoint(pointId, bookId)){
            return 0;
        }
        //узнаем, когда читатель брал книгу
        String query = "SELECT `дата_получения_книги` FROM `library`.`книги_читателя`" +
                " WHERE (`id_читателя` = '" + readerCardNumber + "'" +
                " AND `id_книги` = '" + bookId + "'" +
                " AND `дата_реального_возвращения_книги` IS NULL)";
        ResultSet resultSet;
        boolean flag = false;
        Date giveDate = null;
        try {
            resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                giveDate = resultSet.getDate(1);
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если читатель не брал книгу или уже возвращал её
        if(!flag){
            return 1;
        }
        //узнаем срок хранения для этой книги
        query = "SELECT `Допустимый_срок_хранения` FROM `library`.`книга`" +
                " WHERE (`id` = '" + bookId + "')";
        int allowPeriod = 0;
        flag = false;
        try {
            resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                allowPeriod = resultSet.getInt(1);
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если что-то пошло не так
        if(!flag){
            return 2;
        }
        //сегодняшняя дата
        Calendar todayCal = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String today = dateFormat.format(todayCal.getTime());
        //вносим информацию о дате возвращения книги
        query = "UPDATE `library`.`книги_читателя`" +
                " SET `дата_реального_возвращения_книги` = '" + today + "'" +
                " WHERE (`id_читателя` = '" + readerCardNumber + "'" +
                " AND `id_книги` = '" + bookId + "'" +
                " AND `дата_реального_возвращения_книги` IS NULL)";
        int num = 0;
        try {
            num = connector.statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если строка не обновилась
        if(num == 0){
            return 2;
        }
        //сравним дату необходимого возвращения и сегодняшнюю дату
        long todayLong = todayCal.getTimeInMillis();
        long giveDateLong = giveDate.getTime();
        long allowDateLong = giveDateLong + (long) allowPeriod * 86400000;
        //если просрочка
        if(todayLong > allowDateLong){
            return 3;
        }
        else{
            return 4;
        }
    }

    //присоединить книгу к пункту выдачи
    //return 0 - не существует такого пункта выдачи
    //return 1 - не существует книги с таким id
    //return 2 - книга уже прикреплена к какому-то пункту выдачи
    //return 3 - что-то пошло не так
    //return 4 - отметка успешно создана
    public int signBookAtPoint(String pointId, String bookId){
        //проверка существования пункта выдачи
        if(!isValidPoint(pointId)){
            return 0;
        }
        //проверка существования книги
        if(!isExistingBook(bookId)){
            return 1;
        }
        //проверка того, что книга ещё не отмечена на каком-либо пункте выдачи
        String query = "SELECT * FROM `library`.`книги_пункта_выдачи`" +
                " WHERE (`id_книги` = '" + bookId + "')";
        ResultSet resultSet;
        boolean flag = false;
        try {
            resultSet = connector.statement.executeQuery(query);
            if(resultSet.next()){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (flag){
            return 2;
        }
        //книга прошла все проверки, можно её присоединять
        query = "INSERT INTO `library`.`книги_пункта_выдачи`" +
                " (`id_пункта_выдачи`, `id_книги`)" +
                " VALUES ('" + pointId + "', '" + bookId + "')";
        int num = 0;
        try {
            num = connector.statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //что-то пошло не так
        if (num == 0){
            return 3;
        }
        else{
            return 4;
        }
    }

    //редактирование профиля книги
    //return 0 - не существует такой книги
    //return 1 - введены некорректные данные
    //return 2 - что-то пошло не так
    //return 3 - данные успешно обновлены
    public int editBookProfile(String bookId, String bookName,
                               String bookAuthor, String pubYear,
                               String arrivalDate, String allowPeriod,
                               String bookCost){
        //проверка существования книги
        if(!isExistingBook(bookId)){
            return 0;
        }
        //проверка корректности данных
        if (!validatorToEditBookProfile(bookName, bookAuthor, pubYear,
                arrivalDate, allowPeriod, bookCost)){
            return 1;
        }
        //если было введено название
        if (!bookName.equals("")){
            String query = "UPDATE `library`.`книга`" +
                    " SET `Название` = '" + bookName + "'" +
                    " WHERE (`id` = '" + bookId +"')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if(num == 0){
                return 2;
            }
        }
        //если было введено имя автора
        if (!bookAuthor.equals("")){
            String query = "UPDATE `library`.`книга`" +
                    " SET `Автор` = '" + bookAuthor + "'" +
                    " WHERE (`id` = '" + bookId +"')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if(num == 0){
                return 2;
            }
        }
        //если был введён год публикации
        if (!pubYear.equals("")){
            String query = "UPDATE `library`.`книга`" +
                    " SET `Год_издания` = '" + pubYear + "'" +
                    " WHERE (`id` = '" + bookId +"')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if(num == 0){
                return 2;
            }
        }
        //если была введена дата прихода
        if (!arrivalDate.equals("")){
            String query = "UPDATE `library`.`книга`" +
                    " SET `Дата_поступления` = '" + arrivalDate + "'" +
                    " WHERE (`id` = '" + bookId +"')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if(num == 0){
                return 2;
            }
        }
        //если был введён срок хранения
        if (!allowPeriod.equals("")){
            String query = "UPDATE `library`.`книга`" +
                    " SET `Допустимый_срок_хранения` = '" + allowPeriod + "'" +
                    " WHERE (`id` = '" + bookId +"')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if(num == 0){
                return 2;
            }
        }
        //если была введена стоимость
        if (!bookCost.equals("")){
            String query = "UPDATE `library`.`книга`" +
                    " SET `Стоимость` = '" + bookCost + "'" +
                    " WHERE (`id` = '" + bookId +"')";
            int num = 0;
            try {
                num = connector.statement.executeUpdate(query);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //если строка не обновилась
            if(num == 0){
                return 2;
            }
        }
        return 3;
    }

    //отметить книгу утерянной
    //return 0 - такой книги не существует
    //return 1 - что-то пошло не так
    //return 2 - отметка об утерянности поставлена
    public int markBookLost(String bookId){
        //проверка существования книги
        if(!isExistingBook(bookId)){
            return 0;
        }
        //сегодняшняя дата
        Calendar todayCal = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String today = dateFormat.format(todayCal.getTime());
        String query = "UPDATE `library`.`книга`" +
                " SET `Статус_утерянности` = '1'," +
                " `Дата_потери` = '" + today + "'" +
                " WHERE (`id` = '" + bookId + "')";
        int num = 0;
        try {
            num = connector.statement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //если строка не обновилась
        if (num == 0){
            return 1;
        }
        else{
            return 2;
        }
    }


    //вывод общего перечня читателей с применением выбранных фильтров
    //return String[][], который передаётся в JTable
    public String[][] generalReadersListWithFilters(String pointId, String chair,
                                                    String department, String group, String groupBy){
        String query = "SELECT `Фамилия`, `Имя`, `Отчество`," +
                " `Номер_читательского_билета`, `Должность`," +
                " `id_пункта_выдачи`, `Кафедра`, `Факультет`, `Группа`" +
                " FROM `library`.`читатель` JOIN `library`.`читатели_пункта_выдачи`" +
                " ON `читатель`.`id` = `читатели_пункта_выдачи`.`id_читателя`";
        //когда заполнено только поле пункта выдачи
        if (!pointId.equals("") && chair.equals("") && department.equals("") && group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "')";
        }
        //когда заполнено только поле кафедры
        if (pointId.equals("") && !chair.equals("") && department.equals("") && group.equals("")){
            query += " WHERE (`читатель`.`Кафедра` = '" + chair + "')";
        }
        //когда заполнены поля пункта выдачи и кафедры
        if (!pointId.equals("") && !chair.equals("") && department.equals("") && group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `читатель`.`Кафедра` = '" + chair + "')";
        }
        //когда заполнено только поле факультета
        if (pointId.equals("") && chair.equals("") && !department.equals("") && group.equals("")){
            query += " WHERE (`читатель`.`Факультет` = '" + department + "')";
        }
        //когда заполнены поля пункта выдачи и факультета
        if(!pointId.equals("") && chair.equals("") && !department.equals("") && group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `читатель`.`Факультет` = '" + department + "')";
        }
        //когда заполнены поля кафедры и факультета
        if(pointId.equals("") && !chair.equals("") && !department.equals("") && group.equals("")){
            query += " WHERE (`читатель`.`Кафедра` = '" + chair + "'" +
                    " AND `читатель`.`Факультет` = '" + department + "')";
        }
        //когда заполнены поля пункта выдачи, кафедры и факультета
        if(!pointId.equals("") && !chair.equals("") && !department.equals("") && group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `читатель`.`Кафедра` = '" + chair + "'" +
                    " AND `читатель`.`Факультет` = '" + department + "')";
        }
        //когда заполнено только поле группы
        if(pointId.equals("") && chair.equals("") && department.equals("") && !group.equals("")){
            query += " WHERE (`читатель`.`Группа` = '" + group + "')";
        }
        //когда заполнены поля пункта выдачи и группы
        if(!pointId.equals("") && chair.equals("") && department.equals("") && !group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `читатель`.`Группа` = '" + group + "')";
        }
        //когда заполнены поля кафедры и группы
        if(pointId.equals("") && !chair.equals("") && department.equals("") && !group.equals("")){
            query += " WHERE (`читатель`.`Кафедра` = '" + chair + "'" +
                    " AND `читатель`.`Группа` = '" + group + "')";
        }
        //когда заполнены поля пункта выдачи, кафедры и группы
        if(!pointId.equals("") && !chair.equals("") && department.equals("") && !group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `читатель`.`Кафедра` = '" + chair + "'" +
                    " AND `читатель`.`Группа` = '" + group + "')";
        }
        //когда заполнены поля факультета и группы
        if(pointId.equals("") && chair.equals("") && !department.equals("") && !group.equals("")){
            query += " WHERE (`читатель`.`Факультет` = '" + department + "'" +
                    " AND `читатель`.`Группа` = '" + group + "')";
        }
        //когда заполнены поля пункта выдачи, факультета и группы
        if(!pointId.equals("") && chair.equals("") && !department.equals("") && !group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `читатель`.`Факультет` = '" + department + "'" +
                    " AND `читатель`.`Группа` = '" + group + "')";
        }
        //когда заполнены поля кафедры, фаультета и группы
        if(pointId.equals("") && !chair.equals("") && !department.equals("") && !group.equals("")){
            query += " WHERE (`читатель`.`Кафедра` = '" + chair + "'" +
                    "AND `читатель`.`Факультет` = '" + department + "'" +
                    " AND `читатель`.`Группа` = '" + group + "')";
        }
        //когда заполнены поля пункта выдачи, кафедры, факультета и группы
        if(!pointId.equals("") && !chair.equals("") && !department.equals("") && !group.equals("")){
            query += " WHERE (`читатели_пункта_выдачи`.`id_пункта_выдачи` = '" + pointId + "'" +
                    " AND `читатель`.`Кафедра` = '" + chair + "'" +
                    " AND `читатель`.`Факультет` = '" + department + "'" +
                    " AND `читатель`.`Группа` = '" + group + "')";
        }
        //группировка
        if(!groupBy.equals("")) {
            query += " GROUP BY `Номер_читательского_билета`";
        }

        ResultSet resultSet;
        String [][] data = new String[30][9];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Фамилия";
        data[0][1] = "Имя";
        data[0][2] = "Отчество";
        data[0][3] = "№ чит.билета";
        data[0][4] = "Должность";
        data[0][5] = "Пункт выдачи";
        data[0][6] = "Кафедра";
        data[0][7] = "Факультет";
        data[0][8] = "Группа";
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
                data[i][7] = resultSet.getString(8);
                data[i][8] = resultSet.getString(9);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод таблицы "Книги читателей"
    //return String[][], который передаётся в JTable
    public String[][] listOfReadersBooks(){
        String query = "SELECT `читатель`.`id`, `Фамилия`, `Имя`, `Отчество`," +
                " `id_книги`, `Название`, `дата_получения_книги`, `дата_реального_возвращения_книги`" +
                " FROM `library`.`книги_читателя`" +
                " JOIN `library`.`читатель`" +
                " ON `книги_читателя`.`id_читателя` = `читатель`.`id`" +
                " JOIN `library`.`книга`" +
                " ON `книги_читателя`.`id_книги` = `книга`.`id`";
        ResultSet resultSet;
        String[][] data = new String[30][8];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Номер чит.билета";
        data[0][1] = "Фамилия";
        data[0][2] = "Имя";
        data[0][3] = "Отчество";
        data[0][4] = "id книги";
        data[0][5] = "Название";
        data[0][6] = "Получение";
        data[0][7] = "Возврат";
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
                data[i][7] = resultSet.getString(8);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод таблицы "Правонарушения"
    //return String[][], который передаётся в JTable
    public String[][] listOfOffenses(){
        String query = "SELECT `id`, `Фамилия`, `Имя`, `Отчество`," +
                " `id_книги`, `Срок_дисквалификации`, `Дата_совершения`," +
                " `Наложенный_штраф`" +
                " FROM `library`.`правонарушения`" +
                " JOIN `library`.`читатель`" +
                " ON `правонарушения`.`id_читателя` = `читатель`.`id`";
        ResultSet resultSet;
        String[][] data = new String[30][8];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Номер чит.билета";
        data[0][1] = "Фамилия";
        data[0][2] = "Имя";
        data[0][3] = "Отчество";
        data[0][4] = "id книги";
        data[0][5] = "Срок диск.";
        data[0][6] = "Дата соверш.";
        data[0][7] = "Штраф";
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
                data[i][7] = resultSet.getString(8);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод таблицы "Читатели пункта выдачи"
    //return String[][], который передаётся в JTable
    public String[][] listOfReadersAtPoint(){
        String query = "SELECT `id_пункта_выдачи`, `id`," +
                " `Фамилия`, `Имя`, `Отчество`," +
                " `Дата_отметки`" +
                " FROM `library`.`читатели_пункта_выдачи`" +
                " JOIN `library`.`читатель`" +
                " ON `читатели_пункта_выдачи`.`id_читателя` = `читатель`.`id`";
        ResultSet resultSet;
        String[][] data = new String[30][6];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Номер п.выдачи";
        data[0][1] = "Номер чит.билета";
        data[0][2] = "Фамилия";
        data[0][3] = "Имя";
        data[0][4] = "Отчество";
        data[0][5] = "Дата отметки";
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
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод таблицы "Пункты выдачи"
    //return String[][], который передаётся в JTable
    public String[][] listOfPoints(){
        String query = "SELECT `id`, `Статус`" +
                " FROM `library`.`пункт_выдачи`";
        ResultSet resultSet;
        String[][] data = new String[30][2];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Номер п.выдачи";
        data[0][1] = "Статус";
        int i = 1;
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()){
                data[i][0] = resultSet.getString(1);
                String status = resultSet.getString(2);
                if(status.equals("1")){
                    data[i][1] = "Абонемент";
                }
                else {
                    data[i][1] = "Читальный зал";
                }
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод таблицы "Книга"
    //return String[][], который передаётся в JTable
    public String[][] listOfBooks(){
        String query = "SELECT `id`, `Название`, `Автор`, `Год_издания`," +
                " `Дата_поступления`, `Допустимый_срок_хранения`," +
                " `Статус_утерянности`, `Дата_потери`, `Стоимость`" +
                " FROM `library`.`книга`";
        ResultSet resultSet;
        String[][] data = new String[30][9];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "id";
        data[0][1] = "Название";
        data[0][2] = "Автор";
        data[0][3] = "Год издания";
        data[0][4] = "Поступление";
        data[0][5] = "Срок хранения";
        data[0][6] = "Утерянность";
        data[0][7] = "Дата потери";
        data[0][8] = "Стоимость";
        int i = 1;
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()) {
                data[i][0] = resultSet.getString(1);
                data[i][1] = resultSet.getString(2);
                data[i][2] = resultSet.getString(3);
                data[i][3] = resultSet.getString(4);
                data[i][4] = resultSet.getString(5);
                data[i][5] = resultSet.getString(6);
                String status = resultSet.getString(7);
                if(status.equals("0")){
                    data[i][6] = "Нет";
                }
                else {
                    data[i][6] = "Да";
                }
                data[i][7] = resultSet.getString(8);
                data[i][8] = resultSet.getString(9);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод таблицы "Книги пунктов выдачи"
    //return String[][], который передаётся в JTable
    public String[][] listOfBooksAtPoint(){
        String query = "SELECT `id_пункта_выдачи`, `id_книги`, `Название`" +
                " FROM `library`.`книги_пункта_выдачи`" +
                " JOIN `library`.`книга`" +
                " ON `книги_пункта_выдачи`.`id_книги` = `книга`.`id`";
        ResultSet resultSet;
        String[][] data = new String[30][3];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Номер п.выдачи";
        data[0][1] = "id книги";
        data[0][2] = "Название";
        int i = 1;
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()) {
                data[i][0] = resultSet.getString(1);
                data[i][1] = resultSet.getString(2);
                data[i][2] = resultSet.getString(3);
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод таблицы "Заказ книг"
    //return String[][], который передаётся в JTable
    public String[][] listOfOrederedBooks(){
        String query = "SELECT `читатель`.`id`, `Фамилия`, `Имя`, `Отчество`," +
                " `id_книги`, `Название`, `Дата_заказа`, `Статус_заказа`" +
                " FROM `library`.`заказ_книг`" +
                " JOIN `library`.`читатель`" +
                " ON `заказ_книг`.`id_читателя` = `читатель`.`id`" +
                " JOIN `library`.`книга`" +
                " ON `заказ_книг`.`id_книги` = `книга`.`id`";
        ResultSet resultSet;
        String[][] data = new String[30][8];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "Номер чит.билета";
        data[0][1] = "Фамилия";
        data[0][2] = "Имя";
        data[0][3] = "Отчество";
        data[0][4] = "id книги";
        data[0][5] = "Название";
        data[0][6] = "Дата";
        data[0][7] = "Статус";
        int i = 1;
        try {
            resultSet = connector.statement.executeQuery(query);
            while(resultSet.next()) {
                data[i][0] = resultSet.getString(1);
                data[i][1] = resultSet.getString(2);
                data[i][2] = resultSet.getString(3);
                data[i][3] = resultSet.getString(4);
                data[i][4] = resultSet.getString(5);
                data[i][5] = resultSet.getString(6);
                data[i][6] = resultSet.getString(7);
                String status = resultSet.getString(8);
                if(status.equals("0")){
                    data[i][7] = "Активен";
                }
                else{
                    data[i][7] = "Не активен";
                }
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    //вывод собственных книг для читателя
    public String[][] listOfMyBook(String login){
        String query = "SELECT `книги_пункта_выдачи`.`id_пункта_выдачи`," +
                " `Название`, `Автор`, `дата_получения_книги`, `Допустимый_срок_хранения`," +
                " `дата_реального_возвращения_книги`" +
                " FROM `library`.`читатель`" +
                " JOIN `library`.`книги_читателя`" +
                " ON `читатель`.`id` = `книги_читателя`.`id_читателя`" +
                " JOIN `library`.`книга`" +
                " ON `книги_читателя`.`id_книги` = `книга`.`id`" +
                " JOIN `library`.`книги_пункта_выдачи`" +
                " ON `книга`.`id` = `книги_пункта_выдачи`.`id_книги`" +
                " WHERE (`читатель`.`Логин` = '" + login + "')";
        ResultSet resultSet;
        String[][] data = new String[30][6];
        //задаём первую строку в таблице, которая будет являться шапкой
        data[0][0] = "П.выдачи";
        data[0][1] = "Название";
        data[0][2] = "Автор";
        data[0][3] = "Дата получения";
        data[0][4] = "Срок хранения";
        data[0][5] = "Дата возврата";
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
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

}
