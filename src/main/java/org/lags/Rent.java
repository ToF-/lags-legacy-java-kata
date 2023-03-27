package org.lags;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Rent {
    public static void main(String[] args) throws IOException {
        int command = 1;
        Order order = null;
        String idt = "";
        String idt_ = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-r")) {
                command = 1;
            } else if (args[i].equals("-a")) {
                if (args.length < 5) {
                    System.err.println("usage: java lags.Rent -a ID START DURTN PRICE");
                    exit(1);
                } else {
                    command = 2;
                    idt = args[i + 1];
                    int start = Integer.parseInt(args[i + 2]);
                    int durn = Integer.parseInt(args[i + 3]);
                    int bid = Integer.parseInt(args[i + 4]);
                    order = new Order(idt, start, durn, bid);
                }
            } else if (args[i].equals("-d")) {
                if (args.length < 2) {
                    System.err.println("usage: Java lags.Rent -d ID");
                    exit(1);
                } else {
                    command = 4;
                    idt_ = args[i + 1];
                }
            } else if (args[i].equals("-l")) {
                command = 3;
            }
        }
        List<Order> orders;
        orders = new ArrayList<Order>();
        String fileName = System.getenv("LAGS_ORDER_FILE");
        if (fileName == null) {
            System.err.println("wich file ? set LAGS_ORDER_FILE var");
            exit(1);
        }
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).build();
            CSVIterator iterator = new CSVIterator(reader);
            boolean isFirstLine = true;
            for (CSVIterator it = iterator; it.hasNext(); ) {
                String[] line = it.next();
                if(! isFirstLine) {
                    idt = line[0].trim();
                    int start = Integer.parseInt(line[1].trim());
                    int durn = Integer.parseInt((line[2].trim()));
                    int bid = Integer.parseInt(line[3].trim());
                    Order o = new Order(idt, start, durn, bid);
                    orders.add(o);
                }
                isFirstLine = false;
            }
        } catch (IOException e) {
            System.err.println("problem reading file " + fileName );
            exit(1);
        } catch (CsvValidationException e) {
            System.err.println("problem reading file " + fileName );
            exit(1);
        }
        switch (command) {
            case 1 :
                Lags lags = new Lags(orders);
                int r = lags.revenue();
                System.out.println(r);
                break;
            case 2 :
                orders.add(order);
                saveOrders(orders, fileName);
                break;
            case 3 :
                System.out.format("%10s %8s %8s %8s\n",
                        "Id",
                        "Start",
                        "Duration",
                        "Price");
                for (Order o : orders) {
                    System.out.format("%10s %8d %8d %8d\n",
                            o.getId(),
                            o.getStart(),
                            o.getDuration(),
                            o.getPrice());
                }
                break;
            case 4 :
                final String id = idt_;
                List<Order> select = new ArrayList<Order>();
                for (Order o : orders) {
                    if(! o.getId().equals(idt)) {
                        select.add(o);
                    }
                }
                saveOrders(select, fileName);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }

        exit(0);
    }

    private static void saveOrders(List<Order> orders, String fileName) throws IOException {
        String[] line = new String[4];
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(fileName),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            line = "Id,Start,Duration,Price".split(",");
            writer.writeNext(line);
            for (Order o : orders) {
                line[0] = o.getId();
                line[1] = String.valueOf(o.getStart());
                line[2] = String.valueOf(o.getDuration());
                line[3] = String.valueOf(o.getPrice());
                writer.writeNext(line);
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("problem writing file: " + fileName);
            exit(1);
        }
    }
}
