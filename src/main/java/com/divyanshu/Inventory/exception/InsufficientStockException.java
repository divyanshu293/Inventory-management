package com.divyanshu.Inventory.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String msg) {super(msg);}
}
