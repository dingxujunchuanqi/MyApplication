package com.example.myapplication.bean;

import java.util.List;

/**
 * Created by dingxujun on 2018/4/30.
 *
 * @project MyApplication
 */

public class GroupLvBean {

    private List<ReturnDataBean> returnData;

    public List<ReturnDataBean> getReturnData() {
        return returnData;
    }

    public void setReturnData(List<ReturnDataBean> returnData) {
        this.returnData = returnData;
    }

    public static class ReturnDataBean {
        /**
         * orderinfo : [{"num":"1","price":"8.9","typename":"冷菜类","shopname":"南京店","vegetname":"凉拌黄瓜"},{"num":"2","price":"5.5","typename":"冷菜类","shopname":"南京店","vegetname":"花生米"},{"num":"3","price":"10.0","typename":"热菜类","shopname":"南京店","vegetname":"青椒土豆丝"}]
         * orderid : 123456789
         */

        private String orderid;
        private List<OrderinfoBean> orderinfo;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public List<OrderinfoBean> getOrderinfo() {
            return orderinfo;
        }

        public void setOrderinfo(List<OrderinfoBean> orderinfo) {
            this.orderinfo = orderinfo;
        }

        public static class OrderinfoBean {
            /**
             * num : 1
             * price : 8.9
             * typename : 冷菜类
             * shopname : 南京店
             * vegetname : 凉拌黄瓜
             */
            private String shopname;
            private String vegetname;

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getVegetname() {
                return vegetname;
            }

            public void setVegetname(String vegetname) {
                this.vegetname = vegetname;
            }
        }
    }
}
