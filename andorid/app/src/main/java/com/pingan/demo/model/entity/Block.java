package com.pingan.demo.model.entity;

import java.util.List;

/**
 * Created by guolidong752 on 17/5/9.
 */

public class Block {
    private String version;//版本
    private Timestamp timestamp;
    private List<Transaction> transactions;
    private String previousBlockHash;
    private NonHashData nonHashData;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public NonHashData getNonHashData() {
        return nonHashData;
    }

    public void setNonHashData(NonHashData nonHashData) {
        this.nonHashData = nonHashData;
    }

    public static class NonHashData{
        private Timestamp localLedgerCommitTimestamp;

        public Timestamp getLocalLedgerCommitTimestamp() {
            return localLedgerCommitTimestamp;
        }

        public void setLocalLedgerCommitTimestamp(Timestamp localLedgerCommitTimestamp) {
            this.localLedgerCommitTimestamp = localLedgerCommitTimestamp;
        }
    }
}
