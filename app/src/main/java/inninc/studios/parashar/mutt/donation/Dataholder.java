package inninc.studios.parashar.mutt.donation;

public class Dataholder {

    String donatorName ,donationAmt ,mobileNum ,address ;

    public Dataholder(String donatorName, String donationAmt, String mobileNum, String address) {
        this.donatorName = donatorName;
        this.donationAmt = donationAmt;
        this.mobileNum = mobileNum;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
