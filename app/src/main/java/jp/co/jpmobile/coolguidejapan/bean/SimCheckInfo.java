package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/7/11.
 */
public class SimCheckInfo {

    /**
     * available_recharge_plans : [{"model":"prepaid.rechargeplantype","pk":1,"fields":{"mmt_recharge_code":"8R160","recharge_days":7,"recharge_total_usage":"1.0GB","recharge_price_jpy":"1000.00","description_english":"Pre paid Recharge: 7 Days 1GB","description_japanese":"初プ リチャージオプション7日1GB","description_taiwanese":"","description_chinese":"","short_description_english":"","short_description_japanese":"初ﾌﾟﾘﾁｬｰｼﾞｵﾌﾟｼｮﾝ7d1G","short_description_taiwanese":"","short_description_chinese":"","last_modified":"2016-06-03T14:53:51.468Z"}},{"model":"prepaid.rechargeplantype","pk":2,"fields":{"mmt_recharge_code":"8R161","recharge_days":7,"recharge_total_usage":"1.0MB","recharge_price_jpy":"1000.00","description_english":"Pre paid Recharge: 7 Days","description_japanese":"初プ リチャージオプション7日1MB","description_taiwanese":"","description_chinese":"","short_description_english":"","short_description_japanese":"初ﾌﾟﾘﾁｬｰｼﾞｵﾌﾟｼｮﾝ7d1M","short_description_taiwanese":"","short_description_chinese":"","last_modified":"2016-06-03T14:53:57.919Z"}},{"model":"prepaid.rechargeplantype","pk":3,"fields":{"mmt_recharge_code":"8R162","recharge_days":30,"recharge_total_usage":"2.0GB","recharge_price_jpy":"1000.00","description_english":"Pre paid Recharge: 30 Days 2GB","description_japanese":"初プ リチャージオプション30日2GB","description_taiwanese":"","description_chinese":"","short_description_english":"","short_description_japanese":"初ﾌﾟﾘﾁｬｰｼﾞｵﾌﾟｼｮ30d2G","short_description_taiwanese":"","short_description_chinese":"","last_modified":"2016-06-03T14:54:03.020Z"}},{"model":"prepaid.rechargeplantype","pk":4,"fields":{"mmt_recharge_code":"8R163","recharge_days":30,"recharge_total_usage":"1.0MB","recharge_price_jpy":"1000.00","description_english":"Pre paid Recharge: 30 Days","description_japanese":"初プ リチャージオプション30日1MB","description_taiwanese":"","description_chinese":"","short_description_english":"","short_description_japanese":"初ﾌﾟﾘﾁｬｰｼﾞｵﾌﾟｼｮ30d1M","short_description_taiwanese":"","short_description_chinese":"","last_modified":"2016-06-03T14:54:19.420Z"}}]
     * activated_card : [{"model":"prepaid.activatedcard","pk":127153,"fields":{"first_connection_date":"2016-06-30","last_connection_time":"2016-07-06T08:30:01.489Z","plan_expiry_date":"2016-07-07"}}]
     * usages : [{"model":"prepaid.usage","pk":2027640,"fields":{"usage_date":"2016-07-08","data_multiplier_usage_in_mb":0,"connected_time":"0:00:00"}},{"model":"prepaid.usage","pk":2022932,"fields":{"usage_date":"2016-07-07","data_multiplier_usage_in_mb":0,"connected_time":"0:00:00"}},{"model":"prepaid.usage","pk":2018272,"fields":{"usage_date":"2016-07-06","data_multiplier_usage_in_mb":220.9,"connected_time":"17:34:17"}},{"model":"prepaid.usage","pk":2013718,"fields":{"usage_date":"2016-07-05","data_multiplier_usage_in_mb":513.5,"connected_time":"23:18:26"}},{"model":"prepaid.usage","pk":2009143,"fields":{"usage_date":"2016-07-04","data_multiplier_usage_in_mb":553.9,"connected_time":"23:21:53"}},{"model":"prepaid.usage","pk":2004616,"fields":{"usage_date":"2016-07-03","data_multiplier_usage_in_mb":300.6,"connected_time":"11:31:31"}},{"model":"prepaid.usage","pk":1999987,"fields":{"usage_date":"2016-07-02","data_multiplier_usage_in_mb":5.2,"connected_time":"0:02:28"}},{"model":"prepaid.usage","pk":1995404,"fields":{"usage_date":"2016-07-01","data_multiplier_usage_in_mb":475.2,"connected_time":"15:56:32"}},{"model":"prepaid.usage","pk":1994065,"fields":{"usage_date":"2016-06-30","data_multiplier_usage_in_mb":347.4,"connected_time":"6:52:30"}}]
     * data_multiplier_usage_daybefore : 0
     * error_msg :
     * error_msg_taiwanese :
     * data_multiplier_usage_yesterday : 0
     * data_multiplier_usage_today : 0
     * error_msg_chinese :
     * issued_card : [{"model":"prepaid.issuedcard","pk":114919,"fields":{"iccid":"DN0504606174910","tel_no":"7041540351","apn_grid":"1@jpmob","plan_type":{"custom_id":"F","total_usage_limit":"3.0GB","plan_description":"8 days : 3.0GB LTE usage limit + unlimited data with 256kbps","plan_description_chinese":"8天3.0GB+无限流量套餐","daily_usage_limit":"205MB","data_multiplier":1.8,"plan_days":7,"sms_compatible":false,"plan_description_taiwanese":"8天3.0GB+降速吃到飽","mmt_plan_name":"N+7日間(205MB/d)","id":6},"card_request_date":"2016-04-20","mmt_card_activation_date":"2016-04-20","mmt_card_delivery_date":"2016-03-28","mmt_card_expiry_date":"2016-09-24","person_in_charge":"周","agent":{"username":"P100","name":"上海　周さん","id":20},"tel_no_with_zero_prefix":"07041540351"}}]
     * remaining_data : 2871
     * tel_no_or_iccid : 07041540351
     */

