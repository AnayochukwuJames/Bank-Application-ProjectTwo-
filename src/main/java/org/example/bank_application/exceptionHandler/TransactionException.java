package org.example.bank_application.exceptionHandler;

public class TransactionException extends RuntimeException{
    private String message;
    public  TransactionException(String message){
        super(message);
    }
}
