package com.staed;

import com.staed.beans.Request;
import java.util.Iterator;
import java.util.Scanner;

// TODO: Move to it's own file
enum LEVEL {
    verbose, warn, info
};

public class Interpreter {
    private Service service;
    private boolean stopped;
    // TODO: Scanner, etc.

    public Interpreter() {
        service = new Service();
        stopped = false;
    }

    // TODO: Return proper command
    public String parseConsole(String input) {
        String[] args = input.split("\\s");
        if (args == null) return "Invalid command";

        String ans = "";
        switch(args[0].toLowerCase()) {
            case "get":
                ans = _ConsoleGet(args);
                break;
            case "add":
                ans = _ConsoleAdd();
                break;
            case "exit":	// meant to fall through to case "quit"
            case "quit":
                stopped = true;
                ans = "Good bye.";
            default:
                break;
        }
        return ans;
    }

    private String _ConsoleAdd() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        sc.nextLine();
        // Some method to generic-ify the series of system.out and nextLine()?

        // Generate Request
        // Push Request
        sc.close();
        return "Added request";
    }

    private String _ConsoleGet(String[] args) {
        String ans = "Got:\n";
        if (args.length < 2) {
            // Maybe don't allow this
            Iterator<Request> it = service.getAllRequest();
            StringBuffer s = new StringBuffer();
            while (it.hasNext()) {
                s.append(it.next());
            }
            ans += s.toString();
        } else if (args[1].matches("\\d+")) {
            service.getRequest(Integer.parseInt(args[1]));
            ans += service.current().toString();
        } else {
            ans = "That's not a valid request index.";
        }
        return ans;
    }

    public boolean stopped() {
        return stopped;
    }

    // TODO: Handle webpage interactions
    public void parsePage() { }

    // TODO: Log
    public void log(LEVEL level, String info) { }
}