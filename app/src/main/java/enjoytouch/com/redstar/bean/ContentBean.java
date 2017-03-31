package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaozhao on 16/6/13.
 */
public class ContentBean implements Serializable{

    private String id;
    private String comment;
    private String head_img;
    private String nickname;
    private String created;
    private List<String> pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
