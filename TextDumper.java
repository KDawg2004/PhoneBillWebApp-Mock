package edu.pdx.cs.joy.kbarta;

import edu.pdx.cs.joy.PhoneBillDumper;
import edu.pdx.cs.joy.kbarta.PhoneBill;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * responsible for writing a phone bill to txt output.
 * handles phone bill objects
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {
  /** Writer used for output text*/
  private final Writer writer;

  /** Formatted for date and time*/
  private static final DateTimeFormatter FORMATTER =
          DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");

  /**
   * Constructor
   * @param writer Where phone bill is written to
   */
  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * Dumps phone bill to text format
   *
   * @param phoneBill The phone bill
   * @throws IOException If writing fails
   */
  @Override
  public void dump(PhoneBill phoneBill) throws IOException {
    writer.write(phoneBill.getCustomer() + "\n");

    for (PhoneCall call : phoneBill.getPhoneCalls()) {

      String begin = call.getBeginTime().format(FORMATTER);
      String end = call.getEndTime().format(FORMATTER);

      writer.write(call.getCaller() + " "
              + call.getCallee() + " "
              + begin + " "
              + end + "\n");
    }

    writer.flush();
  }

}
