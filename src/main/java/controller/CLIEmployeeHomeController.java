package controller;

import factory.GraphicalFactory;

import java.util.Scanner;

public class CLIEmployeeHomeController  extends  HomeEmployeeController{


        public CLIEmployeeHomeController() {
                home();
        }


    public void home(){

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("*************************");
            System.out.println("**   CivisAlertStaff   **");
            System.out.println("**        Home         **");
            System.out.println("*************************");
            System.out.println("1) Visualizza segnalazioni");
            System.out.println("2) Aggiungi evento");
            System.out.println("3) Visualizza eventi ");
            System.out.println("4) Il mio account");
            System.out.print("> ");
            String choice = sc.nextLine();

            switch(choice) {
                case "1":
                    showReports();
                    break;

                case "2":
                    addEvent();

                case  "3":
                    break;

                case "4":
                    break;

                default:
                    System.out.println("Scelta non valida. Perfavore riprovare");

            }




        }


    }


    @Override
    public void showReports() {

        GraphicalFactory graphicalFactory = GraphicalFactory.getInstance();
        ShowReportsController showReportsController = graphicalFactory.createShowReportsController();

        }

    @Override
    public void addEvent() {

    }
}
