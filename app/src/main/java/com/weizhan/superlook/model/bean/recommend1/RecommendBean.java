package com.weizhan.superlook.model.bean.recommend1;

import java.util.List;

/**
 * Created by Administrator on 2018/11/7.
 */

public class RecommendBean {
    List<Banner> banner;
    List<ComlumInfo> comlum_list;

    public List<Banner> getBanner() {
        return banner;
    }


    public List<ComlumInfo> getComlum_list() {
        return comlum_list;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public void setComlum_list(List<ComlumInfo> comlum_list) {
        this.comlum_list = comlum_list;
    }

    public class PartTitle {
        String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class MovieItem {
        String title;
        int id;
        String v_img;
        String h_img;
        String score;
        String describes;
        int total;
        int type;
        String times;
        int current_num;

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public String getV_img() {
            return v_img;
        }

        public String getH_img() {
            return h_img;
        }

        public String getScore() {
            return score;
        }

        public int getTotal() {
            return total;
        }

        public int getCurrent_num() {
            return current_num;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setV_img(String v_img) {
            this.v_img = v_img;
        }

        public void setH_img(String h_img) {
            this.h_img = h_img;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setCurrent_num(int current_num) {
            this.current_num = current_num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }
    }

    public class Banners {
        private List<Banner> banners;

        public List<Banner> getBanners() {
            return banners;
        }

        public void setBanners(List<Banner> banners) {
            this.banners = banners;
        }
    }

    public class Banner {
        String title;
        int id;
        String imgurl;
        int type;

        public String getTitle() {
            return title;
        }

        public int getId() {
            return id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public int getType() {
            return type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public class ComlumInfo {
        String home_name;
        int id;
        int last_page;
        int type;
        List<ItemInfo> list;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getHome_name() {
            return home_name;
        }

        public int getId() {
            return id;
        }

        public List<ItemInfo> getList() {
            return list;
        }

        public void setHome_name(String home_name) {
            this.home_name = home_name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setList(List<ItemInfo> list) {
            this.list = list;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public class ItemInfo {
            String title;
            int id;
            String v_img;
            String h_img;
            String score;
            String describes;
            int total;
            int type;
            int current_num;
            String times;

            public String getTitle() {
                return title;
            }

            public int getId() {
                return id;
            }

            public String getV_img() {
                return v_img;
            }

            public String getH_img() {
                return h_img;
            }

            public String getScore() {
                return score;
            }

            public int getTotal() {
                return total;
            }

            public int getCurrent_num() {
                return current_num;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setV_img(String v_img) {
                this.v_img = v_img;
            }

            public void setH_img(String h_img) {
                this.h_img = h_img;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setCurrent_num(int current_num) {
                this.current_num = current_num;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDescribes() {
                return describes;
            }

            public void setDescribes(String describes) {
                this.describes = describes;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }
        }
    }
}
