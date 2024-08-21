package iplion.shift;

@FunctionalInterface
interface StatisticsPrinter {
    void run();
}

@FunctionalInterface
interface AddHandler {
    <T> void run(T t);
}

public class Statistics {
    private int longNumsTotal = 0;
    private long minLongVal;
    private long maxLongVal;
    private long longSum = 0;
    private int doubleNumsTotal = 0;
    private double minDoubleVal;
    private double maxDoubleVal;
    private double doubleSum = 0;
    private int stringsTotal = 0;
    private int minStringLength;
    private int maxStringLength;

    private final StatisticsPrinter statisticsPrinter;
    private final AddHandler addHandler;

    public Statistics(boolean fullStats) {
        if (fullStats) {
            statisticsPrinter = this::printFullStatistics;
            addHandler = this::addFullStat;
        } else {
            statisticsPrinter = this::printShortStatistics;
            addHandler = this::addShortStat;
        }
    }

    private <T> void addShortStat(T t) {
        if (t instanceof Long) {
            longNumsTotal++;

            return;
        }

        if (t instanceof Double) {
            doubleNumsTotal++;

            return;
        }

        stringsTotal++;
    }

    private <T> void addFullStat(T t) {
        if (t instanceof Long) {
            long l = (long) t;
            if (longSum == 0) {
                minLongVal = maxLongVal = l;
            } else {
                minLongVal = Math.min(minLongVal, l);
                maxLongVal = Math.max(maxLongVal, l);
            }
            longNumsTotal++;
            longSum += (long) t;

            return;
        }

        if (t instanceof Double) {
            double d = (double) t;
            if (doubleSum == 0) {
                minDoubleVal = maxDoubleVal = d;
            } else {
                minDoubleVal = Math.min(minDoubleVal, d);
                maxDoubleVal = Math.max(maxDoubleVal, d);
            }
            doubleNumsTotal++;
            doubleSum += (double) t;

            return;
        }

        String s = t.toString();
        if (stringsTotal == 0) {
            minStringLength = maxStringLength = s.length();
        } else {
            minStringLength = Math.min(minStringLength, s.length());
            maxStringLength = Math.max(maxStringLength, s.length());
        }
        stringsTotal++;
    }

    public <T> void addRecord(T t) {
        if (t instanceof Long) {
            addHandler.run((Long) t);

            return;
        }
        if (t instanceof Double) {
            addHandler.run((Double) t);

            return;
        }
        addHandler.run(t.toString());
    }

    private void printShortStatistics() {
        System.out.printf("""
                Integer numbers: %d
                Float numbers: %d
                Strings: %d
                Total lines processed: %d
                """,
            longNumsTotal, doubleNumsTotal, stringsTotal, longNumsTotal + doubleNumsTotal + stringsTotal
        );
    }

    private void printFullStatistics() {
        System.out.println("Integer numbers: " + longNumsTotal);
        if (longNumsTotal != 0) {
            System.out.println("MINimum integer value: " + minLongVal);
            System.out.println("MAXimum integer value: " + maxLongVal);
            System.out.println("Average integer value: " + (longSum / longNumsTotal));
            System.out.println("Sum of integers: " + longSum);
        }
        System.out.println("---");

        System.out.println("Float numbers: " + doubleNumsTotal);
        if (doubleNumsTotal != 0) {
            System.out.println("MINimum float value: " + minDoubleVal);
            System.out.println("MAXimum float value: " + maxDoubleVal);
            System.out.println("Average float value: " + (doubleSum / doubleNumsTotal));
            System.out.println("Sum of float: " + doubleSum);
        }
        System.out.println("---");

        System.out.println("Strings: " + stringsTotal);
        System.out.println("MINimum string length: " + minStringLength);
        System.out.println("MAXimum string length: " + maxStringLength);
        System.out.println("---");

        System.out.println("Total lines processed: " + (longNumsTotal + doubleNumsTotal + stringsTotal));
    }

    public void printStatistics() {
        statisticsPrinter.run();
    }
}
