package mvpdemo.zhou.com.mvpdemo.api;

import io.reactivex.Flowable;
import mvpdemo.zhou.com.mvpdemo.mvp.bean.MeiziInfo;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhou on 2017/3/25.
 */

public interface ApiService {
    @GET("data/福利/{pageSize}/{page}")
    Flowable<MeiziInfo> getMeiZiInfos (@Path("pageSize") int pageSize, @Path("page") int page);

}
