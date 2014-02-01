package com.bszeliga.rxjava.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;
import rx.util.functions.Func1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by bszeliga on 1/24/14.
 */
public class SimpleExample implements Observer<Integer> {
    private final static Logger logger = LoggerFactory.getLogger(SimpleExample.class);

    public SimpleExample(Observable<Integer> observable) {
        observable.subscribe(this);
    }

    public static void main(String [] args) throws IOException {

        final PublishSubject publish = PublishSubject.create();
        final SimpleExample example = new SimpleExample(publish.map(new Func1<String, Integer>() {
            @Override
            public Integer call(String o) {
                return Integer.valueOf(o);
            }
        }));

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        while (!s.equals("-1")) {
            logger.info("Enter a string (-1 to exit): ");

            s = bufferRead.readLine();
            logger.info(String.format("Value of %s  being published", s));

            publish.onNext(s);
        }

        publish.onCompleted();

        String last = "LAST STRING";
        logger.info("publishing last value = " + last);
        publish.onNext(last);

        logger.info("Exiting");
    }

    @Override
    public void onCompleted() {
        logger.info("On Complete.");
    }

    @Override
    public void onError(Throwable e) {
        logger.error("onError", e);
    }

    @Override
    public void onNext(Integer args) {
        logger.info("Received integer = " + args.toString());
    }
}
