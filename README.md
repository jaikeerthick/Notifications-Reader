# Notifications-Reader

Read all notifications in android using ```NotificationListenerService``` class.
* Using NotificationListenerService class we can listen and do things, :
* When notifications posted
* When notifications removed
* Rank is updated, etc 

Check the full code : [click here](https://github.com/jaikeerthick/Notifications-Reader/tree/main/app/src/main/java/com/jaikeerthick/mynotifications)
### To Use
* click the menu icon in the top left corner of the Screen to open settings page and give required permission. Tht's all! Enjoy.
* Replace the ```google-services.json``` file with your's.

### NotificationListenerService.java

```java
public class MyNotificationListener extends NotificationListenerService {

    @Override
    public void onCreate() {
        super .onCreate() ;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

    //code to do when notification posted
       
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {

    //code to do when notification removed

    }   
}
```

General form of the class looks like this
