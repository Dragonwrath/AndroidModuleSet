package com.joker.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lee on 2018/4/4 0004.
 */

public class CardListBean implements Serializable {

    private PostList fashionPost;
    private PostList beautySinkCarePost;
    private PostList collectionPost;


    public PostList getFashionPost() {
        return fashionPost;
    }

    public void setFashionPost(PostList fashionPost) {
        this.fashionPost = fashionPost;
    }

    public PostList getBeautySinkCarePost() {
        return beautySinkCarePost;
    }

    public void setBeautySinkCarePost(PostList beautySinkCarePost) {
        this.beautySinkCarePost = beautySinkCarePost;
    }

    public PostList getCollectionPost() {
        return collectionPost;
    }

    public void setCollectionPost(PostList collectionPost) {
        this.collectionPost = collectionPost;
    }

    /**
     * postList : {"isFirstPage":true,"isLastPage":false,"lists":[{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:42:56","createUser":1,"id":1,"nick":"1","orderStatus":1,"postBussType":1,"postType":0,"praiseNum":1,"status":0,"title":"测试视频（时尚妆容）","type":2,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"http://120.25.226.186:32812/resources/videos/minion_02.mp4","viewNum":2,"postImgList":[{"id":1,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":2,"status":0},{"id":2,"picUrl":"http://a.hiphotos.baidu.com/image/pic/item/b7fd5266d01609240bcda2d1dd0735fae7cd340b.jpg","postId":2,"status":0},{"id":3,"picUrl":"http://h.hiphotos.baidu.com/image/pic/item/728da9773912b31b57a6e01f8c18367adab4e13a.jpg","postId":2,"status":0}]},{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:46:55","createUser":1,"id":2,"nick":"1","orderStatus":2,"postBussType":1,"postImgList":[{"id":1,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":2,"status":0},{"id":2,"picUrl":"http://a.hiphotos.baidu.com/image/pic/item/b7fd5266d01609240bcda2d1dd0735fae7cd340b.jpg","postId":2,"status":0},{"id":3,"picUrl":"http://h.hiphotos.baidu.com/image/pic/item/728da9773912b31b57a6e01f8c18367adab4e13a.jpg","postId":2,"status":0}],"postType":0,"praiseNum":1,"status":0,"title":"测试图文（时尚妆容）","type":1,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"","viewNum":2},{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:42:56","createUser":1,"id":3,"nick":"1","orderStatus":3,"postBussType":2,"postType":0,"praiseNum":1,"status":0,"title":"测试视频（美容护肤）","type":2,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"http://120.25.226.186:32812/resources/videos/minion_02.mp4","viewNum":2},{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:46:55","createUser":1,"id":4,"nick":"1","orderStatus":4,"postBussType":2,"postImgList":[{"id":4,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":4,"status":0},{"id":5,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":4,"status":0}],"postType":0,"praiseNum":1,"status":0,"title":"测试图文（美容护肤）","type":1,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"","viewNum":2}],"pageNum":1,"pageSize":4,"pages":3,"total":12}
     */

    private PostList postList;

    public PostList getPostList() {
        return postList;
    }

    public void setPostList(PostList postList) {
        this.postList = postList;
    }

    public static class PostList implements Serializable {
        /**
         * isFirstPage : true
         * isLastPage : false
         * lists : [{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:42:56","createUser":1,"id":1,"nick":"1","orderStatus":1,"postBussType":1,"postType":0,"praiseNum":1,"status":0,"title":"测试视频（时尚妆容）","type":2,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"http://120.25.226.186:32812/resources/videos/minion_02.mp4","viewNum":2},{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:46:55","createUser":1,"id":2,"nick":"1","orderStatus":2,"postBussType":1,"postImgList":[{"id":1,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":2,"status":0},{"id":2,"picUrl":"http://a.hiphotos.baidu.com/image/pic/item/b7fd5266d01609240bcda2d1dd0735fae7cd340b.jpg","postId":2,"status":0},{"id":3,"picUrl":"http://h.hiphotos.baidu.com/image/pic/item/728da9773912b31b57a6e01f8c18367adab4e13a.jpg","postId":2,"status":0}],"postType":0,"praiseNum":1,"status":0,"title":"测试图文（时尚妆容）","type":1,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"","viewNum":2},{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:42:56","createUser":1,"id":3,"nick":"1","orderStatus":3,"postBussType":2,"postType":0,"praiseNum":1,"status":0,"title":"测试视频（美容护肤）","type":2,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"http://120.25.226.186:32812/resources/videos/minion_02.mp4","viewNum":2},{"collectionNum":1,"commentNum":1,"createTime":"2018-04-16 15:46:55","createUser":1,"id":4,"nick":"1","orderStatus":4,"postBussType":2,"postImgList":[{"id":4,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":4,"status":0},{"id":5,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":4,"status":0}],"postType":0,"praiseNum":1,"status":0,"title":"测试图文（美容护肤）","type":1,"userAvatar":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","userId":1,"videoUrl":"","viewNum":2}]
         * pageNum : 1
         * pageSize : 4
         * pages : 3
         * total : 12
         */

