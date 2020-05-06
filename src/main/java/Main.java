import Controller.Controller;
import Model.Connector;
import Model.Model;


public class Main {
    public static void main(String[] args) throws Exception{
        Connector connector = new Connector();
        connector.startConnection();

        Model model = new Model(connector);

        Controller controller = new Controller(model);
        controller.start();

        //connector.closeConnection(connector.connection, connector.statement);//TODO: спросить у Кирюхи, как нормально закрывать соединение
    }
}
