package interfaces;

//import com.anypresence.sdk.acl.IAuthenticatable;
import com.anypresence.sdk.citi_mobile_challenge.models.RetailBankingLogin;

/**
 * Copyright (c) 2015 AnyPresence, Inc. All rights reserved.
 * Created by daniel.pavon on 27/02/15.
 */
public interface LoginListener {
    //void onLoginFailed(Throwable ex);

   // void onLoginSuccess(IAuthenticatable result);
    void onLoginFailed(boolean isConnected, Throwable ex);
    void onLoginSuccess(RetailBankingLogin client);
}
