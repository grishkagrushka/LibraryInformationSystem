package Controller;

import Model.Model;
import View.AddNewBookForm;
import View.AddNewReaderForm;
import View.GiveBookToReaderForm;
import View.MainForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    JFrame frame;
    Model model;

    public Controller(Model model){
        this.model = model;
    }

    public void start() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame = new JFrame();

        showMainForm();

        frame.setResizable(false);

        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Информационная система библиотеки");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //главная форма
    public void showMainForm(){
        //отрисовка формы
        MainForm form = new MainForm();
        frame.setContentPane(form.getRootPanel());

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
    }

    //форма добавления нового читателя
    public void showAddNewReaderForm(){
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

        //нажатие на "добавить"
        form.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //собираем данные с полей ввода
                String surname = form.surnameTextField.getText();
                String name = form.nameTextField.getText();
                String fathername = form.fathernameTextField.getText();
                String position = (String)form.positionComboBox.getSelectedItem();
                if(position.equals("Выберите должность")){
                    position = "";
                }
                String seasonTicket = "";
                if(position.equals("Слушатель ФПК") || position.equals("Стажёр") || position.equals("Абитуриент")){
                    seasonTicket = "0";
                }
                String department = (String)form.departmentComboBox.getSelectedItem();
                if(department.equals("Выберите факультет")||position.equals("Слушатель ПО")||position.equals("Слушатель ФПК")){
                    department = "";
                }
                String chair = form.chairTextField.getText();
                if (position.equals("Слушатель ПО")||position.equals("Слушатель ФПК")){
                    chair = "";
                }
                String group = form.groupTextField.getText();
                if(!position.equals("Студент")){
                    group = "";
                }
                //передаём управление в Model
                String result = model.addNewReader(surname, name, fathername, seasonTicket, position, department, chair, group);
                //сообщение об успехе или неудаче добавления
                if (!result.equals("0")){
                    JOptionPane.showMessageDialog(form.getAddNewReaderPanel(), "Новый читатель добавлен\nНомер читательского билета: " + result,
                            "Успех!", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(form.getAddNewReaderPanel(), "Проверьте корректность введённых данных",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                //перерисовываем форму, чтобы она обновилась
                showAddNewReaderForm();
            }
        });
    }

    //форма добавления новой книги
    public void showAddNewBookForm(){
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
                if(result) {
                    //сообщение об успехе или неудаче добавления
                    JOptionPane.showMessageDialog(form.getAddNewBookPanel(), "Новая книга добавлена",
                            "Успех!", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(form.getAddNewBookPanel(), "Проверьте корректность введённых данных. Дата получения книги должна быть указана в формате yyyy.MM.dd",
                            "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
                showAddNewBookForm();
            }
        });
    }

    //форма выдачи книги читателю
    public void showGiveBookToReaderForm(){
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
    }
}
