package com.apptutti.tuttistore.bean.discover;

import java.util.List;

/**
 * Created by 006 on 12/10/2017.
 */

public class DiscoveryBean{



    public boolean success;
    public List<DataBeanX> data;

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public List<DataBeanX> getData(){
        return data;
    }

    public void setData(List<DataBeanX> data){
        this.data = data;
    }

    public static class DataBeanX{


        private String type;
        private String label;
        private int style;
        private ParamsBean params;
        private int changed_flag;
        private boolean show_title;
        private List<DataBean> data;

        public String getType(){
            return type;
        }

        public void setType(String type){
            this.type = type;
        }

        public String getLabel(){
            return label;
        }

        public void setLabel(String label){
            this.label = label;
        }

        public int getStyle(){
            return style;
        }

        public void setStyle(int style){
            this.style = style;
        }

        public ParamsBean getParams(){
            return params;
        }

        public void setParams(ParamsBean params){
            this.params = params;
        }

        public int getChanged_flag(){
            return changed_flag;
        }

        public void setChanged_flag(int changed_flag){
            this.changed_flag = changed_flag;
        }

        public boolean isShow_title(){
            return show_title;
        }

        public void setShow_title(boolean show_title){
            this.show_title = show_title;
        }

        public List<DataBean> getData(){
            return data;
        }

        public void setData(List<DataBean> data){
            this.data = data;
        }

        public static class ParamsBean{
            /**
             * id : 381
             */

            private String id;

            public String getId(){
                return id;
            }

            public void setId(String id){
                this.id = id;
            }
        }

        public static class DataBean{


            private int id;
            private String title;
            private String uri;
            private int comments;
            private ContentsBean contents;
            private BannerBean banner;


            private String name;
            private String avatar;
            private String medium_avatar;
            private String gender;
            private String intro;
            private VerifiedBean verified;
            private boolean is_certified;
            /**
             * label : 新游预约
             */

            private String label;

            public int getId(){
                return id;
            }

            public void setId(int id){
                this.id = id;
            }

            public String getTitle(){
                return title;
            }

            public void setTitle(String title){
                this.title = title;
            }

            public String getUri(){
                return uri;
            }

            public void setUri(String uri){
                this.uri = uri;
            }

            public int getComments(){
                return comments;
            }

            public void setComments(int comments){
                this.comments = comments;
            }

            public ContentsBean getContents(){
                return contents;
            }

            public void setContents(ContentsBean contents){
                this.contents = contents;
            }

            public BannerBean getBanner(){
                return banner;
            }

            public void setBanner(BannerBean banner){
                this.banner = banner;
            }

            public String getName(){
                return name;
            }

            public void setName(String name){
                this.name = name;
            }

            public String getAvatar(){
                return avatar;
            }

            public void setAvatar(String avatar){
                this.avatar = avatar;
            }

            public String getMedium_avatar(){
                return medium_avatar;
            }

            public void setMedium_avatar(String medium_avatar){
                this.medium_avatar = medium_avatar;
            }

            public String getGender(){
                return gender;
            }

            public void setGender(String gender){
                this.gender = gender;
            }

            public String getIntro(){
                return intro;
            }

            public void setIntro(String intro){
                this.intro = intro;
            }

            public VerifiedBean getVerified(){
                return verified;
            }

            public void setVerified(VerifiedBean verified){
                this.verified = verified;
            }

            public boolean isIs_certified(){
                return is_certified;
            }

            public void setIs_certified(boolean is_certified){
                this.is_certified = is_certified;
            }

            public String getLabel(){
                return label;
            }

            public void setLabel(String label){
                this.label = label;
            }

            public static class ContentsBean{
                /**
                 * text : 连招、闪避、格挡、反击；前后、左右、跳跃、斩劈。停不下来的点触操作，迎敌而上，杀杀杀！
                 燃烧动作之魂，秀出一手好操作。
                 */

                private String text;

                public String getText(){
                    return text;
                }

                public void setText(String text){
                    this.text = text;
                }
            }

            public static class BannerBean{


                private String url;
                private String medium_url;
                private String original_url;
                private String original_format;
                private int width;
                private int height;
                private String color;

                public String getUrl(){
                    return url;
                }

                public void setUrl(String url){
                    this.url = url;
                }

                public String getMedium_url(){
                    return medium_url;
                }

                public void setMedium_url(String medium_url){
                    this.medium_url = medium_url;
                }

                public String getOriginal_url(){
                    return original_url;
                }

                public void setOriginal_url(String original_url){
                    this.original_url = original_url;
                }

                public String getOriginal_format(){
                    return original_format;
                }

                public void setOriginal_format(String original_format){
                    this.original_format = original_format;
                }

                public int getWidth(){
                    return width;
                }

                public void setWidth(int width){
                    this.width = width;
                }

                public int getHeight(){
                    return height;
                }

                public void setHeight(int height){
                    this.height = height;
                }

                public String getColor(){
                    return color;
                }

                public void setColor(String color){
                    this.color = color;
                }
            }

            public static class VerifiedBean{


                private String type;
                private String reason;
                private String url;

                public String getType(){
                    return type;
                }

                public void setType(String type){
                    this.type = type;
                }

                public String getReason(){
                    return reason;
                }

                public void setReason(String reason){
                    this.reason = reason;
                }

                public String getUrl(){
                    return url;
                }

                public void setUrl(String url){
                    this.url = url;
                }
            }
        }
    }
}
