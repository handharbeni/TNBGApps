package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by root on 17/07/17.
 */

public class LoadImage {
    Context context;

    public LoadImage(Context context) {
        this.context = context;
    }

    public void setImage(String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }
}
