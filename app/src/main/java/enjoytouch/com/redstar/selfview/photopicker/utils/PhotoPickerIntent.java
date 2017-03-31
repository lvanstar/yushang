package enjoytouch.com.redstar.selfview.photopicker.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;



/**
 * Created by donglua on 15/7/2.
 */
public class PhotoPickerIntent extends Intent {

  private PhotoPickerIntent() {
  }

  private PhotoPickerIntent(Intent o) {
    super(o);
  }

  private PhotoPickerIntent(String action) {
    super(action);
  }

  private PhotoPickerIntent(String action, Uri uri) {
    super(action, uri);
  }

  private PhotoPickerIntent(Context packageContext, Class<?> cls) {
    super(packageContext, cls);
  }



}
