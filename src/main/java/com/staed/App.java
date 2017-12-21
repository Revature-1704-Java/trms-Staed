package com.staed;

import java.util.Scanner;

public class App {
    private Scanner scanner;
    private Interpreter interpreter;

    public App() {
        interpreter = new Interpreter();
        scanner = new Scanner(System.in);
    }

    public void run() {
        while(scanner.hasNextLine() && !interpreter.stopped()) {
            // TODO: Handle returned string
            System.out.println(interpreter.parseConsole(scanner.nextLine()));
        }
        scanner.close();
        interpreter.close();
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
