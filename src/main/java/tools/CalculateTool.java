package tools;

import client.ClientInfo;

/**
 * Created by tina on 16/4/28.
 */
public class CalculateTool {
    private double basicExpense = 25;
    private double totalConversationTime;
    private double expensePerMinute = 0.15;
    private ClientInfo client;
    private double discount;
    private double owedExpense = 0;

    public CalculateTool( double totalConversationTime, ClientInfo client ) {
        this.totalConversationTime = totalConversationTime;
        this.client = client;
        this.discount = calculateDiscount();
        this.owedExpense = this.client.getOwedThisYear() + 0.05 * this.client.getOwedBeforeYear();
    }

    public double calculateDiscount() {
        if ( totalConversationTime > 0 && totalConversationTime <= 60 ) {
            if ( client.getTimeOutCount() <= 1 ) {
                return 0.01;
            }
        } else if ( totalConversationTime > 60 && totalConversationTime <= 120 ) {
            if ( client.getTimeOutCount() <= 2 ) {
                return 0.015;
            }

        } else if ( totalConversationTime > 120 && totalConversationTime <= 180 ) {
            if ( client.getTimeOutCount() <= 3 ) {
                return 0.02;
            }

        } else if ( totalConversationTime > 180 && totalConversationTime <= 300 ) {
            if ( client.getTimeOutCount() <= 3 ) {
                return 0.025;
            }

        } else if ( totalConversationTime > 300 ) {
            if ( client.getTimeOutCount() <= 6 ) {
                return 0.03;
            }

        }
        return 0;
    }

    public double calculateExpense() {
        return owedExpense + basicExpense + expensePerMinute * totalConversationTime * ( 1 - discount );
    }
}
