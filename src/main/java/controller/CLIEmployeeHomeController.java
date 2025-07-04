package controller;

import factory.GraphicalFactory;

import java.util.Scanner;

public class CLIEmployeeHomeController  extends  HomeEmployeeController{


    @Override
    public void loadHome(){

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
                    break;
                case  "3":
                    //non implementato
                    break;

                case "4":
                    showMyAcc();
                    break;

                default:
                    System.out.println("Scelta non valida. Perfavore riprovare");

            }




        }


    }

    @Override
    public void showMyAcc() {
        GraphicalFactory factory = GraphicalFactory.getInstance();
        MyAccController acc = factory.createMyAccController();
        acc.startMyAcc();
    }


    @Override
    public void showReports() {
        GraphicalFactory graphicalFactory = GraphicalFactory.getInstance();
        ShowReportsController showReportsController = graphicalFactory.createShowReportsController();
        showReportsController.showReports();
        }

    @Override
    public void addEvent() {
        // non implementato
    }



}
