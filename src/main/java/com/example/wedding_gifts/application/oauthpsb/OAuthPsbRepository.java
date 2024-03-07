package com.example.wedding_gifts.application.oauthpsb;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wedding_gifts.common.MyZone;
import com.example.wedding_gifts.core.domain.dtos.oauthpsb.CreateOAuthPsb;
import com.example.wedding_gifts.core.domain.model.OAuthPsb;
import com.example.wedding_gifts.core.usecases.oauthpsb.IOAuthPsbRepository;
import com.example.wedding_gifts.infra.jpa.JpaOAuthPsbRepository;

@Repository
@SuppressWarnings("null")
public class OAuthPsbRepository implements IOAuthPsbRepository {

    @Autowired
    private JpaOAuthPsbRepository jpaRepository;

    @Override
    public OAuthPsb saveOAuth(OAuthPsb oauth) throws Exception {
        try {
            return jpaRepository.save(oauth);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public OAuthPsb createOAuth(CreateOAuthPsb oauth) throws Exception {
        try {
            OAuthPsb oldOauth = getOAuth();

            if(oldOauth != null) deleteOAuth();
            
            oldOauth = new OAuthPsb(oauth);

            return saveOAuth(oldOauth);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public OAuthPsb getOAuth() throws Exception {
        try {
            List<OAuthPsb> allOauths = jpaRepository.findAll();

            if(allOauths == null || allOauths.size() == 0) return null;

            OAuthPsb out = allOauths.get(0);
            if(allOauths.size() > 1) {
                for(OAuthPsb oauth : allOauths){
                    out = oauth.getExpiration().toEpochSecond(MyZone.zoneOffset()) > out.getExpiration().toEpochSecond(MyZone.zoneOffset()) 
                        ? oauth 
                        : out;
                }
            }

            if(out.getExpiration().isBefore(LocalDateTime.now())) {
                deleteOAuth();
                return null;
            }

            return out;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public OAuthPsb updateOAuth(CreateOAuthPsb oauth) throws Exception {
        try {
            deleteOAuth();

            return createOAuth(oauth);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteOAuth() throws Exception {
        try {
            jpaRepository.deleteAll();
        } catch (Exception e) {
            throw e;
        }
    }

}