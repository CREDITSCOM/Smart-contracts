package com.example.contract;

import com.credits.scapi.v3.SmartContract;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EscrowContract extends SmartContract {

    final private String administrator;

    private String depositor = "";
    private String beneficiary = "reciever_wallet";

    private BigDecimal sum = null;
    private Date date = null;
    private boolean is_closed= false;

    public EscrowContract() {
        administrator = initiator;
        final int unix_time = date_time;
        date = new Date((long)unix_time * 1000);

    }

    @Override
    public String payable(BigDecimal amount, byte[] userData) {
        if(isClosed()) {
            throw new RuntimeException("contract is closed");
        }
        if(!depositor.isEmpty()) {
            throw new RuntimeException("Depositor may be set only once");
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit sum must be positive");
        }
        depositor = initiator;
        sum = amount;
        return "accepted deposit " + String.valueOf(sum.doubleValue()) + " from " + depositor;
    }

    public String tryWithdraw() {
        if(isClosed()) {
            return "contract is closed";
        }
        if(beneficiary.isEmpty()) {
            return "beneficiary is not set";
        }
        if(date == null) {
            return "date to release deposit is not set";
        }
        if(sum == null) {
            return "deposit sum is not set";
        }
        Date now = new Date(getBlockchainTimeMills());
        if(date.before(now)) {
            BigDecimal balance = getBalance(contractAddress);
            if(sum.compareTo(balance) <= 0) {
                sum = balance;
                if(sum.doubleValue() < 0.001) {
                    is_closed = true;
                    return "deposit is empty";
                }
            }
            sendTransaction(contractAddress, beneficiary, sum.doubleValue(), null);
            sum = BigDecimal.ZERO;
            return "deposit released";
        }
        return "deposit is hold until " + printDate(date);
    }

    public String getDepositor() {
        if(depositor.isEmpty()) {
            return "not set yet";
        }
        return depositor;
    }

    public String getBeneficiary() {
        if(beneficiary.isEmpty()) {
            return "not set yet";
        }
        return beneficiary;
    }

    public String getDepositSum() {
        if(sum == null) {
            return "not set yet";
        }
        return String.valueOf(sum.doubleValue());
    }

    public String getReleaseDate() {
        if(date == null) {
            return "not set yet";
        }
        return printDate(date);
    }

    private String printDate(Date d) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy HH:mm::ss");
        return fmt.format(d);
    }

    public boolean isClosed() {
        return is_closed;
    }

}
