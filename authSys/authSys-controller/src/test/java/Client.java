import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

//@RunWith(SpringJUnit4ClassRunner.class)
public class Client {

    @Test
    public void jwtTest(){
        long expireTime = 60 * 30;
        JwtBuilder builder = Jwts.builder();
        String token = builder.setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")
                .claim("userName","admin")
                .claim("passwd", "123")
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
        System.out.println(token);
    }
}