        private boolean isFirstPage;
        private boolean isLastPage;
        private int pageNum;
        private int pageSize;
        private int pages;
        private int total;
        private List<Lists> lists;

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Lists> getLists() {
            return lists;
        }

        public void setLists(List<Lists> lists) {
            this.lists = lists;
        }

        public static class Lists implements Serializable{
            /**
             * collectionNum : 1
             * commentNum : 1
             * createTime : 2018-04-16 15:42:56
             * createUser : 1
             * id : 1
             * nick : 1
             * orderStatus : 1
             * postBussType : 1
             * postType : 0
             * praiseNum : 1
             * status : 0
             * title : 测试视频（时尚妆容）
             * type : 2
             * userAvatar : http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg
             * userId : 1
             * videoUrl : http://120.25.226.186:32812/resources/videos/minion_02.mp4
             * viewNum : 2
             * postImgList : [{"id":1,"picUrl":"http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg","postId":2,"status":0},{"id":2,"picUrl":"http://a.hiphotos.baidu.com/image/pic/item/b7fd5266d01609240bcda2d1dd0735fae7cd340b.jpg","postId":2,"status":0},{"id":3,"picUrl":"http://h.hiphotos.baidu.com/image/pic/item/728da9773912b31b57a6e01f8c18367adab4e13a.jpg","postId":2,"status":0}]
             */

            private int collectionNum;
            private int commentNum;
            private String createTime;
            private int createUser;
            private int id;
            private String nick;
            private float picUrlProportion;
            private int orderStatus;
            private int postBussType;
            private int postType;
            private int praiseNum;
            private String praisePost;
            private int status;
            private String title;
            private int type;
            private String userAvatar;
            private int userId;
            private String picUrl;
            private String videoUrl;
            private String linkUrl;
            private String content;
            private String subTitle;
            private int viewNum;

            public float getPicUrlProportion() {
                return picUrlProportion;
            }

            public void setPicUrlProportion(float picUrlProportion) {
                this.picUrlProportion = picUrlProportion;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public String getPraisePost() {
                return praisePost;
            }

            public void setPraisePost(String praisePost) {
                this.praisePost = praisePost;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            private List<PostImgList> postImgList;

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getCollectionNum() {
                return collectionNum;
            }

            public void setCollectionNum(int collectionNum) {
                this.collectionNum = collectionNum;
            }

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getCreateUser() {
                return createUser;
            }

            public void setCreateUser(int createUser) {
                this.createUser = createUser;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public int getPostBussType() {
                return postBussType;
            }

            public void setPostBussType(int postBussType) {
                this.postBussType = postBussType;
            }

            public int getPostType() {
                return postType;
            }

            public void setPostType(int postType) {
                this.postType = postType;
            }

            public int getPraiseNum() {
                return praiseNum;
            }

            public void setPraiseNum(int praiseNum) {
                this.praiseNum = praiseNum;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUserAvatar() {
                return userAvatar;
            }

            public void setUserAvatar(String userAvatar) {
                this.userAvatar = userAvatar;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public int getViewNum() {
                return viewNum;
            }

            public void setViewNum(int viewNum) {
                this.viewNum = viewNum;
            }

            public List<PostImgList> getPostImgList() {
                return postImgList;
            }

            public void setPostImgList(List<PostImgList> postImgList) {
                this.postImgList = postImgList;
            }

            public static class PostImgList implements Serializable{
                /**
                 * id : 1
                 * picUrl : http://d.hiphotos.baidu.com/image/pic/item/b7fd5266d016092408d4a5d1dd0735fae7cd3402.jpg
                 * postId : 2
                 * status : 0
                 */

                private int id;
                private String picUrl;
                private int postId;
                private int status;
                private float picUrlProportion;
                public int getId() {
                    return id;
                }

                public float getPicUrlProportion() {
                    return picUrlProportion;
                }

                public void setPicUrlProportion(float picUrlProportion) {
                    this.picUrlProportion = picUrlProportion;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(String picUrl) {
                    this.picUrl = picUrl;
                }

                public int getPostId() {
                    return postId;
                }

                public void setPostId(int postId) {
                    this.postId = postId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }
    }
}
