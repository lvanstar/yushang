package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/3.
 */
public class GetFirstCityBean {
    private String status;
    /**
     * id : 1
     * cityname : 上海市
     * parentid : 0
     * level : 1
     * children : [{"id":"35","cityname":"上海市","parentid":"1","level":"2","children":[{"id":"449","cityname":"宝山区","parentid":"35","level":"3","children":null},{"id":"450","cityname":"长宁区","parentid":"35","level":"3","children":null},{"id":"451","cityname":"崇明县","parentid":"35","level":"3","children":null},{"id":"452","cityname":"奉贤区","parentid":"35","level":"3","children":null},{"id":"453","cityname":"虹口区","parentid":"35","level":"3","children":null},{"id":"454","cityname":"黄浦区","parentid":"35","level":"3","children":null},{"id":"455","cityname":"嘉定区","parentid":"35","level":"3","children":null},{"id":"456","cityname":"金山区","parentid":"35","level":"3","children":null},{"id":"457","cityname":"静安区","parentid":"35","level":"3","children":null},{"id":"458","cityname":"卢湾区","parentid":"35","level":"3","children":null},{"id":"459","cityname":"闵行区","parentid":"35","level":"3","children":null},{"id":"460","cityname":"南汇区","parentid":"35","level":"3","children":null},{"id":"461","cityname":"浦东新区","parentid":"35","level":"3","children":null},{"id":"462","cityname":"普陀区","parentid":"35","level":"3","children":null},{"id":"463","cityname":"青浦区","parentid":"35","level":"3","children":null},{"id":"464","cityname":"松江区","parentid":"35","level":"3","children":null},{"id":"465","cityname":"徐汇区","parentid":"35","level":"3","children":null},{"id":"466","cityname":"杨浦区","parentid":"35","level":"3","children":null},{"id":"467","cityname":"闸北区","parentid":"35","level":"3","children":null}]}]
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String cityname;
        private String parentid;
        private String level;
        /**
         * id : 35
         * cityname : 上海市
         * parentid : 1
         * level : 2
         * children : [{"id":"449","cityname":"宝山区","parentid":"35","level":"3","children":null},{"id":"450","cityname":"长宁区","parentid":"35","level":"3","children":null},{"id":"451","cityname":"崇明县","parentid":"35","level":"3","children":null},{"id":"452","cityname":"奉贤区","parentid":"35","level":"3","children":null},{"id":"453","cityname":"虹口区","parentid":"35","level":"3","children":null},{"id":"454","cityname":"黄浦区","parentid":"35","level":"3","children":null},{"id":"455","cityname":"嘉定区","parentid":"35","level":"3","children":null},{"id":"456","cityname":"金山区","parentid":"35","level":"3","children":null},{"id":"457","cityname":"静安区","parentid":"35","level":"3","children":null},{"id":"458","cityname":"卢湾区","parentid":"35","level":"3","children":null},{"id":"459","cityname":"闵行区","parentid":"35","level":"3","children":null},{"id":"460","cityname":"南汇区","parentid":"35","level":"3","children":null},{"id":"461","cityname":"浦东新区","parentid":"35","level":"3","children":null},{"id":"462","cityname":"普陀区","parentid":"35","level":"3","children":null},{"id":"463","cityname":"青浦区","parentid":"35","level":"3","children":null},{"id":"464","cityname":"松江区","parentid":"35","level":"3","children":null},{"id":"465","cityname":"徐汇区","parentid":"35","level":"3","children":null},{"id":"466","cityname":"杨浦区","parentid":"35","level":"3","children":null},{"id":"467","cityname":"闸北区","parentid":"35","level":"3","children":null}]
         */

        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            private String id;
            private String cityname;
            private String parentid;
            private String level;
            /**
             * id : 449
             * cityname : 宝山区
             * parentid : 35
             * level : 3
             * children : null
             */

            private List<ChildrenBean2> children;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public List<ChildrenBean2> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean2> children) {
                this.children = children;
            }

            public static class ChildrenBean2 {
                private String id;
                private String cityname;
                private String parentid;
                private String level;
                private Object children;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCityname() {
                    return cityname;
                }

                public void setCityname(String cityname) {
                    this.cityname = cityname;
                }

                public String getParentid() {
                    return parentid;
                }

                public void setParentid(String parentid) {
                    this.parentid = parentid;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public Object getChildren() {
                    return children;
                }

                public void setChildren(Object children) {
                    this.children = children;
                }
                //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
                public String getPickerViewText() {
                    //这里还可以判断文字超长截断再提供显示
                    return cityname;
                }
            }
        }
    }

    private ErrorEntity error;
    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public ErrorEntity getError() {
        return error;
    }


    public static class ErrorEntity {
        /**
         * code : 1012
         * error : WRONG_PASSWORD
         * message : 密码错误!
         */

        private String code;
        private String error;
        private String message;

        public void setCode(String code) {
            this.code = code;
        }

        public void setError(String error) {
            this.error = error;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "ErrorEntity{" +
                    "code='" + code + '\'' +
                    ", error='" + error + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
