package Controller;

import Model.Model;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    JFrame frame;
    Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void start() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame = new JFrame();

        showAuthorizationForm();

        frame.setResizable(false);

        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Информационная система библиотеки");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //форма авторизации
    public void showAuthorizationForm() {
        //отрисовка формы
        final AuthorizationForm form = new AuthorizationForm();
        frame.setContentPane(form.getAuthorizationPanel());

        form.entryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = form.loginTextField.getText();
                String password = form.passwordTextField.getText();
                int authResult = model.authorization(login, password);
                if (authResult == 0) {
                    //не вошли
                    JOptionPane.showMessageDialog(form.getAuthorizationPanel(), "Неверный логин или пароль",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                if (authResult == 1) {
                    //вошли администратором
                    showMainForm();
                }
                if (authResult == 2) {
                    //вошли читателем
                }
            }
        });
    }

    //главная форма
    public void showMainForm() {
        //отрисовка формы
        MainForm form = new MainForm();
        frame.setContentPane(form.getRootPanel());
        frame.revalidate();

        //вызов "Добавление новго читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //вызов "Добавление новой книги"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });
    }

    //форма добавления нового читателя
    public void showAddNewReaderForm() {
        //отрисовка формы
        final AddNewReaderForm form = new AddNewReaderForm();
        frame.setContentPane(form.getAddNewReaderPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"- окно обновится
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "добавить"
        form.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //собираем данные с полей ввода
                String surname = form.surnameTextField.getText();
                String name = form.nameTextField.getText();
                String fathername = form.fathernameTextField.getText();
                String position = (String) form.positionComboBox.getSelectedItem();
                if (position.equals("Выберите должность")) {
                    position = "";
                }
                String seasonTicket = "";
                if (position.equals("Слушатель ФПК") || position.equals("Стажёр") || position.equals("Абитуриент")) {
                    seasonTicket = "0";
                }
                String department = (String) form.departmentComboBox.getSelectedItem();
                if (department.equals("Выберите факультет") || position.equals("Слушатель ПО") || position.equals("Слушатель ФПК")) {
                    department = "";
                }
                String chair = form.chairTextField.getText();
                if (position.equals("Слушатель ПО") || position.equals("Слушатель ФПК")) {
                    chair = "";
                }
                String group = form.groupTextField.getText();
                if (!position.equals("Студент")) {
                    group = "";
                }
                //передаём управление в Model
                String result = model.addNewReader(surname, name, fathername, seasonTicket, position, department, chair, group);
                //сообщение об успехе или неудаче добавления
                if (!result.equals("0")) {
                    JOptionPane.showMessageDialog(form.getAddNewReaderPanel(), "Новый читатель добавлен\nНомер читательского билета: " + result,
                            "Успех!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(form.getAddNewReaderPanel(), "Проверьте корректность введённых данных",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                //перерисовываем форму, чтобы она обновилась
                showAddNewReaderForm();
            }
        });
    }

    //форма добавления новой книги
    public void showAddNewBookForm() {
        //отрисовка формы
        final AddNewBookForm form = new AddNewBookForm();
        frame.setContentPane(form.getAddNewBookPanel());
        frame.revalidate();

        //вызов "Добавление нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //вызов "Добавление новой книги"- окно обновится
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //вызов "добавить"
        form.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //собираем данные с полей ввода
                String bookName = form.bookNameTextField.getText();
                String author = form.authorNameTextField.getText();
                String publishingYear = form.yearOfPublishingTextField.getText();
                String arrivalDate = form.bookArrivalDateTextField.getText();
                String allowPeriod = form.bookAllowPeriodTextField.getText();
                String cost = form.bookCostTextField.getText();
                //передаём управление в Model
                boolean result = model.addNewBook(bookName, author, publishingYear, arrivalDate, allowPeriod, cost);
                if (result) {
                    //сообщение об успехе или неудаче добавления
                    JOptionPane.showMessageDialog(form.getAddNewBookPanel(), "Новая книга добавлена",
                            "Успех!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(form.getAddNewBookPanel(), "Проверьте корректность введённых данных.\nДата получения книги должна быть указана в формате yyyy.MM.dd",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                showAddNewBookForm();
            }
        });
    }

    //форма выдачи книги читателю
    public void showGiveBookToReaderForm() {
        //отрисовка формы
        final GiveBookToReaderForm form = new GiveBookToReaderForm();
        frame.setContentPane(form.getGiveBookToReaderPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"- окно обновится
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "Выдать"
        form.giveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pointID = form.pointIDTextField.getText();
                String readerCardNumber = form.cardNumberTextField.getText();
                String bookName = form.bookNameTextField.getText();
                String bookAuthor = form.bookAuthorTextField.getText();
                //проверка на пустоту хотя бы одного поля
                if (pointID.equals("") || readerCardNumber.equals("") || bookName.equals("") || bookAuthor.equals("")) {
                    JOptionPane.showMessageDialog(form.getGiveBookToReaderPanel(), "Введите корректные данные",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                } else {
                    String resultCode = model.giveBookToReader(pointID, readerCardNumber, bookName, bookAuthor);
                    //не существует такого пункта выдачи
                    if (resultCode.equals("0")) {
                        JOptionPane.showMessageDialog(form.getGiveBookToReaderPanel(), "Такого пункта выдачи не существует",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //книга не выдана на руки по какой-то причине, связанной с читателем
                    if (resultCode.equals("1")) {
                        JOptionPane.showMessageDialog(form.getGiveBookToReaderPanel(), "Книга не может быть выдана этому читателю",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //книга не выдана по какой-то причине, связанной с книгой
                    if (resultCode.equals("2")) {
                        JOptionPane.showMessageDialog(form.getGiveBookToReaderPanel(), "Эта книга не может быть выдана",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если прошли все проверки, возвращается номер книги, которую можно выдать
                    if (Integer.parseInt(resultCode) > 2){
                        JOptionPane.showMessageDialog(form.getGiveBookToReaderPanel(), "Можете передать читателю книгу с номером: "
                                        + resultCode,
                                "Успех!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                showGiveBookToReaderForm();
            }
        });
    }

    //форма действий с читателями
    public void showReaderActionForm() {
        //отрисовка формы
        final ReaderActionForm form = new ReaderActionForm();
        frame.setContentPane(form.getReaderActionPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"- форма обновится
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "Поставить отметку"
        form.signReaderAtPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignReaderAtPointForm();
            }
        });

        //нажатие на "Выдать заказ"
        form.giveOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveOrderedBooksToReaderForm();
            }
        });

        //нажатие на "Редактировать профиль"
        form.editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditReadersProfileForm();
            }
        });

        //нажатие на "Информация"
        form.readerInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderInformationForm();
            }
        });
    }

    //форма действий с книгами
    public void showBookActionForm() {
        //отрисовка формы
        final BookActionForm form = new BookActionForm();
        frame.setContentPane(form.getBookActionPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"- форма обновится
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });
    }

    //форма Информации по читателям
    public void showReaderInformationForm(){
        final ReaderInformationForm form = new ReaderInformationForm();
        frame.setContentPane(form.getReaderInformationPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //Нажатие на "Общий перечень читателей"
        //направит на форму фильтра перед показом перечня
        form.readersList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilterForReadersList();
            }
        });

        //Нажатие на "Список задолжников"
        //направит на форму фильтра перед показом списка
        form.debtorList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilterForDebtorListForm();
            }
        });
    }

    //форма фильтра для общего перечня читателей
    public void showFilterForReadersList(){
        //отрисовка формы
        final FilterForGeneralReadersListForm form = new FilterForGeneralReadersListForm();
        frame.setContentPane(form.getFilterForReadersListPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "Показать"
        form.showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pointId = form.pointIdTextField.getText();
                String chair = form.chairTextField.getText();
                String department = (String) form.departmentComboBox.getSelectedItem();
                if (department.equals("Выберите факультет")) {
                    department = "";
                }
                String group = form.groupTextField.getText();
                String[][] data = model.generalReadersListWithFilters(pointId, chair, department, group);
                showListOfReadersForm(data);
            }
        });
    }

    //форма вывода общего перечня читателей
    public void showListOfReadersForm(String[][] data){
        final ListOfReadersForm form = new ListOfReadersForm(data);
        frame.setContentPane(form.getListOfReadersPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });
    }

    //форма добавления отметки на пункте выдачи для читателя
    public void showSignReaderAtPointForm(){
        final SignReaderAtPoint form = new SignReaderAtPoint();
        frame.setContentPane((form.getMarkingReaderPanel()));
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "Отметить"
        form.markButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pointId = form.pointIdTextField.getText();
                String readerCardNumber = form.cardNumberTextField.getText();
                //проверяем, что оба поля заполнены
                if (pointId.equals("") || readerCardNumber.equals("")){
                    JOptionPane.showMessageDialog(form.getMarkingReaderPanel(), "Введите корректные данные!",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int resultCode = model.signReaderAtPoint(pointId, readerCardNumber);
                    //если вернулся 0, то такого пункта выдачи не существует
                    if (resultCode == 0){
                        JOptionPane.showMessageDialog(form.getMarkingReaderPanel(), "Такого пункта выдачи не существует!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 1, то такого читателя не существует
                    if (resultCode == 1){
                        JOptionPane.showMessageDialog(form.getMarkingReaderPanel(), "Такого читателя не существует!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 2, разовый читатель попытался отметиться на абонементе
                    if (resultCode == 2){
                        JOptionPane.showMessageDialog(form.getMarkingReaderPanel(), "Разовый читатель не может отметиться на абонементе!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 3, что-то пошло не так во время обновления БД
                    if (resultCode == 3){
                        JOptionPane.showMessageDialog(form.getMarkingReaderPanel(), "Что-то пошло не так!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 4, то отметка была успешно обновлена
                    if(resultCode == 4){
                        JOptionPane.showMessageDialog(form.getMarkingReaderPanel(), "Отметка успешно обновлена!",
                                "Успех!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (resultCode == 5){
                        JOptionPane.showMessageDialog(form.getMarkingReaderPanel(), "Отметка была создана!",
                                "Успех!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                showSignReaderAtPointForm();
            }
        });
    }

    //форма выдачи заказанных книг читателю
    public void showGiveOrderedBooksToReaderForm(){
        //отрисовка формы
        final GiveOrderedBooksToReaderForm form = new GiveOrderedBooksToReaderForm();
        frame.setContentPane(form.getGiveOrderedBooksToReaderPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "Выдать"
        form.giveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pointId = form.pointIdTextField.getText();
                String readerCardNumber = form.cardNumberTextField.getText();
                String bookId = form.bookIdTextField.getText();

                //проверяем, нет ли пустых полей
                if (pointId.equals("") || readerCardNumber.equals("") || bookId.equals("")){
                    JOptionPane.showMessageDialog(form.getGiveOrderedBooksToReaderPanel(), "Введите корректные данные!",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int resultCode = model.giveOrderedBookToReader(pointId, readerCardNumber, bookId);

                    //если вернулся 0, то нет такого активного заказа
                    if (resultCode == 0){
                        JOptionPane.showMessageDialog(form.getGiveOrderedBooksToReaderPanel(), "Нет такого активного заказа!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 1, то заказ находится на другом пункте выдачи
                    if (resultCode == 1){
                        JOptionPane.showMessageDialog(form.getGiveOrderedBooksToReaderPanel(), "Заказ находится на другом пункте выдачи!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 4, то у читателя нет отметки на этом пункте выдачи
                    if (resultCode == 4){
                        JOptionPane.showMessageDialog(form.getGiveOrderedBooksToReaderPanel(), "У читателя нет отметки на этом пункте выдачи!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 2, то что-то пошло не так
                    if (resultCode == 2){
                        JOptionPane.showMessageDialog(form.getGiveOrderedBooksToReaderPanel(), "Что-то пошло не так!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 3, то заказ может быть успешно выдан
                    if (resultCode == 3){
                        JOptionPane.showMessageDialog(form.getGiveOrderedBooksToReaderPanel(), "Передайте заказ читателю!",
                                "Успех!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                showGiveOrderedBooksToReaderForm();
            }
        });
    }

    public void showEditReadersProfileForm(){
        //отрисовка формы
        final EditReadersProfileForm form = new EditReadersProfileForm();
        frame.setContentPane(form.getEditReadersProfilePanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "Обновить"
        form.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //собираем данные с полей ввода
                String readerCardNumber = form.readerCardNumberTextField.getText();
                String surname = form.surnameTextField.getText();
                String name = form.nameTextField.getText();
                String fathername = form.fathernameTextField.getText();
                String position = (String) form.positionComboBox.getSelectedItem();
                String department = (String) form.departmentComboBox.getSelectedItem();
                String chair = form.chairTextField.getText();
                String group = form.groupTextField.getText();
                //если не была выбрана должность
                if(position.equals("Выберите должность")){
                    position = "";
                }
                //если не был выбран факультет
                if(department.equals("Выберите факультет")){
                    department = "";
                }

                //если не был введён номер читательского билета
                if (readerCardNumber.equals("")){
                    JOptionPane.showMessageDialog(form.getEditReadersProfilePanel(), "Введите номер читательского билета!",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int resultCode = model.editReadersProfile(readerCardNumber, surname, name, fathername, position, department, chair, group);
                    //если вернулся 0, то нет читателя с таким номером читательского билета
                    if(resultCode == 0){
                        JOptionPane.showMessageDialog(form.getEditReadersProfilePanel(), "Нет читателя с таким номером читательского билета!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулась 1, то введены не корректные данные
                    if(resultCode == 1){
                        JOptionPane.showMessageDialog(form.getEditReadersProfilePanel(), "Введите корректные данные!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 2, то что-то пошло не так
                    if(resultCode == 2){
                        JOptionPane.showMessageDialog(form.getEditReadersProfilePanel(), "Что-то пошло не так!",
                                "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }
                    //если вернулось 3, то обновление прошло успешно
                    if(resultCode == 3){
                        JOptionPane.showMessageDialog(form.getEditReadersProfilePanel(), "Обновление прошло успешно!",
                                "Успех!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                showEditReadersProfileForm();
            }
        });
    }

    //форма фильтра для списка задолжников
    public void showFilterForDebtorListForm(){
        //отриосовка формы
        final FilterForDebtorListForm form = new FilterForDebtorListForm();
        frame.setContentPane(form.getFilterForDebtorListPanel());
        frame.revalidate();

        //нажатие на "Добавить нового читателя"
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });

        //нажатие на "Добавить новую книгу"
        form.buttonAddNewBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewBookForm();
            }
        });

        //нажатие на "Выдать книгу читателю"
        form.buttonGiveBookToReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGiveBookToReaderForm();
            }
        });

        //нажатие на "Действия с читателями"
        form.buttonReaderAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReaderActionForm();
            }
        });

        //нажатие на "Действия с книгами"
        form.buttonBookAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookActionForm();
            }
        });

        //нажатие на "Показать"

    }
}