package com.staed;

import com.staed.delegate.Interpreter;

public class Client {
	private Interpreter interpreter;
	
    public static void main(String[] args) {
        Client client = new Client(new Interpreter());
        client.run();
    }
    
    public Client(Interpreter interpreter) {
    	this.interpreter = interpreter;
    }

    public void run() {
    }
}