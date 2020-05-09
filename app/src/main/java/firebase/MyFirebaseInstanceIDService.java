package firebase;

import com.google.firebase.messaging.FirebaseMessagingService;

// import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private final static String TAG = "FCM_ID";

//    @Override
//    public void onTokenRefresh() {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "FirebaseInstanceId Refreshed token: " + refreshedToken);
//    }

}
