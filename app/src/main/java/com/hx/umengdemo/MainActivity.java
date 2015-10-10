package com.hx.umengdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.laiwang.media.LWDynamicShareContent;
import com.umeng.socialize.laiwang.media.LWShareContent;
import com.umeng.socialize.media.GooglePlusShareContent;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.TwitterShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.umeng.update.UmengUpdateAgent;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");

    @Bind(R.id.share)
    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configPlatforms(this);
        setShareContent(this);
        UmengUpdateAgent.setDeltaUpdate(true);
        UmengUpdateAgent.update(this);
    }

    @OnClick({R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                share();
                break;
        }
    }

    private void share() {
        mController.openShare(this, false);
    }


    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms(Activity activity) {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加腾讯微博SSO授权
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        // 添加人人网SSO授权
        RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(activity,
                "201874", "28401c0964f04a72a14c812d6132fcef",
                "3bf66e42db1e4fa9829b955cc300b737");
        mController.getConfig().setSsoHandler(renrenSsoHandler);

        // 添加QQ、QZone平台
        addQQQZonePlatform(activity);

        // 添加微信、微信朋友圈平台
        addWXPlatform(activity);
    }

    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private void addWXPlatform(Context context) {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx1e79806e9512c75f";
        String appSecret = "4db435655e8509312c75e2e78fdc34fd";
        String md5 = "5ABC4B9DAABA697FCECCCA332A061BB3";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
        wxHandler.showCompressToast(false);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     * image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     * 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     * : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform(Activity activity) {
        String appId = "1104900006";
        String appKey = "doOw3jEiWiIS4PkW";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity,
                appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }


    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent(Activity activity) {

        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity,
                "1104900006", "doOw3jEiWiIS4PkW");
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能。http://www.umeng.com/social");

        // APP ID：201874, API
        // * KEY：28401c0964f04a72a14c812d6132fcef, Secret
        // * Key：3bf66e42db1e4fa9829b955cc300b737.
        RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(activity,
                "201874", "28401c0964f04a72a14c812d6132fcef",
                "3bf66e42db1e4fa9829b955cc300b737");
        mController.getConfig().setSsoHandler(renrenSsoHandler);

        UMImage localImage = new UMImage(activity, R.mipmap.ic_launcher);
        UMImage urlImage = new UMImage(activity,
                "http://www.baidu.com/img/bdlogo.png");
        // UMImage resImage = new UMImage(getActivity(), R.drawable.icon);

        // 视频分享
        UMVideo video = new UMVideo(
                "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        // vedio.setThumb("http://www.umeng.com/images/pic/home/social/img-1.png");
        video.setTitle("友盟社会化组件视频");
        video.setThumb(urlImage);

        UMusic uMusic = new UMusic(
                "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        uMusic.setAuthor("umeng");
        uMusic.setTitle("天籁之音");
        // uMusic.setThumb(urlImage);
        uMusic.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");

        // UMEmoji emoji = new UMEmoji(getActivity(),
        // "http://www.pc6.com/uploadimages/2010214917283624.gif");
        // UMEmoji emoji = new UMEmoji(getActivity(),
        // "/storage/sdcard0/emoji.gif");

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-微信。http://www.umeng.com/social");
        weixinContent.setTitle("友盟社会化分享组件-微信");
        weixinContent.setTargetUrl("http://www.umeng.com/social");
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-朋友圈。http://www.umeng.com/social");
        circleMedia.setTitle("友盟社会化分享组件-朋友圈");
        circleMedia.setShareMedia(urlImage);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
        circleMedia.setTargetUrl("http://www.umeng.com/social");
        mController.setShareMedia(circleMedia);

        // 设置renren分享内容
        RenrenShareContent renrenShareContent = new RenrenShareContent();
        renrenShareContent.setShareContent("人人分享内容");
        UMImage image = new UMImage(activity,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        image.setTitle("thumb title");
        image.setThumb("http://www.umeng.com/images/pic/social/integrated_3.png");
        renrenShareContent.setShareImage(image);
        renrenShareContent.setAppWebSite("http://www.umeng.com/social");
        mController.setShareMedia(renrenShareContent);

        UMImage qzoneImage = new UMImage(activity,
                "http://www.umeng.com/images/pic/social/integrated_3.png");
        qzoneImage
                .setTargetUrl("http://www.umeng.com/images/pic/social/integrated_3.png");

        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent("share test");
        qzone.setTargetUrl("http://www.umeng.com");
        qzone.setTitle("QZone title");
        qzone.setShareMedia(urlImage);
        // qzone.setShareMedia(uMusic);
        mController.setShareMedia(qzone);

        video.setThumb(new UMImage(activity, BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_launcher)));

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能 -- QQ");
        qqShareContent.setTitle("hello, title");
        qqShareContent.setShareMedia(image);
        qqShareContent.setTargetUrl("http://www.umeng.com/social");
        mController.setShareMedia(qqShareContent);

        // 视频分享
        UMVideo umVideo = new UMVideo(
                "http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        umVideo.setThumb("http://www.umeng.com/images/pic/home/social/img-1.png");
        umVideo.setTitle("友盟社会化组件视频");

        TencentWbShareContent tencent = new TencentWbShareContent();
        tencent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-腾讯微博。http://www.umeng.com/social");
        // 设置tencent分享内容
        mController.setShareMedia(tencent);

        // 设置邮件分享内容， 如果需要分享图片则只支持本地图片
        MailShareContent mail = new MailShareContent(localImage);
        mail.setTitle("share form umeng social sdk");
        mail.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-email。http://www.umeng.com/social");
        // 设置tencent分享内容
        mController.setShareMedia(mail);

        // 设置短信分享内容
        SmsShareContent sms = new SmsShareContent();
        sms.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-短信。http://www.umeng.com/social");
        // sms.setShareImage(urlImage);
        mController.setShareMedia(sms);

        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-新浪微博。http://www.umeng.com/social");
        sinaContent.setShareImage(new UMImage(activity, R.mipmap.ic_launcher));
        mController.setShareMedia(sinaContent);

        TwitterShareContent twitterShareContent = new TwitterShareContent();
        twitterShareContent
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-TWITTER。http://www.umeng.com/social");
        twitterShareContent.setShareMedia(new UMImage(activity, new File(
                "/storage/sdcard0/emoji.gif")));
        mController.setShareMedia(twitterShareContent);

        GooglePlusShareContent googlePlusShareContent = new GooglePlusShareContent();
        googlePlusShareContent
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-G+。http://www.umeng.com/social");
        googlePlusShareContent.setShareMedia(localImage);
        mController.setShareMedia(googlePlusShareContent);

        // 来往分享内容
        LWShareContent lwShareContent = new LWShareContent();
        // lwShareContent.setShareImage(urlImage);
        // lwShareContent.setShareMedia(uMusic);
        lwShareContent.setShareMedia(umVideo);
        lwShareContent.setTitle("友盟社会化分享组件-来往");
        lwShareContent.setMessageFrom("友盟分享组件");
        lwShareContent
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-来往。http://www.umeng.com/social");
        mController.setShareMedia(lwShareContent);

        // 来往动态分享内容
        LWDynamicShareContent lwDynamicShareContent = new LWDynamicShareContent();
        // lwDynamicShareContent.setShareImage(urlImage);
        // lwDynamicShareContent.setShareMedia(uMusic);
        lwDynamicShareContent.setShareMedia(umVideo);
        lwDynamicShareContent.setTitle("友盟社会化分享组件-来往动态");
        lwDynamicShareContent.setMessageFrom("来自友盟");
        lwDynamicShareContent
                .setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能-来往动态。http://www.umeng.com/social");
        lwDynamicShareContent.setTargetUrl("http://www.umeng.com/social");
        mController.setShareMedia(lwDynamicShareContent);

    }
}
