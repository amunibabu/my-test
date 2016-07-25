/*
 * (C)2016 Digi-Net Technologies, Inc.
 * 5200 NW 43rd Street
 * Suite 102 #348
 * Gainesville, FL 32606, USA
 * All rights reserved.
 */

package com.muni.test;
import com.google.common.io.BaseEncoding;

import nl.martijndwars.webpush.GcmNotification;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;

import org.apache.http.client.fluent.Content;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * Created by Munindra Adusumalli<muni@rekko.com>.
 */
public class TestPushNotification {


  public static void main(String[] args)
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException,
             NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
             BadPaddingException, InvalidKeyException, IOException, ExecutionException,
             InterruptedException {
    Security.addProvider(new BouncyCastleProvider());

    String gcmApiKey = "AIzaSyDZyBccfaOTwzCyY8eP2Su4CqQ5pmzXtLI";

    UserInfo user1 = user1();
    UserInfo user2 = user2();
    UserInfo user3 = user3();
    PushService pushService = new PushService(gcmApiKey);
    Notification notification
    = new GcmNotification(user1.endPoint, user1.userPublicKey, user1.userAuth, "{\"title\": \"Hello\", \"message\": \"this is a message that has more than ninety characters. Lets see where the data custs off 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 01234567890\", \"url\":\"http://google.com\"}".getBytes());

    Future<Content> httpResponse = pushService.send(notification);
    System.out.println(httpResponse.get().asString());


    PushService pushService2 = new PushService();
    Notification notification2
        = new Notification(user2.endPoint, user2.userPublicKey, user2.userAuth, "{\"title\": \"Hello\", \"message\": \"World2\"}".getBytes());
    //Future<Content> httpResponse2 = pushService2.send(notification2);
    //System.out.println(httpResponse2.get().asString());

    Notification notification3
        = new Notification(user3.endPoint, user3.userPublicKey, user3.userAuth, "{\"title\": \"Hello\", \"message\": \"World2\"}".getBytes());
    //Future<Content> httpResponse3 = pushService2.send(notification3);
    //System.out.println(httpResponse3.get().asString());

    System.out.println("Done");
    System.exit(0);
  }

  private static UserInfo user1()
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
    return new UserInfo("https://android.googleapis.com/gcm/send/fgDe29C-H3s:APA91bFfM-CbDaPeHrFF1GPFJ-lXbVodSiFoUADJ0QuVoB2G9GZ7zk21-iI-UD-jXdYdiHciCHoZpElvGqvbLM4BmlExGaGbMPzJt4y5VXecs2hNU9PDClATccRFgRV4tFuvkul788Bc", "BPGtnLgTtQEbLaGSQ6WLMnEoEQUNDCotsS_rSepLyBxWDMwHSXY81XWCbRXioWXIPh9c1fsHRYGEVsrZP7UJat8=", "y4yf2LQSVR1miSKR3TfggQ==");
  }

  private static UserInfo user2()
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
    return new UserInfo("https://updates.push.services.mozilla.com/push/v1/gAAAAABXhQt5jEXmpWO6krikDNNQFGQknUMwgoPZuGrzrekYCsC8RCmjQteKurRabwVvZzqyuhyHjCg295iYgkxZLUHKBuqpLI2zJ6cpHyNHWA2y1lcCLLh4toKQLhEsrqqRrFlJ-1aR", "BCJxkj2wjyeBnVaVOvisxI_QPBfxN-hOIMELjhmzu1ZiAKxPo9oM7XTNDXfK9MCAbtl6wuZgg53vf_r3kc1mGDA", "9aFaSp5a-sOYuChchaZS0A");

  }

  private static UserInfo user3()
      throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
    return new UserInfo("https://updates.push.services.mozilla.com/push/v1/gAAAAABXhopPFuNXI-U0AJACjKE7C0Lq6AqqZMs_ATIFL14tAmZaHHJWjRxzAdGLC6Mw8WCY1NkqtagVpKEwGL0rKKnQZRFGd7B-4j2iyAbAdyEyEFZeYlKBt6P0MYqFuBzNyo7Vmhw4", "BDuYCMuxMmeXc4BGGUqD-sd9W69uPskhH_SdiPRqntX9P17aSJ1HdRdll89XmF_Z8Cdpeyi5gio8_N11rPg9jSc", "HwwwtySHpybM1z9AjP3ScA");

  }


  private static class UserInfo {
    String endPoint;
    PublicKey userPublicKey;
    byte[] userAuth;

    public UserInfo(String endPoint, String userPublicKey, String userAuth)
        throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
      this.endPoint = endPoint;
      this.userPublicKey = Utils.loadPublicKey(userPublicKey);
      this.userAuth = BaseEncoding.base64Url().decode(userAuth);
    }
  }
}
