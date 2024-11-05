package model;

public class Theater {
    private String theaterName;
    private String theaterEmail;
    private String address;
    private String location;
    private String managerName;
    private String managerEmail;
    
    public Theater(String theaterName, String theaterEmail, String address, String location, String mName, String mEmail) {
        this.theaterName = theaterName;
        this.theaterEmail = theaterEmail;
        this.address = address;
        this.location = location;
        this.managerName = mName;
        this.managerEmail = mEmail;
    }
    public String getTheaterName()
    {
    		return theaterName;
    }
    public String getTheaterEmail()
    {
    		return theaterEmail;
    }
    public String getTheaterAddress()
    {
    		return address;
    }
    public String getTheaterLocation()
    {
    		return location;
    }
    public String getManagerName()
    {
    		return managerName;
    }
    public String getManagerEmail()
    {
    		return managerEmail;
    }
}

