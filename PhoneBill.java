package edu.pdx.cs.joy.kbarta;

import edu.pdx.cs.joy.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The class for the PhoneBill;
 * This class manages all phone calls users make and tracks them in a phone bill.
 * It has data members customer which keeps track of customer name, and then an array list
 * they have made from objects of the PhoneCall class
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    /**
     * customer name
     */
    private final String customer;

    /**
     * call list
     */
    private final Collection<PhoneCall> calls = new ArrayList<>();

    /**
     * Assigns a customer name to a phone bill
     * @param customer customers name
     */
    public PhoneBill(String customer) {
        this.customer = customer;
    }

    /**
     * Retrives the customers name
     * @return a string var with the customers name
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adds a phone call object to the array of phone call objects
     * @param call the call to be added
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    /**
     * Gets all the phone calls recorded in the array list
     * @return the phone call list
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls.stream().sorted().toList();
    }
}

