package Controller;

import Factory.GraphicalFactory;

import java.util.Scanner;

public class CLIHomeController  extends HomeController{

    private final Scanner sc = new Scanner(System.in);

    public CLIHomeController() {
        startMenu();
    }


    @Override
    public void onNewReport(){
        GraphicalFactory factory = GraphicalFactory.getInstance();
        ChooseMunicipalityController controller = factory.CreateMunicipalityController();

    }

    @Override
    public void showMyAcc() {
        GraphicalFactory factory = GraphicalFactory.getInstance();
        MyAccController acc = factory.createMyAccController();

    }

    public void startMenu(){
        while(true) {
            System.out.println("*************************");
            System.out.println("**     CivisAlert      **");
            System.out.println("**        Home         **");
            System.out.println("*************************");
            System.out.println("1) Nuova segnalazione");
            System.out.println("2) Visualizza le tue segnalazioni");
            System.out.println("3) Visualizza eventi ");
            System.out.println("4) Il mio account");
            System.out.print("> ");
            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1":
                    onNewReport();
                    break;
                case "2":
                    break;

                case "3":
                    break;

                case "4":
                    showMyAcc();
                    break;

                default:
                    System.out.println("scelta invalida. Perfavore riprovare");
            }
        }

    }


}