    private String data_multiplier_usage_daybefore;
    private String error_msg;
    private String error_msg_taiwanese;
    private String data_multiplier_usage_yesterday;
    private String data_multiplier_usage_today;
    private String error_msg_chinese;
    private String remaining_data;
    private String tel_no_or_iccid;
    /**
     * model : prepaid.rechargeplantype
     * pk : 1
     * fields : {"mmt_recharge_code":"8R160","recharge_days":7,"recharge_total_usage":"1.0GB","recharge_price_jpy":"1000.00","description_english":"Pre paid Recharge: 7 Days 1GB","description_japanese":"初プ リチャージオプション7日1GB","description_taiwanese":"","description_chinese":"","short_description_english":"","short_description_japanese":"初ﾌﾟﾘﾁｬｰｼﾞｵﾌﾟｼｮﾝ7d1G","short_description_taiwanese":"","short_description_chinese":"","last_modified":"2016-06-03T14:53:51.468Z"}
     */

    private List<AvailableRechargePlansBean> available_recharge_plans;
    /**
     * model : prepaid.activatedcard
     * pk : 127153
     * fields : {"first_connection_date":"2016-06-30","last_connection_time":"2016-07-06T08:30:01.489Z","plan_expiry_date":"2016-07-07"}
     */

    private List<ActivatedCardBean> activated_card;
    /**
     * model : prepaid.usage
     * pk : 2027640
     * fields : {"usage_date":"2016-07-08","data_multiplier_usage_in_mb":0,"connected_time":"0:00:00"}
     */

