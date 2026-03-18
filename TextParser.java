package edu.pdx.cs.joy.kbarta;

import edu.pdx.cs.joy.ParserException;
import edu.pdx.cs.joy.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.pdx.cs.joy.kbarta.PhoneBill;

public class TextParser implements PhoneBillParser<PhoneBill> {
  /**
   * char stream which is read from
   */
  private final Reader reader;

  /**
   * put the date in correct format
   */
  private static final DateTimeFormatter FORMATTER =
          DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");

  /**
   * creates new object that reads the stream
   * @param reader holds the text
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * takes input from the stream and builds phonebill
   * @return Phonebill containing all essestial info
   * @throws ParserException if customer name is bad
   */
  @Override
  public PhoneBill parse() throws ParserException {
    try (BufferedReader br = new BufferedReader(this.reader)) {

      String customer = br.readLine();

      if (customer == null) {
        throw new ParserException("Empty file");
      }

      PhoneBill bill = new PhoneBill(customer);

      String line;
      while ((line = br.readLine()) != null) {

        String[] parts = line.split(" ");

        if (parts.length < 8) {
          throw new ParserException("Malformed phone call line: " + line);
        }

        String caller = parts[0];
        String callee = parts[1];

        String beginString = parts[2] + " " + parts[3] + " " + parts[4];
        String endString = parts[5] + " " + parts[6] + " " + parts[7];

        LocalDateTime begin = LocalDateTime.parse(beginString, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endString, FORMATTER);

        PhoneCall call = new PhoneCall(caller, callee, begin, end);
        bill.addPhoneCall(call);
      }

      return bill;

    } catch (IOException e) {
      throw new ParserException("Error reading input", e);
    }
  }
}
