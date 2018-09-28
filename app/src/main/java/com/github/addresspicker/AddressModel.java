package com.github.addresspicker;

import java.util.List;

/**
 * @data 2018-09-28
 * @desc
 */

public class AddressModel {

    private List<CityListModel> citylist;

    public List<CityListModel> getCitylist() {
        return citylist;
    }

    public void setCitylist(List<CityListModel> citylist) {
        this.citylist = citylist;
    }

    public class CityListModel {

        private List<CityModel> city;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityModel> getCity() {
            return city;
        }

        public void setCity(List<CityModel> city) {
            this.city = city;
        }

        public class CityModel{
            private String name;
            private List<String> area;

            public List<String> getArea() {
                return area;
            }

            public void setArea(List<String> area) {
                this.area = area;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

    }

}