    private List<UsagesBean> usages;
    /**
     * model : prepaid.issuedcard
     * pk : 114919
     * fields : {"iccid":"DN0504606174910","tel_no":"7041540351","apn_grid":"1@jpmob","plan_type":{"custom_id":"F","total_usage_limit":"3.0GB","plan_description":"8 days : 3.0GB LTE usage limit + unlimited data with 256kbps","plan_description_chinese":"8天3.0GB+无限流量套餐","daily_usage_limit":"205MB","data_multiplier":1.8,"plan_days":7,"sms_compatible":false,"plan_description_taiwanese":"8天3.0GB+降速吃到飽","mmt_plan_name":"N+7日間(205MB/d)","id":6},"card_request_date":"2016-04-20","mmt_card_activation_date":"2016-04-20","mmt_card_delivery_date":"2016-03-28","mmt_card_expiry_date":"2016-09-24","person_in_charge":"周","agent":{"username":"P100","name":"上海　周さん","id":20},"tel_no_with_zero_prefix":"07041540351"}
     */

    private List<IssuedCardBean> issued_card;

    public String getData_multiplier_usage_daybefore() {
        return data_multiplier_usage_daybefore;
    }

    public void setData_multiplier_usage_daybefore(String data_multiplier_usage_daybefore) {
        this.data_multiplier_usage_daybefore = data_multiplier_usage_daybefore;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getError_msg_taiwanese() {
        return error_msg_taiwanese;
    }

    public void setError_msg_taiwanese(String error_msg_taiwanese) {
        this.error_msg_taiwanese = error_msg_taiwanese;
    }

    public String getData_multiplier_usage_yesterday() {
        return data_multiplier_usage_yesterday;
    }

    public void setData_multiplier_usage_yesterday(String data_multiplier_usage_yesterday) {
        this.data_multiplier_usage_yesterday = data_multiplier_usage_yesterday;
    }

    public String getData_multiplier_usage_today() {
        return data_multiplier_usage_today;
    }

    public void setData_multiplier_usage_today(String data_multiplier_usage_today) {
        this.data_multiplier_usage_today = data_multiplier_usage_today;
    }

    public String getError_msg_chinese() {
        return error_msg_chinese;
    }

    public void setError_msg_chinese(String error_msg_chinese) {
        this.error_msg_chinese = error_msg_chinese;
    }

    public String getRemaining_data() {
        return remaining_data;
    }

    public void setRemaining_data(String remaining_data) {
        this.remaining_data = remaining_data;
    }

    public String getTel_no_or_iccid() {
        return tel_no_or_iccid;
    }

    public void setTel_no_or_iccid(String tel_no_or_iccid) {
        this.tel_no_or_iccid = tel_no_or_iccid;
    }

    public List<AvailableRechargePlansBean> getAvailable_recharge_plans() {
        return available_recharge_plans;
    }

    public void setAvailable_recharge_plans(List<AvailableRechargePlansBean> available_recharge_plans) {
        this.available_recharge_plans = available_recharge_plans;
    }

    public List<ActivatedCardBean> getActivated_card() {
        return activated_card;
    }

    public void setActivated_card(List<ActivatedCardBean> activated_card) {
        this.activated_card = activated_card;
    }

    public List<UsagesBean> getUsages() {
        return usages;
    }

    public void setUsages(List<UsagesBean> usages) {
        this.usages = usages;
    }

    public List<IssuedCardBean> getIssued_card() {
        return issued_card;
    }

    public void setIssued_card(List<IssuedCardBean> issued_card) {
        this.issued_card = issued_card;
    }

    public static class AvailableRechargePlansBean {
        private String model;
        private String pk;
        /**
         * mmt_recharge_code : 8R160
         * recharge_days : 7
         * recharge_total_usage : 1.0GB
         * recharge_price_jpy : 1000.00
         * description_english : Pre paid Recharge: 7 Days 1GB
         * description_japanese : 初プ リチャージオプション7日1GB
         * description_taiwanese :
         * description_chinese :
         * short_description_english :
         * short_description_japanese : 初ﾌﾟﾘﾁｬｰｼﾞｵﾌﾟｼｮﾝ7d1G
         * short_description_taiwanese :
         * short_description_chinese :
         * last_modified : 2016-06-03T14:53:51.468Z
         */

        private FieldsBean fields;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }

        public FieldsBean getFields() {
            return fields;
        }

        public void setFields(FieldsBean fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            private String mmt_recharge_code;
            private String recharge_days;
            private String recharge_total_usage;
            private String recharge_price_jpy;
            private String description_english;
            private String description_japanese;
            private String description_taiwanese;
            private String description_chinese;
            private String short_description_english;
            private String short_description_japanese;
            private String short_description_taiwanese;
            private String short_description_chinese;
            private String last_modified;

            public String getMmt_recharge_code() {
                return mmt_recharge_code;
            }

            public void setMmt_recharge_code(String mmt_recharge_code) {
                this.mmt_recharge_code = mmt_recharge_code;
            }

            public String getRecharge_days() {
                return recharge_days;
            }

            public void setRecharge_days(String recharge_days) {
                this.recharge_days = recharge_days;
            }

            public String getRecharge_total_usage() {
                return recharge_total_usage;
            }

            public void setRecharge_total_usage(String recharge_total_usage) {
                this.recharge_total_usage = recharge_total_usage;
            }

            public String getRecharge_price_jpy() {
                return recharge_price_jpy;
            }

            public void setRecharge_price_jpy(String recharge_price_jpy) {
                this.recharge_price_jpy = recharge_price_jpy;
            }

            public String getDescription_english() {
                return description_english;
            }

            public void setDescription_english(String description_english) {
                this.description_english = description_english;
            }

            public String getDescription_japanese() {
                return description_japanese;
            }

            public void setDescription_japanese(String description_japanese) {
                this.description_japanese = description_japanese;
            }

            public String getDescription_taiwanese() {
                return description_taiwanese;
            }

            public void setDescription_taiwanese(String description_taiwanese) {
                this.description_taiwanese = description_taiwanese;
            }

            public String getDescription_chinese() {
                return description_chinese;
            }

            public void setDescription_chinese(String description_chinese) {
                this.description_chinese = description_chinese;
            }

            public String getShort_description_english() {
                return short_description_english;
            }

            public void setShort_description_english(String short_description_english) {
                this.short_description_english = short_description_english;
            }

            public String getShort_description_japanese() {
                return short_description_japanese;
            }

            public void setShort_description_japanese(String short_description_japanese) {
                this.short_description_japanese = short_description_japanese;
            }

            public String getShort_description_taiwanese() {
                return short_description_taiwanese;
            }

            public void setShort_description_taiwanese(String short_description_taiwanese) {
                this.short_description_taiwanese = short_description_taiwanese;
            }

            public String getShort_description_chinese() {
                return short_description_chinese;
            }

            public void setShort_description_chinese(String short_description_chinese) {
                this.short_description_chinese = short_description_chinese;
            }

            public String getLast_modified() {
                return last_modified;
            }

            public void setLast_modified(String last_modified) {
                this.last_modified = last_modified;
            }
        }
    }

