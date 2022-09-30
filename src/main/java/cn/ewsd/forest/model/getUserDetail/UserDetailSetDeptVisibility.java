package cn.ewsd.forest.model.getUserDetail;

public class UserDetailSetDeptVisibility {
    private String iCanSeeWho;
    private String whoCanSeeMe;
    private int isClose;

    public String getiCanSeeWho() {
        return iCanSeeWho;
    }

    public void setiCanSeeWho(String iCanSeeWho) {
        this.iCanSeeWho = iCanSeeWho;
    }

    public String getWhoCanSeeMe() {
        return whoCanSeeMe;
    }

    public void setWhoCanSeeMe(String whoCanSeeMe) {
        this.whoCanSeeMe = whoCanSeeMe;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }
}
