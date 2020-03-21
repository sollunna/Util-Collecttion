import java.util.Date;
import java.util.Objects;

/**
 * @Author: NineEr
 * @Date: 2020/3/19 22:12
 * @Description:
 */
public class User {

    private String name;

    private int age;

    private Date time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;//比较地址
        if (o == null || getClass() != o.getClass()) return false;//判空
        User user = (User) o;//当前对象比较
        return age == user.age &&
                name.equals(user.name) &&
                time.equals(user.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, time);
    }
}
