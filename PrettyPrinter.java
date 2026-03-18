package edu.pdx.cs.joy.kbarta;

import java.io.PrintWriter;
import java.io.Writer;
import java.time.Duration;
import edu.pdx.cs.joy.kbarta.PhoneBill;

/**
 * Pretty prints the phone bill, plus gets duration of calls in nice format.
 */
public class PrettyPrinter {
  /**
   * what the output is wrote to
   */
  private final Writer writer;

  /**
   * creates an object that write output to a writer
   * @param writer dest of writer
   */
  public PrettyPrinter(Writer writer) {
    this.writer = writer;
  }

  /**
   * This function will take a phone bill and give a nicely
   * formatted version of the bill
   * @param bill the bill to pretty print
   */
  public void dump(PhoneBill bill) {

    try (PrintWriter pw = new PrintWriter(this.writer)) {

      pw.println("Phone Bill for " + bill.getCustomer());
      pw.println();

      for (PhoneCall call : bill.getPhoneCalls()) {

        long minutes = Duration.between(
                call.getBeginTime(),
                call.getEndTime()
        ).toMinutes();

        pw.println("Call from " + call.getCaller()
                + " to " + call.getCallee());

        pw.println("  Start time: " + call.getBeginTime());
        pw.println("  End time:   " + call.getEndTime());
        pw.println("  Duration:   " + minutes + " minutes");
        pw.println();
      }

      pw.flush();
    }
  }
}