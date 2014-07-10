package com.malibu.security.ldap;

import java.util.List;

import oracle.igf.ids.ReadOptions;

import oracle.security.idm.IMException;
import oracle.security.idm.IdentityStore;
import oracle.security.idm.StoreConfiguration;
import oracle.security.idm.User;
import oracle.security.idm.UserProfile;
import oracle.security.idm.providers.oid.OIDIdentityStoreFactory;
import oracle.security.jps.JpsContext;
import oracle.security.jps.JpsContextFactory;
import oracle.security.jps.JpsException;
import oracle.security.jps.internal.idstore.ldap.LdapIdentityStore;
import oracle.security.jps.service.idstore.IdentityStoreService;


public class IdentityStoreAdapter {
    
    private static IdentityStore identityStore;   
    private static IdentityStoreAdapter identityAdapter=null;
    
    public IdentityStoreAdapter() {
        //Method to initialze identity Store
        JpsContextFactory jps;
        try {
            jps = JpsContextFactory.getContextFactory();
            JpsContext jpsContext=jps.getContext();            
            LdapIdentityStore storeService = (LdapIdentityStore)jpsContext.getServiceInstance(IdentityStoreService.class);
            this.identityStore = storeService.getIdmStore();   
            
            StoreConfiguration storeConfig = identityStore.getStoreConfiguration(); 
            Object value=storeConfig.getProperty(OIDIdentityStoreFactory.ST_USER_NAME_ATTR);
            
            System.out.println(value);
        } catch (JpsException e) {
            e.printStackTrace();
        } 
        catch (IMException ie) {
            ie.printStackTrace();
        }catch (Exception ee) {
            ee.printStackTrace();
        }
    }
    
    public static IdentityStoreAdapter getInstance() {       
        if(identityAdapter == null) {
            synchronized(IdentityStoreAdapter.class) {               
                
                if (identityAdapter == null) {
                    identityAdapter = new IdentityStoreAdapter();
                }
            }
        }
        return identityAdapter;
    } 
    
    public UserProfile getUserProfile(String userName){       
        try {
            
            //ReadOptions readOpts = new ReadOptions();            
            //readOpts.setLocale("en-us");
            User user = (User)this.identityStore.searchUser(IdentityStore.SEARCH_BY_NAME, userName);
            return (user!=null)?user.getUserProfile():null;
                           
        } catch (IMException e) {
            return null; 
        }
    }
    
    public String getPropertyVal(UserProfile profile, String attribute){       
        try {            
            return (profile.getPropertyVal(attribute)!=null?profile.getPropertyVal(attribute).toString():null);           
            
        } catch (IMException e) {
            e.printStackTrace();
            return null; 
        }    
    } 
}
