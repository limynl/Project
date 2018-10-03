package com.limynl.project.constants;

/**
 * email 1434117404@qq.com
 */


public class Constants {
    public static final String INTERFACE_URL = "http://120.78.149.138/xcv/index.php/home/index/index";
    public static final String GETVIDEOS_URL = "http://120.78.149.138:8080/getVideos/";

    public static final String BASE_URL = "http://47.100.245.128/lingxi-test";

    public static final String IMG_URL = "http://47.100.245.128/rss/lingxi-test";

    /**
     * 获取用户帖子
     */
    public static final String API_URL = BASE_URL + "/feed/page";

    /**
     * 上传图片
     */
    public static String uploadFeedImage = BASE_URL + "/rss/upload/feed/image";

    /**
     * 发布动态
     */
    public static final String saveFeed = BASE_URL + "/feed/save";

    /**
     * 查看动态
     */
    public static String viewFeed = BASE_URL + "/feed/view";

    /**
     * 动态评论列表
     */
    public static String pageComment = BASE_URL + "/feed/comment/page";

    /**
     * 新增动态评论
     */
    public static String saveComment = BASE_URL + "/feed/comment/save";


    public static boolean isRead = true;

    public static final int ACTIVITY_MAIN = 10001;
    public static final int ACTIVITY_PUBLISH = 10002;
    public static final int ACTIVITY_MOOD = 10003;
    public static final int ACTIVITY_PERSONAL = 10004;

    public static final String GO_INDEX = "go_index";
}
