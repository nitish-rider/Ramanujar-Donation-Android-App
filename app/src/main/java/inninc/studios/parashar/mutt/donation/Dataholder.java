package inninc.studios.parashar.mutt.donation;

public class Dataholder {

    String donatorName, donationAmt, mobileNum, address, date;
    long invoiceID;

    public Dataholder(String donatorName, String donationAmt, String mobileNum, String address, String date, long invID) {
        this.donatorName = donatorName;
        this.donationAmt = donationAmt;
        this.mobileNum = mobileNum;
        this.address = address;
        this.date = date;
        this.invoiceID = invID;
    }

    public Dataholder(String donatorName, String donationAmt, String mobileNum, String address, String date) {
        this.donatorName = donatorName;
        this.donationAmt = donationAmt;
        this.mobileNum = mobileNum;
        this.address = address;
        this.date = date;
    }

    public long getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(long invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }

    public String getDonationAmt() {
        return donationAmt;
    }

    public void setDonationAmt(String donationAmt) {
        this.donationAmt = donationAmt;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }
}
