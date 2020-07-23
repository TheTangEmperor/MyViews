package com.demo.myviews.bean;

import java.util.List;

public class Province {



    private DataBean data;
    private String msg;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        private List<AreasBean> areas;

        public List<AreasBean> getAreas() {
            return areas;
        }

        public void setAreas(List<AreasBean> areas) {
            this.areas = areas;
        }

        public static class AreasBean {


            private int id;
            private String name;
            private int parent_id;
            private int is_hot;
            private List<ChildrenBeanX> children;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(int is_hot) {
                this.is_hot = is_hot;
            }

            public List<ChildrenBeanX> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBeanX> children) {
                this.children = children;
            }

            public static class ChildrenBeanX {


                private int id;
                private String name;
                private int parent_id;
                private int is_hot;
                private List<ChildrenBean> children;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getParent_id() {
                    return parent_id;
                }

                public void setParent_id(int parent_id) {
                    this.parent_id = parent_id;
                }

                public int getIs_hot() {
                    return is_hot;
                }

                public void setIs_hot(int is_hot) {
                    this.is_hot = is_hot;
                }

                public List<ChildrenBean> getChildren() {
                    return children;
                }

                public void setChildren(List<ChildrenBean> children) {
                    this.children = children;
                }

                public static class ChildrenBean {


                    private int id;
                    private String name;
                    private int parent_id;
                    private int is_hot;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getParent_id() {
                        return parent_id;
                    }

                    public void setParent_id(int parent_id) {
                        this.parent_id = parent_id;
                    }

                    public int getIs_hot() {
                        return is_hot;
                    }

                    public void setIs_hot(int is_hot) {
                        this.is_hot = is_hot;
                    }
                }
            }
        }
    }
}
