package com.bszeliga.rxjava.test;

import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by bszeliga on 1/24/14.
 */
public class SimpleExample implements Observer<String> {

    public SimpleExample(Observable observable) {
        observable.subscribe(this);
    }

    public static void main(String [] args) throws IOException {

        final PublishSubject publish = PublishSubject.create();
        final SimpleExample example = new SimpleExample(publish);

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        while (!s.equals("-1")) {
            System.out.println("Enter a string (-1 to exit): ");

            s = bufferRead.readLine();
            System.out.println(String.format("Value of %s  being published", s));

            publish.onNext(s);
        }

        publish.onCompleted();

        String last = "LAST STRING";
        System.out.println("publishing last value = " + last);
        publish.onNext(last);

        System.out.println("Exiting");
    }

    @Override
    public void onCompleted() {
        System.out.println("On Complete.");
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("Received throwable");
        e.printStackTrace();
    }

    @Override
    public void onNext(String args) {
        System.out.println("Received string = " + args.toString());
    }
}