    public static class ActivatedCardBean {
        private String model;
        private String pk;
        /**
         * first_connection_date : 2016-06-30
         * last_connection_time : 2016-07-06T08:30:01.489Z
         * plan_expiry_date : 2016-07-07
         */

        private FieldsBean fields;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }

        public FieldsBean getFields() {
            return fields;
        }

        public void setFields(FieldsBean fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            private String first_connection_date;
            private String last_connection_time;
            private String plan_expiry_date;

            public String getFirst_connection_date() {
                return first_connection_date;
            }

            public void setFirst_connection_date(String first_connection_date) {
                this.first_connection_date = first_connection_date;
            }

            public String getLast_connection_time() {
                return last_connection_time;
            }

            public void setLast_connection_time(String last_connection_time) {
                this.last_connection_time = last_connection_time;
            }

            public String getPlan_expiry_date() {
                return plan_expiry_date;
            }

            public void setPlan_expiry_date(String plan_expiry_date) {
                this.plan_expiry_date = plan_expiry_date;
            }
        }
    }

    public static class UsagesBean {
        private String model;
        private String pk;
        /**
         * usage_date : 2016-07-08
         * data_multiplier_usage_in_mb : 0.0
         * connected_time : 0:00:00
         */

        private FieldsBean fields;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }

        public FieldsBean getFields() {
            return fields;
        }

        public void setFields(FieldsBean fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            private String usage_date;
            private String data_multiplier_usage_in_mb;
            private String connected_time;

            public String getUsage_date() {
                return usage_date;
            }

            public void setUsage_date(String usage_date) {
                this.usage_date = usage_date;
            }

            public String getData_multiplier_usage_in_mb() {
                return data_multiplier_usage_in_mb;
            }

            public void setData_multiplier_usage_in_mb(String data_multiplier_usage_in_mb) {
                this.data_multiplier_usage_in_mb = data_multiplier_usage_in_mb;
            }

            public String getConnected_time() {
                return connected_time;
            }

            public void setConnected_time(String connected_time) {
                this.connected_time = connected_time;
            }
        }
    }

    public static class IssuedCardBean {
        private String model;
        private String pk;
        /**
         * iccid : DN0504606174910
         * tel_no : 7041540351
         * apn_grid : 1@jpmob
         * plan_type : {"custom_id":"F","total_usage_limit":"3.0GB","plan_description":"8 days : 3.0GB LTE usage limit + unlimited data with 256kbps","plan_description_chinese":"8天3.0GB+无限流量套餐","daily_usage_limit":"205MB","data_multiplier":1.8,"plan_days":7,"sms_compatible":false,"plan_description_taiwanese":"8天3.0GB+降速吃到飽","mmt_plan_name":"N+7日間(205MB/d)","id":6}
         * card_request_date : 2016-04-20
         * mmt_card_activation_date : 2016-04-20
         * mmt_card_delivery_date : 2016-03-28
         * mmt_card_expiry_date : 2016-09-24
         * person_in_charge : 周
         * agent : {"username":"P100","name":"上海　周さん","id":20}
         * tel_no_with_zero_prefix : 07041540351
         */

        private FieldsBean fields;

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
            this.pk = pk;
        }

        public FieldsBean getFields() {
            return fields;
        }

        public void setFields(FieldsBean fields) {
            this.fields = fields;
        }

        public static class FieldsBean {
            private String iccid;
            private String tel_no;
            private String apn;
            /**
             * custom_id : F
             * total_usage_limit : 3.0GB
             * plan_description : 8 days : 3.0GB LTE usage limit + unlimited data with 256kbps
             * plan_description_chinese : 8天3.0GB+无限流量套餐
             * daily_usage_limit : 205MB
             * data_multiplier : 1.8
             * plan_days : 7
             * sms_compatible : false
             * plan_description_taiwanese : 8天3.0GB+降速吃到飽
             * mmt_plan_name : N+7日間(205MB/d)
             * id : 6
             */

            private PlanTypeBean plan_type;
            private String card_request_date;
            private String mmt_card_activation_date;
            private String mmt_card_delivery_date;
            private String mmt_card_expiry_date;
            private String person_in_charge;
            /**
             * username : P100
             * name : 上海　周さん
             * id : 20
             */

            private AgentBean agent;
            private String tel_no_with_zero_prefix;

            public String getIccid() {
                return iccid;
            }

            public void setIccid(String iccid) {
                this.iccid = iccid;
            }

            public String getTel_no() {
                return tel_no;
            }

            public void setTel_no(String tel_no) {
                this.tel_no = tel_no;
            }

            public String getApn() {
                return apn;
            }

            public void setApn(String apn) {
                this.apn = apn;
            }

            public PlanTypeBean getPlan_type() {
                return plan_type;
            }

            public void setPlan_type(PlanTypeBean plan_type) {
                this.plan_type = plan_type;
            }

            public String getCard_request_date() {
                return card_request_date;
            }

            public void setCard_request_date(String card_request_date) {
                this.card_request_date = card_request_date;
            }

            public String getMmt_card_activation_date() {
                return mmt_card_activation_date;
            }

            public void setMmt_card_activation_date(String mmt_card_activation_date) {
                this.mmt_card_activation_date = mmt_card_activation_date;
            }

            public String getMmt_card_delivery_date() {
                return mmt_card_delivery_date;
            }

            public void setMmt_card_delivery_date(String mmt_card_delivery_date) {
                this.mmt_card_delivery_date = mmt_card_delivery_date;
            }

            public String getMmt_card_expiry_date() {
                return mmt_card_expiry_date;
            }

            public void setMmt_card_expiry_date(String mmt_card_expiry_date) {
                this.mmt_card_expiry_date = mmt_card_expiry_date;
            }

            public String getPerson_in_charge() {
                return person_in_charge;
            }

            public void setPerson_in_charge(String person_in_charge) {
                this.person_in_charge = person_in_charge;
            }

            public AgentBean getAgent() {
                return agent;
            }

            public void setAgent(AgentBean agent) {
                this.agent = agent;
            }

            public String getTel_no_with_zero_prefix() {
                return tel_no_with_zero_prefix;
            }

            public void setTel_no_with_zero_prefix(String tel_no_with_zero_prefix) {
                this.tel_no_with_zero_prefix = tel_no_with_zero_prefix;
            }

            public static class PlanTypeBean {
                private String custom_id;
                private String total_usage_limit;
                private String plan_description;
                private String plan_description_chinese;
                private String daily_usage_limit;
                private String data_multiplier;
                private String plan_days;
                private String sms_compatible;
                private String plan_description_taiwanese;
                private String mmt_plan_name;
                private String id;

                public String getCustom_id() {
                    return custom_id;
                }

                public void setCustom_id(String custom_id) {
                    this.custom_id = custom_id;
                }

                public String getTotal_usage_limit() {
                    return total_usage_limit;
                }

                public void setTotal_usage_limit(String total_usage_limit) {
                    this.total_usage_limit = total_usage_limit;
                }

                public String getPlan_description() {
                    return plan_description;
                }

                public void setPlan_description(String plan_description) {
                    this.plan_description = plan_description;
                }

                public String getPlan_description_chinese() {
                    return plan_description_chinese;
                }

                public void setPlan_description_chinese(String plan_description_chinese) {
                    this.plan_description_chinese = plan_description_chinese;
                }

                public String getDaily_usage_limit() {
                    return daily_usage_limit;
                }

                public void setDaily_usage_limit(String daily_usage_limit) {
                    this.daily_usage_limit = daily_usage_limit;
                }

                public String getData_multiplier() {
                    return data_multiplier;
                }

                public void setData_multiplier(String data_multiplier) {
                    this.data_multiplier = data_multiplier;
                }

                public String getPlan_days() {
                    return plan_days;
                }

                public void setPlan_days(String plan_days) {
                    this.plan_days = plan_days;
                }

                public String getSms_compatible() {
                    return sms_compatible;
                }

                public void setSms_compatible(String sms_compatible) {
                    this.sms_compatible = sms_compatible;
                }

                public String getPlan_description_taiwanese() {
                    return plan_description_taiwanese;
                }

                public void setPlan_description_taiwanese(String plan_description_taiwanese) {
                    this.plan_description_taiwanese = plan_description_taiwanese;
                }

                public String getMmt_plan_name() {
                    return mmt_plan_name;
                }

                public void setMmt_plan_name(String mmt_plan_name) {
                    this.mmt_plan_name = mmt_plan_name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }

            public static class AgentBean {
                private String username;
                private String name;
                private String id;

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}
