package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/7/10.
 */
public class GetUserInfo {

    private String result;
    private List<CardInfo> cards;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<CardInfo> getCards() {
        return cards;
    }

    public void setCards(List<CardInfo> cards) {
        this.cards = cards;
    }

    public class CardInfo{
        String cardNo;
        String outboundDate;
        String returnDate;
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getOutboundDate() {
            return outboundDate;
        }

        public void setOutboundDate(String outboundDate) {
            this.outboundDate = outboundDate;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(String returnDate) {
            this.returnDate = returnDate;
        }
    }
}
