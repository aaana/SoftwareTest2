package client;

import tools.CalculateTool;

/**
 * Created by tina on 16/4/28.
 */
public class ClientInfo {
    private int clientId;
    private double owedBeforeYear = 0;
    private double owedThisYear = 0;
    private double timeOutCount = 0;

    private boolean validFlag = true;

    public ClientInfo() {
    }

    public ClientInfo(int clientId, double owedBeforeYear, double owedThisYear, double timeOutCount) {
        this.clientId = clientId;
        this.owedBeforeYear = owedBeforeYear;
        this.owedThisYear = owedThisYear;
        this.timeOutCount = timeOutCount;

        if ( this.owedThisYear < 0 || this.owedBeforeYear < 0 || timeOutCount < 0 || timeOutCount > 11 ) {
            validFlag = false;
        }
    }

    public ClientInfo( ClientInfo client ) {
        this.clientId = client.getClientId();
        this.owedBeforeYear = client.getOwedBeforeYear();
        this.owedThisYear = client.getOwedThisYear();
        this.timeOutCount = client.getTimeOutCount();
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getOwedBeforeYear() {
        return owedBeforeYear;
    }

    public void setOwedBeforeYear(double owedBeforeYear) {
        this.owedBeforeYear = owedBeforeYear;
    }

    public double getOwedThisYear() {
        return owedThisYear;
    }

    public void setOwedThisYear(double owedThisYear) {
        this.owedThisYear = owedThisYear;
    }

    public double getTimeOutCount() {
        return timeOutCount;
    }

    public void setTimeOutCount(double timeOutCount) {
        this.timeOutCount = timeOutCount;
    }

    public boolean updateInfoEveryMonth( double conversationTime ) {
        if ( owedBeforeYear > 0 && owedThisYear > 0 ) {
            timeOutCount += 1;
        }
        return updateInfo( conversationTime );
    }

    public boolean updateInfo( double conversationTime ) {
        if ( this.validFlag && conversationTime >= 0 && conversationTime <= 44640 ) {
            CalculateTool cTool = new CalculateTool( conversationTime, this );
            owedThisYear += cTool.calculateExpense();
            return true;
        }
        return false;
    }
}
