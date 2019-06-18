package first.test.test;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.Verb;

public class InstaLoginApi extends DefaultApi20{

    protected InstaLoginApi(){
    }

    private static class InstanceHolder{
        private static final InstaLoginApi INSTANCE = new InstaLoginApi();
    }


    public static InstaLoginApi instance(){
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.instagram.com/oauth/access_token";
    }                   

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://api.instagram.com/oauth/authorize/?response_type=code";
    }   
    
    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }
    
}
