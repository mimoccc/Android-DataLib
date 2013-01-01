package fr.eyal.datalib.sample.netflix.data.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.net.NetworkInfo;
import fr.eyal.lib.data.communication.rest.ParameterMap;
import fr.eyal.lib.data.model.BusinessObjectDAO;
import fr.eyal.lib.data.model.ResponseBusinessObjectDAO;
import fr.eyal.lib.data.service.DataManager;
import fr.eyal.lib.data.service.ServiceHelper;
import fr.eyal.lib.data.service.model.DataLibRequest;
import fr.eyal.datalib.sample.netflix.data.model.catalogtitles.*;
import fr.eyal.datalib.sample.netflix.data.model.NetflixProvider;

public class NetflixDataManager extends DataManager {

    @SuppressWarnings("unused")
    private static final String TAG = NetflixDataManager.class.getSimpleName();

    /**
     * Get the instance of the {@link NetflixDataManager}
     * 
     * @param context The context of execution. Any Context can be put here, the application context
     * will be automatically used for the {@link NetflixDataManager}
     * 
     * @return Returns the singleton
     */
    public static NetflixDataManager getInstance(final Context context) {
        synchronized (NetflixDataManager.class) {
			if (sInstance == null) {
            	sInstance = new NetflixDataManager(context.getApplicationContext());
        	}
		}
        return (NetflixDataManager) sInstance;
    }

    /**
     * Get the instance of the {@link NetflixDataManager}
     *  
     * @return Returns the singleton <b>only if</b> the instance have already been create by the call to 
     * <code>{@linkplain NetflixDataManager#getInstance(Context)}</code>. If it has not been called, this 
     * function returns <b>null</b>
     */
    public static NetflixDataManager getInstance() {
		if (sInstance == null) {
        	return null;
    	}
        return (NetflixDataManager) sInstance;
    }


    protected NetflixDataManager(final Context context) {
        super(context);

        mServiceHelper = NetflixServiceHelper.getInstance(context);
        mServiceHelper.addOnRequestFinishedRelayer(this);
    }

    @Override
    public BusinessObjectDAO getBusinessObjectFromCacheByUrl(final int type, final String url) {

        switch (type) {
            default:
                return null;
        }
    }


    @Override
    protected ResponseBusinessObjectDAO getResponseBusinessObjectById(final int webserviceType, final long id) {

        switch (webserviceType) {

            default:
                return null;
        }
    }


    @Override
    protected ArrayList<?> getBusinessObjectsFromDatabase(final int code, final String where, final String[] whereArgs, final String order, final boolean join) {

        switch (code) {

            default:
                return null;
        }
    }





	/**
     * Retrieve the {@link CatalogTitles}
     * 
     * @param datacacheListener The listener who will receive the data from the cache.
     * This parameter is optional butif you want to get the response back you have to subscribe to the
     * request's response by using the function {@link DataManager#addOnDataListener(int requestId, OnDataListener listener)}.
     * Since there is no listener for the request, the response is supposed to be stored in a cache. It is then removed once delivered.
     * 
     * @param max_results Maximum number of results expected. If this parameter is not present, the default value is 25.
     * 
     * @param oauth_consumer_key The OAuth consumer key of the developer
     * 
     * @param oauth_nonce A random string
     * 
     * @param oauth_signature_method The OAuth signature method. Here we use HMAC-SHA1
     * 
     * @param oauth_timestamp The current timestamp
     * 
     * @param start_index Start index of the result (to paginate the results). This value is linked to max_result
     * 
     * @param term The term of the content you are looking for
     * 
     * @param options The options added to the request. The list of constants to use in this filed
     * can be found in {@link DataLibRequest} (ex: {@link DataLibRequest#OPTION_CONSERVE_COOKIE_ENABLED} 
     * or {@link DataLibRequest#OPTION_DATABASE_CACHE_DISABLED}, ...).
     * The options can be aggregated thanks to the pipe character '|' (ex: OPTION_CONSERVE_COOKIE_ENABLED |
     * OPTION_DATABASE_CACHE_DISABLED).
     * 
     * @return Returns the request Id if it have been generated by the DataLib. If there is only
     * a Datacache access, the id returned is the constant {@link DataManager#DATACHACHE_REQUEST}.
     * In case of treatment error, it returns {@link DataManager#BAD_REQUEST}.
     * 
     * @throws UnsupportedEncodingException
     */
	public synchronized int getCatalogTitles(final OnDataListener datacacheListener, final int max_results, final String oauth_consumer_key, final String oauth_nonce, final String oauth_signature_method, final int oauth_timestamp, final String start_index, final String term, final int options) throws UnsupportedEncodingException {
        //we prepare the parameters
        final ParameterMap params = new ParameterMap();
		params.put("max_results", String.valueOf(max_results));
        params.put("oauth_consumer_key", oauth_consumer_key);
        params.put("oauth_nonce", oauth_nonce);
        params.put("oauth_signature_method", oauth_signature_method);
		params.put("oauth_timestamp", String.valueOf(oauth_timestamp));
        params.put("start_index", start_index);
        params.put("term", term);
        //we prepare the request's url
        final String url = ServiceHelper.getServiceUrl(NetflixServiceHelper.URL_CATALOGTITLES, params);

		int requestId = mServiceHelper.launchRequest(options, NetflixService.WEBSERVICE_CATALOGTITLES, params, NetflixService.class, url);
		
		//we add the listener subscription for this request
		if(datacacheListener != null)
			this.addOnDataListener(requestId, datacacheListener);
		
		return requestId;
    }


}
