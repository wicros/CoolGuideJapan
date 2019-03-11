package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/9/5.
 */
public class ExcelInfo {

    /**
     * results : [{"address_components":[{"long_name":"松栄東口第一ビル","short_name":"松栄東口第一ビル","types":["premise"]},{"long_name":"8","short_name":"8","types":["political","sublocality","sublocality_level_4"]},{"long_name":"2","short_name":"2","types":["political","sublocality","sublocality_level_3"]},{"long_name":"1 Chome","short_name":"1 Chome","types":["political","sublocality","sublocality_level_2"]},{"long_name":"Tsutsujigaoka","short_name":"Tsutsujigaoka","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Miyagino-ku","short_name":"Miyagino-ku","types":["locality","political","ward"]},{"long_name":"Sendai-shi","short_name":"Sendai-shi","types":["locality","political"]},{"long_name":"Miyagi-ken","short_name":"Miyagi-ken","types":["administrative_area_level_1","political"]},{"long_name":"Japan","short_name":"JP","types":["country","political"]},{"long_name":"983-0852","short_name":"983-0852","types":["postal_code"]}],"formatted_address":"Japan, 〒983-0852 Miyagi-ken, Sendai-shi, Miyagino-ku, Tsutsujigaoka, 1 Chome−2−8, 松栄東口第一ビル","geometry":{"bounds":{"northeast":{"lat":38.259606,"lng":140.8857502},"southwest":{"lat":38.2593121,"lng":140.8851677}},"location":{"lat":38.2594909,"lng":140.8854845},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":38.26080803029149,"lng":140.8868079302915},"southwest":{"lat":38.2581100697085,"lng":140.8841099697085}}},"place_id":"ChIJf5QkIxkoil8R9Rg6GszXRo8","types":["premise"]}]
     * status : OK
     */

    private String status;
    /**
     * address_components : [{"long_name":"松栄東口第一ビル","short_name":"松栄東口第一ビル","types":["premise"]},{"long_name":"8","short_name":"8","types":["political","sublocality","sublocality_level_4"]},{"long_name":"2","short_name":"2","types":["political","sublocality","sublocality_level_3"]},{"long_name":"1 Chome","short_name":"1 Chome","types":["political","sublocality","sublocality_level_2"]},{"long_name":"Tsutsujigaoka","short_name":"Tsutsujigaoka","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Miyagino-ku","short_name":"Miyagino-ku","types":["locality","political","ward"]},{"long_name":"Sendai-shi","short_name":"Sendai-shi","types":["locality","political"]},{"long_name":"Miyagi-ken","short_name":"Miyagi-ken","types":["administrative_area_level_1","political"]},{"long_name":"Japan","short_name":"JP","types":["country","political"]},{"long_name":"983-0852","short_name":"983-0852","types":["postal_code"]}]
     * formatted_address : Japan, 〒983-0852 Miyagi-ken, Sendai-shi, Miyagino-ku, Tsutsujigaoka, 1 Chome−2−8, 松栄東口第一ビル
     * geometry : {"bounds":{"northeast":{"lat":38.259606,"lng":140.8857502},"southwest":{"lat":38.2593121,"lng":140.8851677}},"location":{"lat":38.2594909,"lng":140.8854845},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":38.26080803029149,"lng":140.8868079302915},"southwest":{"lat":38.2581100697085,"lng":140.8841099697085}}}
     * place_id : ChIJf5QkIxkoil8R9Rg6GszXRo8
     * types : ["premise"]
     */

    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String formatted_address;
        /**
         * bounds : {"northeast":{"lat":38.259606,"lng":140.8857502},"southwest":{"lat":38.2593121,"lng":140.8851677}}
         * location : {"lat":38.2594909,"lng":140.8854845}
         * location_type : ROOFTOP
         * viewport : {"northeast":{"lat":38.26080803029149,"lng":140.8868079302915},"southwest":{"lat":38.2581100697085,"lng":140.8841099697085}}
         */

        private GeometryBean geometry;
        private String place_id;
        /**
         * long_name : 松栄東口第一ビル
         * short_name : 松栄東口第一ビル
         * types : ["premise"]
         */

        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * northeast : {"lat":38.259606,"lng":140.8857502}
             * southwest : {"lat":38.2593121,"lng":140.8851677}
             */

            private BoundsBean bounds;
            /**
             * lat : 38.2594909
             * lng : 140.8854845
             */

            private LocationBean location;
            private String location_type;
            /**
             * northeast : {"lat":38.26080803029149,"lng":140.8868079302915}
             * southwest : {"lat":38.2581100697085,"lng":140.8841099697085}
             */

            private ViewportBean viewport;

            public BoundsBean getBounds() {
                return bounds;
            }

            public void setBounds(BoundsBean bounds) {
                this.bounds = bounds;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class BoundsBean {
                /**
                 * lat : 38.259606
                 * lng : 140.8857502
                 */

                private NortheastBean northeast;
                /**
                 * lat : 38.2593121
                 * lng : 140.8851677
                 */

                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class LocationBean {
                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * lat : 38.26080803029149
                 * lng : 140.8868079302915
                 */

                private NortheastBean northeast;
                /**
                 * lat : 38.2581100697085
                 * lng : 140.8841099697085
                 */

                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
