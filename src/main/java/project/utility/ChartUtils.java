package project.utility;

import project.model.Transaction;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.math.BigDecimal;
import java.time.ZoneId;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This class is a utility class that will be used to define the algorithm behind the
 * graphs we need to show in our view based on a list of transactions.
 */
public final class ChartUtils {

    /**
     * This constructor is private because this is a utility class.
     */
    private ChartUtils() {

    }

    /**
     * This method is used to create a new FreeChart which represents the transactions put as a parameter
     * by creating a graph that starts at 0 and adds or subtracts the amount and the fees to
     * recreate the variations of the balance.
     * 
     * @param transactions the transactions we will be using.
     * 
     * @return the JFreeChart containing the new chart.
     */
    public static JFreeChart createNetCashFlowChart(final List<Transaction> transactions) {

        InputValidator.checkArgument(transactions, p -> !p.isEmpty(), "List");

        // Create the dataset
        final XYSeries balanceSeries = createTransactionSeries(transactions);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(balanceSeries);

        // Create the chart
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Transactions chart",
            "Date",
            "Balance (€)",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );

        // Configure the chart
        final XYPlot plot = chart.getXYPlot();

        final DateAxis dateAxis = new DateAxis("Date");
        /*dateAxis.setDateFormatOverride(
            new SimpleDateFormat("dd/MM/yyyy")
        );*/

        plot.setDomainAxis(dateAxis);

        final NumberAxis balanceAxis =
            (NumberAxis) plot.getRangeAxis();

        balanceAxis.setNumberFormatOverride(
            NumberFormat.getCurrencyInstance(Locale.ITALY)
        );

        final XYLineAndShapeRenderer renderer =
            (XYLineAndShapeRenderer) plot.getRenderer();

        renderer.setDefaultShapesVisible(true);

        return chart;
        // Add the chart to the panel
        /*
        final ChartPanel chartComponent = new ChartPanel(chart);

        this.chartPanel.setLayout(new BorderLayout());
        this.chartPanel.add(chartComponent, BorderLayout.CENTER);

        this.chartPanel.revalidate();
        this.chartPanel.repaint();*/
    }

    private static XYSeries createTransactionSeries(final List<Transaction> transactions) {

        final List<Transaction> sortedTransactions = new ArrayList<>(transactions);

        sortedTransactions.sort(Comparator.comparing((@NonNull Transaction transaction) -> transaction.date()));

        final XYSeries series = new XYSeries("Net change");

        BigDecimal currentValue = BigDecimal.ZERO;

        // Starting point
        series.add(
            sortedTransactions.get(0).date()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli(),
                0.0
        );

    for (final Transaction transaction : sortedTransactions) {

        final BigDecimal variation = transaction.isPositive()
            ? transaction.amount()
            : transaction.amount().add(transaction.fee()).negate();

        currentValue = currentValue.add(variation);

        final long timestamp = transaction.date()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();

        series.add(timestamp, currentValue.doubleValue());
    }

    return series;
}
}
