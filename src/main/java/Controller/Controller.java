package Controller;

import Model.Model;
import View.AddNewReaderForm;
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

    public void showMainForm(){
        MainForm form = new MainForm();
        frame.setContentPane(form.getRootPanel());
        form.buttonAddNewReader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewReaderForm();
            }
        });
    }

    public void showAddNewReaderForm(){
        final AddNewReaderForm form = new AddNewReaderForm();
        frame.setContentPane(form.getAddNewReaderPanel());
        frame.revalidate();

        form.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String surname = form.surnameTextField.getText();
                String name = form.nameTextField.getText();
                String fathername = form.fathernameTextField.getText();
                String seasonTicket = (String)form.seasonTicketComboBox.getSelectedItem();
                if(seasonTicket.equals("Выберите использование") || seasonTicket.equals("Абонент")){
                    seasonTicket = "";
                }
                if(seasonTicket.equals("Читальный зал")){
                    seasonTicket = "0";
                }
                String position = (String)form.positionComboBox.getSelectedItem();
                if(position.equals("Выберите должность")){
                    position = "";
                }
                String department = (String)form.departmentComboBox.getSelectedItem();
                if(department.equals("Выберите факультет")){
                    department = "";
                }
                String chair = (String)form.chairComboBox.getSelectedItem();
                if(chair.equals("Выберите кафедру")){
                    chair = "";
                }
                String group = (String)form.groupComboBox.getSelectedItem();
                if(group.equals("Выберите группу")){
                    group = "";
                }
                model.addNewReader(surname, name, fathername, seasonTicket, position, department, chair, group);
            }
        });
    }
}
