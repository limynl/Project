package com.limynl.project.constants;

/**
 * email 1434117404@qq.com
 */


public class Constants {
    public static final String INTERFACE_URL = "http://120.78.149.138/xcv/index.php/home/index/index";
    public static final String GETVIDEOS_URL = "http://120.78.149.138:8080/getVideos/";

//    public static final String BASE_URL = "http://47.100.245.128/lingxi-test";
//    public static final String BASE_URL = "http://139.199.23.142:8090/project";//192.168.43.210
    public static final String BASE_URL = "http://192.168.1.10:8081/healthy";//192.168.1.10

    public static final String BASE_URL2 = "http://39.105.184.94:8090/project";//192.168.43.210
//    public static final String BASE_URL2 = "http://39.105.184.94:8090";//192.168.43.210

//    public static final String IMG_URL = "http://47.100.245.128/rss/lingxi-test";
    public static final String IMG_URL = "http://39.105.184.94:8080/lingxi";

    /**
     * 获取用户帖子
     */
    public static final String API_URL = BASE_URL2 + "/feed/page";

    /**
     * 上传图片
     */
    public static String uploadFeedImage = BASE_URL2 + "/rss/upload/feed/image";

    /**
     * 发布动态
     */
    public static final String saveFeed = BASE_URL2 + "/feed/save";

    /**
     * 查看动态
     */
    public static String viewFeed = BASE_URL2 + "/feed/view";

    /**
     * 动态评论列表
     */
    public static String pageComment = BASE_URL2 + "/feed/comment/page";

    /**
     * 新增动态评论
     */
    public static String saveComment = BASE_URL2 + "/feed/comment/save";

    /**
     * 用户登录
     */
    public static final String userRegister = BASE_URL + "/register";
    /**
     * 用户重置密码
     */
    public static final String userResetPassword = BASE_URL + "/restPassword";
    /**
     * 用户登录
     */
    public static final String userLogin = BASE_URL + "/login";
    /**
     * 用户更新
     */
    public static final String userUpdate = BASE_URL + "/update";
    public static final String userCheck = BASE_URL + "/queryCheck";
    public static final String userDeleteCheck = BASE_URL + "/deleteCheck";
    public static final String useraddCheck = BASE_URL + "/addCheck";

    public static boolean isRead = true;

    public static final int ACTIVITY_MAIN = 10001;
    public static final int ACTIVITY_PUBLISH = 10002;
    public static final int ACTIVITY_MOOD = 10003;
    public static final int ACTIVITY_PERSONAL = 10004;

    public static final String GO_INDEX = "go_index";
}
