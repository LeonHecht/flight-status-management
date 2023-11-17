import utilities.TextTyper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    int typeDelay = 50;     // milliseconds

    public App() {

    }

    public void run() {
        // print welcome message
        this.printWelcome();

        // Get user choice whether to enter comparison-mode or query mode
        String choice = null;
        while (true) {
            try {
                choice = this.getChoice();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Input inválido. Intenta de nuevo.");
            }
        }

        this.printChoiceConfirm(choice);

        if (choice.equals("s")) {
            return;
        }
    }

    private void printWelcome(){
        String toWrite= "++++++++++++++++++++++++++++++++\n" +
                        "++++Flight Status Management++++\n" +
                        "++++++++++++++++++++++++++++++++\n";
        TextTyper.typeText(toWrite, this.typeDelay);
    }

    private String getChoice() throws InputMismatchException {
        String toWrite = "Para modo Query, pulse 'q'\n" +
                         "Para modo Comparación, pulse 'c'\n" +
                         "Si deseas salir, pulse 's'.";
        TextTyper.typeText(toWrite, this.typeDelay);

        Scanner scn = new Scanner(System.in);
        String choice = scn.nextLine();

        // check for potential input mismatch
        if (!choice.equals("q") && !choice.equals("c") && !choice.equals("s")) {
            throw new InputMismatchException("Input inválido");
        } else {
            return choice;
        }
    }

    private void printChoiceConfirm(String choice) {
        switch (choice) {
            case "q":
                TextTyper.typeText("Entrando Modo Query", this.typeDelay);
                break;
            case "c":
                TextTyper.typeText("Entrando Modo Comparación", this.typeDelay);
                break;
            case "s":
                TextTyper.typeText("Saliendo de la App", this.typeDelay);
                break;
        }
    }
}
