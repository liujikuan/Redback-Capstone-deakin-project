package config;//submitted by Wing Hung Wu
//reference: https://medium.com/@ihorsokolyk/two-factor-authentication-with-java-and-google-authenticator-9d7ea15ffee6

import com.google.zxing.WriterException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Scanner;

import static config.Redback_2FAConfig.generateSecretKey;
import static config.Redback_2FAConfig.getTOTPCode;

public class MainApplication {

    public static String get2FAbarcode(String email) throws IOException, WriterException {
        String fileAddr = "QRCode.png";
        String secretKey = generateSecretKey();// it is generated randomly and is different very time you run this method
        String company = "Redback Company";
        String barcodeurl = Redback_2FAConfig.getGoogleAuthenticatorBarCode(secretKey, email, company);//getHexKey(secretKey)
        System.out.println(barcodeurl);
        Redback_2FAConfig.createQRCode(barcodeurl, fileAddr, 400, 400);//find the picture in your root directory

        setSecretKey(email, secretKey);
        return fileAddr;
    }

    private static String getSecretKey(String email) {
        //TODO: retrieve the key in a database according to the email
        return secretKey;
    }

    private static void setSecretKey(String email, String key) {
        //TODO:  store the username and the key in a database
        secretKey = key;
    }

    private static String secretKey = null;

    public static void main(String[] args) throws IOException, WriterException {
        String email = "testing@gmail.com";
        get2FAbarcode(email);
        System.out.println(new Timestamp(System.currentTimeMillis()));
        System.out.println("Please enter the 6 digit code:");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        verifyIdentityByOTP(email,code);

    }
    public static int verifyIdentityByOTP(String email,String code){
        String OTP = getTOTPCode(getSecretKey(email));
        System.out.println("the TOTPCode(secretKey) is :"+ OTP);
        System.out.println("the input code is :"+ code);
        if (code.equals(OTP)) {
            System.out.println("Logged in successful");
            return 0;
        } else {
            System.out.println("Invalid 2FA Code. Please try again.");
            return 1;
        }

    }
}

