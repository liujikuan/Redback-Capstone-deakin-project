//submitted by Wing Hung Wu
//reference: https://medium.com/@ihorsokolyk/two-factor-authentication-with-java-and-google-authenticator-9d7ea15ffee6

import com.google.zxing.WriterException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Scanner;

import config.Redback_2FAConfig;


import static config.Redback_2FAConfig.generateSecretKey;
import static config.Redback_2FAConfig.getTOTPCode;

public class MainApplication {

    public static void main(String[] args) throws IOException, WriterException {
        String secretKey = generateSecretKey();// it is generated randomly and is different very time you run this method
        String email = "testing@gmail.com";
        String company = "Redback Company";
        String barcodeurl = Redback_2FAConfig.getGoogleAuthenticatorBarCode(secretKey, email, company);//getHexKey(secretKey)
        System.out.println(barcodeurl);
        Redback_2FAConfig.createQRCode(barcodeurl, "QRCode.png", 400, 400);//find the picture in your root directory
        System.out.println(new Timestamp(System.currentTimeMillis()));
        System.out.println("Please enter the 6 digit code:");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        System.out.println("the TOTPCode(secretKey) is :"+ getTOTPCode(secretKey));
        System.out.println("the input code is :"+ code);
        if (code.equals(getTOTPCode(secretKey))) {
            System.out.println("Logged in successful");
        } else {
            System.out.println("Invalid 2FA Code. Please try again.");
        }

    }

}

