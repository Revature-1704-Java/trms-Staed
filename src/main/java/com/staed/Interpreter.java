package com.staed;

import com.staed.beans.Request;

import java.sql.Date;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// TODO: Move to it's own file
enum LEVEL {
    verbose, warn, info
};

public class Interpreter {
    private Service service;
    private boolean stopped;
    private List<String> formats;
    private final String dateRegex = "2\\d\\d\\d-((0\\d)|(1[012]))-(([012]\\d)|([3][01]))";

    public Interpreter() {
        service = new Service();
        stopped = false;
        formats = Arrays.asList("Grade", "Presentation");
    }
    
    public void close() {
    	service.close();
    }

    public String parseConsole(String input) {
        String[] args = input.split("\\s");
        if (args == null) return "Invalid command";

        String ans = "";
        switch(args[0].toLowerCase()) {
        	case "login":
        		// TODO: make login with username / password
        		break;
            case "get":
                ans = _ConsoleGet(args);
                break;
            case "add":
                ans = _ConsoleAdd();
                break;
            case "change":
            	ans = _ConsoleModify(args);
            	break;
            case "view":
            	if (service.current() == null)
            		ans = "No currently loaded request";
            	else
            		ans = service.current().toString();
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
    
    @SuppressWarnings("unchecked")
	private<T> T _ConvertToGeneric(String input, Class<T> type) {
    	if (type.isAssignableFrom(String.class))
    		return type.cast(input);
    	else if (type == Date.class) {
    		return (T) new Date(Date.valueOf(input).getTime());
    	} else {
    		System.out.println("I don't know how to convert strings into " + type.getName());
    		return null;
    	}
    }
    
    private<T> T _ConsoleLoopUntilValid(Scanner sc, String field, String pattern, Class<T> type) {
    	System.out.println("Enter the " + field + ":");
    	String buffer = sc.nextLine().trim();
    	
    	while (!buffer.matches(pattern)) {
    		System.out.println("That isn't a valid " + field + "\nEnter a " + type.getName());
    		buffer = sc.nextLine().trim();
    	}
    	return _ConvertToGeneric(buffer, type);
    }
    
    private Float _ConsoleLoopUntilValidFloat(Scanner sc, String field) {
    	System.out.println("Enter the " + field + ":");
    	String buffer = sc.nextLine().trim();
    	
    	while (!buffer.matches("\\d+.?\\d{0,2}")) {
    		System.out.println("That isn't a valid " + field + "\nEnter a float.");
    		buffer = sc.nextLine().trim();
    	}
    	return Float.parseFloat(buffer);
    }
    
    private String _ConsoleIterator(Scanner sc, String field, Iterator<String> iter, String catchall) {
    	System.out.println("Enter the " + field + ":");
    	String buffer = sc.nextLine().trim();
    	
    	while (iter.hasNext()) {
    		if (buffer.equals(iter.next())) {
	    		return buffer;
	    	}
    	}
    	return catchall;
    }

    private Boolean _ConsoleScanBool(String input, String field) {
    	System.out.println("Is it " + field + "? (Yes/No");
    	return input.toLowerCase().trim().matches("((yes)|y)") ? true : false;
    }
    
    private String _ConsoleAdd() {
    	if (service.current() == null) {
    		Scanner sc = new Scanner(System.in);
    		
    		int eId = _ConsoleLoopUntilValid(sc, "employee id",
    				"\\d+", Integer.class);
    		Date date = _ConsoleLoopUntilValid(sc, "date of the event in yyyy-mm-dd", 
    				dateRegex, Date.class);
    		String loc =_ConsoleLoopUntilValid(sc, "location",
    				".*", String.class);
    		String desc = _ConsoleLoopUntilValid(sc, "description",
    				".*", String.class);
    		float cost = _ConsoleLoopUntilValidFloat(sc, "cost");
    		
    		String format = _ConsoleIterator(sc, "grading format",
    				formats.iterator(), "Grade");
    		String type = _ConsoleIterator(sc, " event type",
    				service.getTypes().getKeys().iterator(), "Other");
    		
    		String justify = _ConsoleLoopUntilValid(sc, "work-related justification",
    				".*", String.class);
    		String email = _ConsoleLoopUntilValid(sc, "email containing prior-approval",
    				".*", String.class);
    		int cutoff = _ConsoleLoopUntilValid(sc, "grade cutoff",
    				"\\d+", Integer.class);
    		
    		Boolean isUrgent = _ConsoleScanBool(sc.nextLine(), "urgent");
    		
    		sc.close();
    		
    		// Generate Request and Push it
    		service.generateRequest(eId, date, loc, desc, cost, format, type,
    			justify, email, false, false, false, cutoff, "open", isUrgent);
    		return service.pushRequest() ? "Added request" : "Failed to submit request";
    	} else {
    		return service.pushRequest() ? "Added request" : "Failed to submit request";
    	}
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
            
            if (service.current() != null)
            	ans += service.current().toString();
            else
            	ans = "That request doesn't exist in the system.";
        } else {
            ans = "That's not a valid request index.";
        }
        return ans;
    }
    
    private String _ConsoleModify(String[] args) {
    	String ans = null;
    	if (args.length < 2) {
    		ans = "You didn't specify what part of the request you wanted to change.";
    		ans += "\nTry one of these:\n" + service.getPossibleFields().toString();
    	} else {
    		List<String> fields = service.getPossibleFields();
    		if (fields == null)
    			return "Failed to get the names of changable fields.";
    		
    		if (fields.contains(args[1].toUpperCase())) {
    			Scanner sc = new Scanner(System.in);
    			
    			Request cur = service.current(); 
    			switch (args[1].toLowerCase()) {
    			case "eventtime":
    				Date newDate = _ConsoleLoopUntilValid(sc, 
    						"date of the event in yyyy-mm-dd",
    						dateRegex, Date.class);
    				cur.setEventDate(newDate);
    				break;
    			case "eventlocation":
    				String loc =_ConsoleLoopUntilValid(sc, "location",
    						".*", String.class);
    				cur.setLocation(loc);
    				break;
    			case "requestdescription":
    				String desc = _ConsoleLoopUntilValid(sc, "description",
    						".*", String.class);
    				cur.setDescription(desc);
    				break;
    			case "reimbursementcost":
    				float cost = _ConsoleLoopUntilValidFloat(sc, "cost");
    				cur.setCost(cost);
    				break;
    			case "gradingformat":
    				String format = _ConsoleIterator(sc, "grading format",
    						formats.iterator(), "Grade");
    				cur.setFormat(format);
    				break;
    			case "eventtypeid":
    				String type = _ConsoleIterator(sc, " event type",
    						service.getTypes().getKeys().iterator(), "Other");
    				cur.setEventName(type);
    				break;
    			case "workjustification":
    				String justify = _ConsoleLoopUntilValid(sc, "work-related justification",
    						".*", String.class);
    				cur.setJustification(justify);
    				break;
    			case "approvalemail":
    				String email = _ConsoleLoopUntilValid(sc, "email containing prior-approval",
    						".*", String.class);
    				cur.setApprovalEmail(email);
    				break;
    			case "directsupervisorapproved":
    				boolean superAppro = _ConsoleScanBool(sc.nextLine(),
						"approved by the Supervisor");
				cur.setOkdBySuper(superAppro);
    				break;
    			case "departmentheadapproved":
    				boolean headAppro = _ConsoleScanBool(sc.nextLine(),
    						"approved by the Department Head");
    				cur.setOkdByHead(headAppro);
    				break;
    			case "benefitscoordinatorapproved":
    				boolean benCoAppro = _ConsoleScanBool(sc.nextLine(),
    						"approved by the Benefits Coordinator");
    				cur.setOkdByBenCo(benCoAppro);
    				break;
    			case "gradecutoff":
    				int cutoff = _ConsoleLoopUntilValid(sc, "grade cutoff",
    	    				"\\d+", Integer.class);
    				cur.setGradeCutoff(cutoff);
    				break;
    			case "status":
    				Iterator<String> it = Arrays.asList("Open", "Processing", "Closed").iterator();
    				String status = _ConsoleIterator(sc, "status of the request", it, "Open");
    				cur.setStatus(status);
    				break;
    			case "urgent":
    				boolean urgent = _ConsoleScanBool(sc.nextLine(), "urgent");
    				cur.setUrgent(urgent);
    				break;
    			case "help":
    				ans = "\nThese are the valid fields:\n" + service.getPossibleFields().toString();
    				break;
    			default:
    				ans = "That isn't a valid field to change.";
    				break;
    			}
    		} else {
    			ans = "That isn't a valid field to change.";
    		}
    		
    	}
    	return ans;
    }

    public boolean stopped() {
        return stopped;
    }

    // TODO: Handle webpage interactions
    public void parsePage() {
    }

    // TODO: Log
    public void log(LEVEL level, String info) {
    }
}